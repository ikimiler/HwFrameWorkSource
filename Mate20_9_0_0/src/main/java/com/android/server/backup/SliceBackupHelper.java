package com.android.server.backup;

import android.app.backup.BlobBackupHelper;
import android.app.slice.ISliceManager.Stub;
import android.content.Context;
import android.os.ServiceManager;
import android.util.Log;
import android.util.Slog;

public class SliceBackupHelper extends BlobBackupHelper {
    static final int BLOB_VERSION = 1;
    static final boolean DEBUG = Log.isLoggable(TAG, 3);
    static final String KEY_SLICES = "slices";
    static final String TAG = "SliceBackupHelper";

    public SliceBackupHelper(Context context) {
        super(1, new String[]{KEY_SLICES});
    }

    protected byte[] getBackupPayload(String key) {
        if (!KEY_SLICES.equals(key)) {
            return null;
        }
        try {
            return Stub.asInterface(ServiceManager.getService("slice")).getBackupPayload(0);
        } catch (Exception e) {
            Slog.e(TAG, "Couldn't communicate with slice manager");
            return null;
        }
    }

    protected void applyRestoredPayload(String key, byte[] payload) {
        if (DEBUG) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Got restore of ");
            stringBuilder.append(key);
            Slog.v(str, stringBuilder.toString());
        }
        if (KEY_SLICES.equals(key)) {
            try {
                Stub.asInterface(ServiceManager.getService("slice")).applyRestore(payload, 0);
            } catch (Exception e) {
                Slog.e(TAG, "Couldn't communicate with slice manager");
            }
        }
    }
}
