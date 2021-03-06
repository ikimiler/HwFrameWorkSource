package com.android.server.net.watchlist;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.UserInfo;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings.Global;
import android.text.TextUtils;
import android.util.Slog;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.HexDump;
import com.android.server.net.watchlist.WatchlistReportDbHelper.AggregatedResult;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

class WatchlistLoggingHandler extends Handler {
    private static final boolean DEBUG = false;
    private static final String DROPBOX_TAG = "network_watchlist_report";
    @VisibleForTesting
    static final int FORCE_REPORT_RECORDS_NOW_FOR_TEST_MSG = 3;
    @VisibleForTesting
    static final int LOG_WATCHLIST_EVENT_MSG = 1;
    private static final long ONE_DAY_MS = TimeUnit.DAYS.toMillis(1);
    @VisibleForTesting
    static final int REPORT_RECORDS_IF_NECESSARY_MSG = 2;
    private static final String TAG = WatchlistLoggingHandler.class.getSimpleName();
    private final ConcurrentHashMap<Integer, byte[]> mCachedUidDigestMap = new ConcurrentHashMap();
    private final WatchlistConfig mConfig;
    private final Context mContext;
    private final WatchlistReportDbHelper mDbHelper;
    private final DropBoxManager mDropBoxManager;
    private final PackageManager mPm;
    private int mPrimaryUserId = -1;
    private final ContentResolver mResolver;
    private final WatchlistSettings mSettings;

    private interface WatchlistEventKeys {
        public static final String HOST = "host";
        public static final String IP_ADDRESSES = "ipAddresses";
        public static final String TIMESTAMP = "timestamp";
        public static final String UID = "uid";
    }

    WatchlistLoggingHandler(Context context, Looper looper) {
        super(looper);
        this.mContext = context;
        this.mPm = this.mContext.getPackageManager();
        this.mResolver = this.mContext.getContentResolver();
        this.mDbHelper = WatchlistReportDbHelper.getInstance(context);
        this.mConfig = WatchlistConfig.getInstance();
        this.mSettings = WatchlistSettings.getInstance();
        this.mDropBoxManager = (DropBoxManager) this.mContext.getSystemService(DropBoxManager.class);
        this.mPrimaryUserId = getPrimaryUserId();
    }

    public void handleMessage(Message msg) {
        switch (msg.what) {
            case 1:
                Bundle data = msg.getData();
                handleNetworkEvent(data.getString(WatchlistEventKeys.HOST), data.getStringArray(WatchlistEventKeys.IP_ADDRESSES), data.getInt("uid"), data.getLong(WatchlistEventKeys.TIMESTAMP));
                return;
            case 2:
                tryAggregateRecords(getLastMidnightTime());
                return;
            case 3:
                if (msg.obj instanceof Long) {
                    tryAggregateRecords(((Long) msg.obj).longValue());
                    return;
                } else {
                    Slog.e(TAG, "Msg.obj needs to be a Long object.");
                    return;
                }
            default:
                Slog.d(TAG, "WatchlistLoggingHandler received an unknown of message.");
                return;
        }
    }

    private int getPrimaryUserId() {
        UserInfo primaryUserInfo = ((UserManager) this.mContext.getSystemService("user")).getPrimaryUser();
        if (primaryUserInfo != null) {
            return primaryUserInfo.id;
        }
        return -1;
    }

    private boolean isPackageTestOnly(int uid) {
        boolean z = false;
        try {
            String[] packageNames = this.mPm.getPackagesForUid(uid);
            if (packageNames != null) {
                if (packageNames.length != 0) {
                    if ((this.mPm.getApplicationInfo(packageNames[0], 0).flags & 256) != 0) {
                        z = true;
                    }
                    return z;
                }
            }
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Couldn't find package: ");
            stringBuilder.append(packageNames);
            Slog.e(str, stringBuilder.toString());
            return false;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    public void reportWatchlistIfNecessary() {
        sendMessage(obtainMessage(2));
    }

    public void forceReportWatchlistForTest(long lastReportTime) {
        Message msg = obtainMessage(3);
        msg.obj = Long.valueOf(lastReportTime);
        sendMessage(msg);
    }

    public void asyncNetworkEvent(String host, String[] ipAddresses, int uid) {
        Message msg = obtainMessage(1);
        Bundle bundle = new Bundle();
        bundle.putString(WatchlistEventKeys.HOST, host);
        bundle.putStringArray(WatchlistEventKeys.IP_ADDRESSES, ipAddresses);
        bundle.putInt("uid", uid);
        bundle.putLong(WatchlistEventKeys.TIMESTAMP, System.currentTimeMillis());
        msg.setData(bundle);
        sendMessage(msg);
    }

    private void handleNetworkEvent(String hostname, String[] ipAddresses, int uid, long timestamp) {
        if (this.mPrimaryUserId == -1) {
            this.mPrimaryUserId = getPrimaryUserId();
        }
        if (UserHandle.getUserId(uid) == this.mPrimaryUserId) {
            String cncDomain = searchAllSubDomainsInWatchlist(hostname);
            if (cncDomain != null) {
                insertRecord(uid, cncDomain, timestamp);
            } else {
                String cncIp = searchIpInWatchlist(ipAddresses);
                if (cncIp != null) {
                    insertRecord(uid, cncIp, timestamp);
                }
            }
        }
    }

    private boolean insertRecord(int uid, String cncHost, long timestamp) {
        if (!this.mConfig.isConfigSecure() && !isPackageTestOnly(uid)) {
            return true;
        }
        byte[] digest = getDigestFromUid(uid);
        if (digest != null) {
            return this.mDbHelper.insertNewRecord(digest, cncHost, timestamp);
        }
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cannot get digest from uid: ");
        stringBuilder.append(uid);
        Slog.e(str, stringBuilder.toString());
        return false;
    }

    private boolean shouldReportNetworkWatchlist(long lastRecordTime) {
        long lastReportTime = Global.getLong(this.mResolver, "network_watchlist_last_report_time", 0);
        boolean z = false;
        if (lastRecordTime < lastReportTime) {
            Slog.i(TAG, "Last report time is larger than current time, reset report");
            this.mDbHelper.cleanup(lastReportTime);
            return false;
        }
        if (lastRecordTime >= ONE_DAY_MS + lastReportTime) {
            z = true;
        }
        return z;
    }

    private void tryAggregateRecords(long lastRecordTime) {
        long startTime = System.currentTimeMillis();
        long endTime;
        String str;
        StringBuilder stringBuilder;
        try {
            if (shouldReportNetworkWatchlist(lastRecordTime)) {
                Slog.i(TAG, "Start aggregating watchlist records.");
                if (this.mDropBoxManager == null || !this.mDropBoxManager.isTagEnabled(DROPBOX_TAG)) {
                    Slog.w(TAG, "Network Watchlist dropbox tag is not enabled");
                } else {
                    Global.putLong(this.mResolver, "network_watchlist_last_report_time", lastRecordTime);
                    AggregatedResult aggregatedResult = this.mDbHelper.getAggregatedRecords(lastRecordTime);
                    if (aggregatedResult == null) {
                        Slog.i(TAG, "Cannot get result from database");
                        endTime = System.currentTimeMillis();
                        str = TAG;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("Milliseconds spent on tryAggregateRecords(): ");
                        stringBuilder.append(endTime - startTime);
                        Slog.i(str, stringBuilder.toString());
                        return;
                    }
                    List<String> digestsForReport = getAllDigestsForReport(aggregatedResult);
                    byte[] encodedResult = ReportEncoder.encodeWatchlistReport(this.mConfig, this.mSettings.getPrivacySecretKey(), digestsForReport, aggregatedResult);
                    if (encodedResult != null) {
                        addEncodedReportToDropBox(encodedResult);
                    }
                }
                this.mDbHelper.cleanup(lastRecordTime);
                long endTime2 = System.currentTimeMillis();
                String str2 = TAG;
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Milliseconds spent on tryAggregateRecords(): ");
                stringBuilder2.append(endTime2 - startTime);
                Slog.i(str2, stringBuilder2.toString());
                return;
            }
            Slog.i(TAG, "No need to aggregate record yet.");
        } finally {
            endTime = System.currentTimeMillis();
            str = TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Milliseconds spent on tryAggregateRecords(): ");
            stringBuilder.append(endTime - startTime);
            Slog.i(str, stringBuilder.toString());
        }
    }

    @VisibleForTesting
    List<String> getAllDigestsForReport(AggregatedResult record) {
        List<ApplicationInfo> apps = this.mContext.getPackageManager().getInstalledApplications(131072);
        HashSet<String> result = new HashSet(apps.size() + record.appDigestCNCList.size());
        int size = apps.size();
        for (int i = 0; i < size; i++) {
            byte[] digest = getDigestFromUid(((ApplicationInfo) apps.get(i)).uid);
            if (digest != null) {
                result.add(HexDump.toHexString(digest));
            } else {
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Cannot get digest from uid: ");
                stringBuilder.append(((ApplicationInfo) apps.get(i)).uid);
                stringBuilder.append(",pkg: ");
                stringBuilder.append(((ApplicationInfo) apps.get(i)).packageName);
                Slog.e(str, stringBuilder.toString());
            }
        }
        result.addAll(record.appDigestCNCList.keySet());
        return new ArrayList(result);
    }

    private void addEncodedReportToDropBox(byte[] encodedReport) {
        this.mDropBoxManager.addData(DROPBOX_TAG, encodedReport, 0);
    }

    private byte[] getDigestFromUid(int uid) {
        return (byte[]) this.mCachedUidDigestMap.computeIfAbsent(Integer.valueOf(uid), new -$$Lambda$WatchlistLoggingHandler$GBD0dX6RhipHIkM0Z_B5jLlwfHQ(this, uid));
    }

    public static /* synthetic */ byte[] lambda$getDigestFromUid$0(WatchlistLoggingHandler watchlistLoggingHandler, int uid, Integer key) {
        String[] packageNames = watchlistLoggingHandler.mPm.getPackagesForUid(key.intValue());
        int userId = UserHandle.getUserId(uid);
        if (!ArrayUtils.isEmpty(packageNames)) {
            int length = packageNames.length;
            int i = 0;
            while (i < length) {
                String packageName = packageNames[i];
                try {
                    String apkPath = watchlistLoggingHandler.mPm.getPackageInfoAsUser(packageName, 786432, userId).applicationInfo.publicSourceDir;
                    if (!TextUtils.isEmpty(apkPath)) {
                        return DigestUtils.getSha256Hash(new File(apkPath));
                    }
                    String str = TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Cannot find apkPath for ");
                    stringBuilder.append(packageName);
                    Slog.w(str, stringBuilder.toString());
                    i++;
                } catch (NameNotFoundException | IOException | NoSuchAlgorithmException e) {
                    Slog.e(TAG, "Should not happen", e);
                    return null;
                }
            }
        }
        return null;
    }

    private String searchIpInWatchlist(String[] ipAddresses) {
        for (String ipAddress : ipAddresses) {
            if (isIpInWatchlist(ipAddress)) {
                return ipAddress;
            }
        }
        return null;
    }

    private boolean isIpInWatchlist(String ipAddr) {
        if (ipAddr == null) {
            return false;
        }
        return this.mConfig.containsIp(ipAddr);
    }

    private boolean isHostInWatchlist(String host) {
        if (host == null) {
            return false;
        }
        return this.mConfig.containsDomain(host);
    }

    private String searchAllSubDomainsInWatchlist(String host) {
        if (host == null) {
            return null;
        }
        for (String subDomain : getAllSubDomains(host)) {
            if (isHostInWatchlist(subDomain)) {
                return subDomain;
            }
        }
        return null;
    }

    @VisibleForTesting
    static String[] getAllSubDomains(String host) {
        if (host == null) {
            return null;
        }
        ArrayList<String> subDomainList = new ArrayList();
        subDomainList.add(host);
        int index = host.indexOf(".");
        while (index != -1) {
            host = host.substring(index + 1);
            if (!TextUtils.isEmpty(host)) {
                subDomainList.add(host);
            }
            index = host.indexOf(".");
        }
        return (String[]) subDomainList.toArray(new String[0]);
    }

    static long getLastMidnightTime() {
        return getMidnightTimestamp(0);
    }

    static long getMidnightTimestamp(int daysBefore) {
        Calendar date = new GregorianCalendar();
        date.set(11, 0);
        date.set(12, 0);
        date.set(13, 0);
        date.set(14, 0);
        date.add(5, -daysBefore);
        return date.getTimeInMillis();
    }
}
