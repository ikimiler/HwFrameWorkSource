package com.android.server.policy;

import android.app.ActivityManager;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.res.Resources.NotFoundException;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemProperties;
import android.provider.Settings.System;
import android.util.AttributeSet;
import android.util.Flog;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.internal.app.AssistUtils;
import com.android.server.LocalServices;
import com.android.server.gesture.GestureNavConst;
import com.android.server.statusbar.StatusBarManagerInternal;
import com.android.server.wifipro.WifiProCommonUtils;
import huawei.android.provider.FrontFingerPrintSettings;
import huawei.com.android.server.fingerprint.FingerViewController;
import java.lang.reflect.Method;
import java.util.List;

public class SearchPanelView extends FrameLayout {
    private static final String ACTION_SINGLE_BUTTON_KEYEVENT = "com.android.huawei.SINGLE_BUTTON_ACTION_KEYEVENT";
    public static final Interpolator ALPHA_OUT = new PathInterpolator(GestureNavConst.BOTTOM_WINDOW_SINGLE_HAND_RATIO, GestureNavConst.BOTTOM_WINDOW_SINGLE_HAND_RATIO, 0.8f, 1.0f);
    private static String ASSISTANT_ACTION = "com.huawei.action.VOICE_ASSISTANT";
    private static String ASSISTANT_ACTIVITY_NAME = "com.huawei.vassistant.ui.main.VAssistantActivity";
    private static final String ASSISTANT_ICON_METADATA_NAME = "com.android.systemui.action_assist_icon";
    private static final int AXIS_MOVE_FROM_INSIDE = 0;
    private static final int AXIS_MOVE_FROM_OUTSIDE = 1;
    private static final int AXIS_TOUCH_DATA_VALID = 1;
    private static final String EVENT_POINTER_COUNT = "pointer_count";
    private static final String EVENT_X = "event_x";
    private static final String EVENT_Y = "event_y";
    private static final String FINGER_PRINT_ACTION_KEYEVENT = "com.android.huawei.FINGER_PRINT_ACTION_KEYEVENT";
    protected static final String HW_ASSISTANT_PACKAGE_NAME = "com.huawei.vassistant";
    public static final String INTENT_KEY = "keycode";
    private static final int KEYCODE_START_VASSISTANT = 9999;
    private static final int MSG_HIDE_SEARCH_VIEW = 2;
    private static final int MSG_MOVE_EVENT = 5;
    private static final int MSG_SHOW_SEARCH_VIEW = 1;
    private static final int MSG_START_ABORT_ANIMATION = 3;
    private static final int MSG_START_EXIT_ANIMATION = 4;
    private static final String TAG = SearchPanelView.class.getSimpleName();
    private static final boolean mIsChina = "CN".equalsIgnoreCase(SystemProperties.get(WifiProCommonUtils.KEY_PROP_LOCALE, ""));
    private static ComponentName sVAssistantComponentName = null;
    private Boolean isLandScapeProduct;
    private boolean mAdded;
    private AssistUtils mAssistUtils;
    private SearchPanelCircleView mCircle;
    private Context mContext;
    private boolean mDraggedFarEnough;
    private boolean mDragging;
    private float mEffctiveStartTouch;
    private Handler mHandler;
    private Boolean mHasHwAssist;
    private boolean mIsCollapseStatusBar;
    private boolean mIsFromOutScreen;
    private boolean mIsTouchDataValid;
    private boolean mLaunchPending;
    private boolean mLaunching;
    private ImageView mLogo;
    private int mNavigationBarHeight;
    private boolean mNeedShowSearchPanel;
    private boolean mRotationReady;
    private View mScrim;
    private boolean mShowing;
    private float mStartDrag;
    private float mStartTouch;
    private float mStartX;
    private float mStartY;
    private int mThreshold;

    public SearchPanelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchPanelView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        boolean z = true;
        this.mIsFromOutScreen = true;
        this.mHasHwAssist = null;
        if (SystemProperties.getInt("ro.panel.hw_orientation", 0) != 90) {
            z = false;
        }
        this.isLandScapeProduct = Boolean.valueOf(z);
        this.mNeedShowSearchPanel = false;
        this.mRotationReady = false;
        this.mIsCollapseStatusBar = false;
        this.mContext = context;
        this.mThreshold = context.getResources().getDimensionPixelSize(34472148);
        this.mNavigationBarHeight = context.getResources().getDimensionPixelSize(17105186);
        this.mAssistUtils = new AssistUtils(context);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mCircle = (SearchPanelCircleView) findViewById(34603139);
        this.mLogo = (ImageView) findViewById(34603140);
        this.mScrim = findViewById(34603141);
        maybeSwapSearchIcon();
    }

    private ComponentName getAssistInfo() {
        if (this.mAssistUtils != null) {
            return this.mAssistUtils.getAssistComponentForUser(-2);
        }
        return null;
    }

    private void maybeSwapSearchIcon() {
        if (hasHwAssist()) {
            replaceDrawable(this.mLogo, getVAssistantComponentName(), ASSISTANT_ICON_METADATA_NAME);
            return;
        }
        Intent intent = ((SearchManager) this.mContext.getSystemService("search")).getAssistIntent(false);
        if (intent != null) {
            replaceDrawable(this.mLogo, intent.getComponent(), ASSISTANT_ICON_METADATA_NAME);
        } else {
            this.mLogo.setImageDrawable(null);
        }
    }

    public void replaceDrawable(ImageView imageView, ComponentName component, String name) {
        String str;
        StringBuilder stringBuilder;
        if (component != null) {
            try {
                PackageManager packageManager = this.mContext.getPackageManager();
                Bundle metaData = packageManager.getActivityInfo(component, 128).metaData;
                if (metaData != null) {
                    int iconResId = metaData.getInt(name);
                    if (iconResId != 0) {
                        imageView.setImageDrawable(packageManager.getResourcesForActivity(component).getDrawable(iconResId));
                    }
                }
            } catch (NameNotFoundException e) {
                str = TAG;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to swap drawable; ");
                stringBuilder.append(component.flattenToShortString());
                stringBuilder.append(" not found");
                Log.w(str, stringBuilder.toString(), e);
            } catch (NotFoundException nfe) {
                str = TAG;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to swap drawable from ");
                stringBuilder.append(component.flattenToShortString());
                Log.w(str, stringBuilder.toString(), nfe);
            }
        }
    }

    private ComponentName getVAssistantComponentName() {
        if (sVAssistantComponentName != null) {
            return sVAssistantComponentName;
        }
        sVAssistantComponentName = new ComponentName(HW_ASSISTANT_PACKAGE_NAME, ASSISTANT_ACTIVITY_NAME);
        PackageManager packageManager = this.mContext.getPackageManager();
        if (packageManager == null) {
            Log.w(TAG, "packageManager is null");
            return sVAssistantComponentName;
        }
        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(new Intent(ASSISTANT_ACTION), 65536);
        if (resolveInfos == null || resolveInfos.size() == 0) {
            Log.w(TAG, "resolveInfos is null");
            return sVAssistantComponentName;
        }
        ComponentInfo info = ((ResolveInfo) resolveInfos.get(0)).activityInfo;
        if (info == null) {
            Log.w(TAG, "activityInfo is null");
            return sVAssistantComponentName;
        }
        sVAssistantComponentName = new ComponentName(info.packageName, info.name);
        return sVAssistantComponentName;
    }

    public boolean dispatchHoverEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight()) {
            return true;
        }
        return super.dispatchHoverEvent(event);
    }

    public boolean handleGesture(MotionEvent event) {
        if (isNotReady(event)) {
            return false;
        }
        if (!this.mIsTouchDataValid && 1 == ((int) event.getAxisValue(37))) {
            Log.d(TAG, " mIsTouchDataValid:true");
            this.mIsTouchDataValid = true;
        }
        if (this.mIsTouchDataValid) {
            this.mIsFromOutScreen = ((int) event.getAxisValue(36)) != 0;
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(" mIsFromOutScreen:");
            stringBuilder.append(this.mIsFromOutScreen);
            Log.d(str, stringBuilder.toString());
        }
        switch (event.getActionMasked()) {
            case 0:
                if (this.isLandScapeProduct.booleanValue()) {
                    this.mStartTouch = event.getY();
                } else {
                    this.mStartTouch = this.mCircle.isLand() ? event.getX() : event.getY();
                }
                this.mEffctiveStartTouch = this.mCircle.isLand() ? event.getY() : event.getX();
                this.mDragging = false;
                this.mDraggedFarEnough = false;
                this.mCircle.reset();
                this.mStartX = this.mCircle.isLand() ? event.getY() : event.getX();
                this.mStartY = this.mCircle.isLand() ? event.getX() : event.getY();
                this.mNeedShowSearchPanel = false;
                this.mRotationReady = false;
                break;
            case 1:
            case 3:
                if (this.mIsFromOutScreen) {
                    float lastTouch;
                    if (this.isLandScapeProduct.booleanValue()) {
                        lastTouch = event.getY();
                    } else {
                        lastTouch = this.mCircle.isLand() ? event.getX() : event.getY();
                    }
                    String str2 = TAG;
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("lastTouch is:");
                    stringBuilder2.append(lastTouch);
                    stringBuilder2.append("mDraggedFarEnough=");
                    stringBuilder2.append(this.mDraggedFarEnough);
                    stringBuilder2.append(",mRotationReady=");
                    stringBuilder2.append(this.mRotationReady);
                    Log.d(str2, stringBuilder2.toString());
                    if (Math.abs(this.mStartTouch - lastTouch) <= ((float) this.mThreshold)) {
                        Log.d(TAG, "Not Far Enough 1");
                    } else if (!mIsChina && hasHwAssist()) {
                        if (this.mRotationReady) {
                            startVoiceAssist();
                        }
                        this.mIsFromOutScreen = true;
                        return true;
                    }
                    if (this.mDraggedFarEnough) {
                        processDraggedEvent();
                    } else {
                        abortAnimation();
                        Log.d(TAG, "Not Far Enough 2");
                    }
                    this.mIsCollapseStatusBar = false;
                    this.mIsFromOutScreen = true;
                    break;
                }
                return true;
            case 2:
                move(event);
                break;
        }
        return true;
    }

    private int getRotationBetweenPoints(float eventX, float eventY) {
        double rotationDegree = 0.0d;
        try {
            float endX = this.mCircle.isLand() ? eventY : eventX;
            float endY = this.mCircle.isLand() ? eventX : eventY;
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("endX = ");
            stringBuilder.append(endX);
            stringBuilder.append(" endY = ");
            stringBuilder.append(endY);
            Log.d(str, stringBuilder.toString());
            if (this.mStartX != endX && this.mStartY > endY) {
                double distanceX = (double) Math.abs(this.mStartX - endX);
                double distanceY = (double) Math.abs(this.mStartY - endY);
                String str2 = TAG;
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(" distanceX = ");
                stringBuilder2.append(distanceX);
                stringBuilder2.append(" distanceY = ");
                stringBuilder2.append(distanceY);
                Log.d(str2, stringBuilder2.toString());
                rotationDegree = (Math.atan(distanceY / distanceX) / 3.141592653589793d) * 180.0d;
            }
            str = TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("getRotationBetweenPoints rotationDegree = ");
            stringBuilder.append(rotationDegree);
            Log.d(str, stringBuilder.toString());
            return (int) rotationDegree;
        } catch (IllegalArgumentException e) {
            String str3 = TAG;
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append("getRotationBetweenPoints exception, ");
            stringBuilder3.append(e);
            Log.w(str3, stringBuilder3.toString());
            return 0;
        }
    }

    private boolean isNotReady(MotionEvent event) {
        return this.mLaunching || this.mLaunchPending || this.mCircle == null || event.getPointerCount() > 1;
    }

    private void processDraggedEvent() {
        if (this.mCircle.isAnimationRunning(true)) {
            this.mLaunchPending = true;
            this.mCircle.setAnimatingOut(true);
            this.mCircle.performOnAnimationFinished(new Runnable() {
                public void run() {
                    SearchPanelView.this.exitAnimation();
                }
            });
            return;
        }
        exitAnimation();
    }

    private void onMove(Bundle bundle) {
        if (bundle == null) {
            Log.w(TAG, "onMove return because the event is null");
        } else if (bundle.getInt(EVENT_POINTER_COUNT) == 0) {
            Log.w(TAG, "onMove return because the pointer count is 0, it will crash if go on.");
        } else {
            float currentEventX = bundle.getFloat(EVENT_X);
            float currentEventY = bundle.getFloat(EVENT_Y);
            try {
                float currentTouch;
                if (this.isLandScapeProduct.booleanValue()) {
                    currentTouch = currentEventY;
                } else {
                    currentTouch = this.mCircle.isLand() ? currentEventX : currentEventY;
                }
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onMove showSearchPanelView:");
                stringBuilder.append(this.mIsFromOutScreen);
                Log.d(str, stringBuilder.toString());
                boolean z = true;
                if (Math.abs(this.mStartTouch - currentTouch) > ((float) this.mNavigationBarHeight) && !isShowing()) {
                    if (!this.mIsCollapseStatusBar) {
                        collapseStatusBar(this.mContext);
                        this.mIsCollapseStatusBar = true;
                    }
                    if (this.mIsFromOutScreen) {
                        int rotationDegree;
                        if (mIsChina) {
                            if (this.isLandScapeProduct.booleanValue()) {
                                showSearchPanelView();
                            } else if (!this.mNeedShowSearchPanel) {
                                this.mNeedShowSearchPanel = true;
                                rotationDegree = getRotationBetweenPoints(currentEventX, currentEventY);
                                if (rotationDegree >= 70 && rotationDegree <= 90) {
                                    showSearchPanelView();
                                }
                            }
                        } else if (!this.mNeedShowSearchPanel) {
                            this.mNeedShowSearchPanel = true;
                            rotationDegree = getRotationBetweenPoints(currentEventX, currentEventY);
                            if (rotationDegree >= 70 && rotationDegree <= 90) {
                                this.mRotationReady = true;
                            }
                        }
                    }
                }
                if (getVisibility() == 0 && !this.mDragging && (!this.mCircle.isAnimationRunning(true) || Math.abs(this.mStartTouch - currentTouch) > ((float) this.mThreshold))) {
                    this.mStartDrag = currentTouch;
                    this.mDragging = true;
                }
                if (this.mDragging) {
                    this.mCircle.setDragDistance(this.mStartDrag > currentTouch ? this.mStartDrag - currentTouch : GestureNavConst.BOTTOM_WINDOW_SINGLE_HAND_RATIO);
                    if (Math.abs(this.mStartTouch - currentTouch) <= ((float) this.mThreshold)) {
                        z = false;
                    }
                    this.mDraggedFarEnough = z;
                    this.mCircle.setDraggedFarEnough(this.mDraggedFarEnough);
                }
            } catch (IllegalArgumentException e) {
                String str2 = TAG;
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("onMove return because there is an IllegalArgumentException, ");
                stringBuilder2.append(e);
                Log.w(str2, stringBuilder2.toString());
            }
        }
    }

    public void show(boolean show, boolean animate) {
        this.mShowing = show;
        if (show) {
            maybeSwapSearchIcon();
            if (getVisibility() != 0) {
                setVisibility(0);
                if (animate) {
                    startEnterAnimation();
                    notifyTrikeyEvent(KEYCODE_START_VASSISTANT);
                } else {
                    this.mScrim.setAlpha(1.0f);
                }
            }
            setFocusable(true);
            setFocusableInTouchMode(true);
            requestFocus();
        } else if (animate) {
            startAbortAnimation();
        } else {
            setVisibility(4);
        }
    }

    private void notifyTrikeyEvent(int keyCode) {
        ContentResolver resolver = this.mContext.getContentResolver();
        int isShowNaviGuide = System.getIntForUser(resolver, "systemui_tips_already_shown", 0, ActivityManager.getCurrentUser());
        boolean aiEnable = FrontFingerPrintSettings.isSingleNavBarAIEnable(resolver);
        boolean aiNavEnable = FrontFingerPrintSettings.isSingleVirtualNavbarEnable(resolver);
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("isShowNaviGuide:");
        stringBuilder.append(isShowNaviGuide);
        stringBuilder.append(", aiEnable:");
        stringBuilder.append(aiEnable);
        stringBuilder.append(", aiNavEnable:");
        stringBuilder.append(aiNavEnable);
        Log.d(str, stringBuilder.toString());
        Intent intent;
        if (aiNavEnable && !aiEnable && !FrontFingerPrintSettings.isChinaArea()) {
            intent = new Intent(ACTION_SINGLE_BUTTON_KEYEVENT);
            intent.putExtra("keycode", keyCode);
            intent.setPackage(FingerViewController.PKGNAME_OF_KEYGUARD);
            intent.addFlags(268435456);
            this.mContext.sendBroadcast(intent, "android.permission.STATUS_BAR");
        } else if (isShowNaviGuide == 0 && !FrontFingerPrintSettings.isNaviBarEnabled(resolver)) {
            intent = new Intent(FINGER_PRINT_ACTION_KEYEVENT);
            intent.putExtra("keycode", keyCode);
            intent.setPackage(FingerViewController.PKGNAME_OF_KEYGUARD);
            intent.addFlags(268435456);
            this.mContext.sendBroadcast(intent, "android.permission.STATUS_BAR");
        }
    }

    private void startEnterAnimation() {
        this.mCircle.startEnterAnimation();
        this.mScrim.setAlpha(1.0f);
    }

    private void startAbortAnimation() {
        this.mCircle.startAbortAnimation(new Runnable() {
            public void run() {
                SearchPanelView.this.mCircle.setAnimatingOut(false);
                SearchPanelView.this.setVisibility(4);
            }
        });
        this.mCircle.setAnimatingOut(true);
        this.mScrim.setAlpha(GestureNavConst.BOTTOM_WINDOW_SINGLE_HAND_RATIO);
    }

    private void startExitAnimation() {
        this.mLaunchPending = false;
        if (!this.mLaunching && getVisibility() == 0) {
            this.mLaunching = true;
            startVoiceAssist();
            this.mCircle.setAnimatingOut(true);
            this.mCircle.startExitAnimation(new Runnable() {
                public void run() {
                    SearchPanelView.this.mLaunching = false;
                    SearchPanelView.this.mCircle.setAnimatingOut(false);
                    SearchPanelView.this.setVisibility(4);
                }
            });
            this.mScrim.animate().alpha(GestureNavConst.BOTTOM_WINDOW_SINGLE_HAND_RATIO).setDuration(300).setStartDelay(0).setInterpolator(ALPHA_OUT);
        }
    }

    private void startVoiceAssist() {
        try {
            notifyTrikeyEvent(KEYCODE_START_VASSISTANT);
            if (FrontFingerPrintSettings.isSingleVirtualNavbarEnable(this.mContext.getContentResolver())) {
                Flog.bdReport(this.mContext, 11, "{type:singleVirturalBar}");
            } else {
                Flog.bdReport(this.mContext, 11, "{type:NoVirturalBar}");
            }
            ((StatusBarManagerInternal) LocalServices.getService(StatusBarManagerInternal.class)).startAssist(new Bundle());
        } catch (Exception exp) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("startVoiceAssist error:");
            stringBuilder.append(exp.getMessage());
            Log.e(str, stringBuilder.toString());
        }
    }

    public void hide(boolean animate) {
        if (animate) {
            startAbortAnimation();
        } else {
            setVisibility(4);
        }
    }

    public boolean isShowing() {
        return getVisibility() == 0 && !this.mCircle.isAnimatingOut();
    }

    public void initUI(Looper looper) {
        initHandler(looper);
    }

    private void initHandler(Looper looper) {
        this.mHandler = new Handler(looper) {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        SearchPanelView.this.show(true, true);
                        return;
                    case 2:
                        SearchPanelView.this.hide(true);
                        return;
                    case 3:
                        SearchPanelView.this.startAbortAnimation();
                        return;
                    case 4:
                        SearchPanelView.this.startExitAnimation();
                        return;
                    case 5:
                        SearchPanelView.this.onMove(msg.getData());
                        return;
                    default:
                        return;
                }
            }
        };
    }

    public void showSearchPanelView() {
        if (hasHwAssist() && this.mHandler != null) {
            this.mHandler.sendEmptyMessage(1);
        }
    }

    public void hideSearchPanelView() {
        if (this.mHandler != null) {
            this.mHandler.sendEmptyMessage(2);
        }
    }

    public boolean isInContentArea(int x, int y) {
        return true;
    }

    public void abortAnimation() {
        if (this.mHandler != null) {
            this.mHandler.sendEmptyMessage(3);
        }
    }

    public void exitAnimation() {
        if (this.mHandler != null) {
            this.mHandler.sendEmptyMessage(4);
        }
    }

    public void move(MotionEvent event) {
        if (this.mHandler != null) {
            Message msg = this.mHandler.obtainMessage();
            msg.what = 5;
            Bundle bundle = new Bundle();
            bundle.putFloat(EVENT_X, event.getX());
            bundle.putFloat(EVENT_Y, event.getY());
            bundle.putInt(EVENT_POINTER_COUNT, event.getPointerCount());
            msg.setData(bundle);
            this.mHandler.sendMessage(msg);
        }
    }

    public static final void collapseStatusBar(Context context) {
        String str;
        StringBuilder stringBuilder;
        Object sbservice = context.getSystemService("statusbar");
        try {
            Method collapse;
            Class<?> statusBarManager = Class.forName("android.app.StatusBarManager");
            if (VERSION.SDK_INT >= 17) {
                collapse = statusBarManager.getMethod("collapsePanels", new Class[0]);
            } else {
                collapse = statusBarManager.getMethod("collapse", new Class[0]);
            }
            collapse.invoke(sbservice, new Object[0]);
        } catch (ClassNotFoundException ce) {
            str = TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Class not found for StatusBarManager:");
            stringBuilder.append(ce.getMessage());
            Log.w(str, stringBuilder.toString());
        } catch (NoSuchMethodException me) {
            str = TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Method not found for collapse:");
            stringBuilder.append(me.getMessage());
            Log.w(str, stringBuilder.toString());
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean hasHwAssist() {
        if (mIsChina) {
            if (this.mHasHwAssist == null) {
                try {
                    this.mContext.getPackageManager().getPackageInfo(HW_ASSISTANT_PACKAGE_NAME, 128);
                    this.mHasHwAssist = Boolean.valueOf(true);
                } catch (NameNotFoundException e) {
                    this.mHasHwAssist = Boolean.valueOf(false);
                } catch (Exception exp) {
                    this.mHasHwAssist = Boolean.valueOf(false);
                    exp.printStackTrace();
                }
            }
            return this.mHasHwAssist.booleanValue();
        } else if (getAssistInfo() != null) {
            return true;
        } else {
            Log.w(TAG, "startAssist::assistComponent is null, return");
            return false;
        }
    }
}
