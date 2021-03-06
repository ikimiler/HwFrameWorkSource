package com.android.server.wm;

import android.graphics.Bitmap;
import android.graphics.GraphicBuffer;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Debug;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.util.ArraySet;
import android.util.Slog;
import android.view.DisplayInfo;
import android.view.SurfaceControl;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import com.android.internal.util.ToBooleanFunction;
import com.android.server.pm.DumpState;
import java.io.PrintWriter;
import java.util.ArrayList;

public class WallpaperController {
    private static final String TAG = "WindowManager";
    private static final int WALLPAPER_DRAW_NORMAL = 0;
    private static final int WALLPAPER_DRAW_PENDING = 1;
    private static final long WALLPAPER_DRAW_PENDING_TIMEOUT_DURATION = 500;
    private static final int WALLPAPER_DRAW_TIMEOUT = 2;
    private static final long WALLPAPER_TIMEOUT = 150;
    private static final long WALLPAPER_TIMEOUT_RECOVERY = 10000;
    private static boolean mUsingHwNavibar = SystemProperties.getBoolean("ro.config.hw_navigationbar", false);
    WindowState mDeferredHideWallpaper = null;
    private final FindWallpaperTargetResult mFindResults = new FindWallpaperTargetResult();
    private final ToBooleanFunction<WindowState> mFindWallpaperTargetFunction = new -$$Lambda$WallpaperController$6pruPGLeSJAwNl9vGfC87eso21w(this);
    private int mLastWallpaperDisplayOffsetX = Integer.MIN_VALUE;
    private int mLastWallpaperDisplayOffsetY = Integer.MIN_VALUE;
    private long mLastWallpaperTimeoutTime;
    private float mLastWallpaperX = -1.0f;
    private float mLastWallpaperXStep = -1.0f;
    private float mLastWallpaperY = -1.0f;
    private float mLastWallpaperYStep = -1.0f;
    private WindowState mPrevWallpaperTarget = null;
    private WindowManagerService mService;
    private WindowState mTmpTopWallpaper;
    private WindowState mWaitingOnWallpaper;
    private int mWallpaperDrawState = 0;
    private WindowState mWallpaperTarget = null;
    private final ArrayList<WallpaperWindowToken> mWallpaperTokens = new ArrayList();

    private static final class FindWallpaperTargetResult {
        boolean resetTopWallpaper;
        WindowState topWallpaper;
        boolean useTopWallpaperAsTarget;
        WindowState wallpaperTarget;

        private FindWallpaperTargetResult() {
            this.topWallpaper = null;
            this.useTopWallpaperAsTarget = false;
            this.wallpaperTarget = null;
            this.resetTopWallpaper = false;
        }

        void setTopWallpaper(WindowState win) {
            this.topWallpaper = win;
        }

        void setWallpaperTarget(WindowState win) {
            this.wallpaperTarget = win;
        }

        void setUseTopWallpaperAsTarget(boolean topWallpaperAsTarget) {
            this.useTopWallpaperAsTarget = topWallpaperAsTarget;
        }

        void reset() {
            this.topWallpaper = null;
            this.wallpaperTarget = null;
            this.useTopWallpaperAsTarget = false;
            this.resetTopWallpaper = false;
        }
    }

    public static /* synthetic */ boolean lambda$new$0(WallpaperController wallpaperController, WindowState w) {
        WindowAnimator winAnimator = wallpaperController.mService.mAnimator;
        if (w.mAttrs.type == 2013) {
            if (wallpaperController.mFindResults.topWallpaper == null || wallpaperController.mFindResults.resetTopWallpaper) {
                wallpaperController.mFindResults.setTopWallpaper(w);
                wallpaperController.mFindResults.resetTopWallpaper = false;
            }
            return false;
        }
        wallpaperController.mFindResults.resetTopWallpaper = true;
        String str;
        if (w == winAnimator.mWindowDetachedWallpaper || w.mAppToken == null || !w.mAppToken.isHidden() || w.mAppToken.isSelfAnimating()) {
            if (WindowManagerDebugConfig.DEBUG_WALLPAPER) {
                str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Win ");
                stringBuilder.append(w);
                stringBuilder.append(": isOnScreen=");
                stringBuilder.append(w.isOnScreen());
                stringBuilder.append(" mDrawState=");
                stringBuilder.append(w.mWinAnimator.mDrawState);
                Slog.v(str, stringBuilder.toString());
            }
            if (w.mWillReplaceWindow && wallpaperController.mWallpaperTarget == null && !wallpaperController.mFindResults.useTopWallpaperAsTarget) {
                wallpaperController.mFindResults.setUseTopWallpaperAsTarget(true);
            }
            boolean keyguardGoingAwayWithWallpaper = w.mAppToken != null && w.mAppToken.isSelfAnimating() && AppTransition.isKeyguardGoingAwayTransit(w.mAppToken.getTransit()) && (w.mAppToken.getTransitFlags() & 4) != 0;
            boolean needsShowWhenLockedWallpaper = false;
            if ((w.mAttrs.flags & DumpState.DUMP_FROZEN) != 0 && wallpaperController.mService.mPolicy.isKeyguardLocked() && wallpaperController.mService.mPolicy.isKeyguardOccluded()) {
                boolean z = (wallpaperController.isFullscreen(w.mAttrs) && (w.mAppToken == null || w.mAppToken.fillsParent())) ? false : true;
                needsShowWhenLockedWallpaper = z;
            }
            if (keyguardGoingAwayWithWallpaper || needsShowWhenLockedWallpaper) {
                wallpaperController.mFindResults.setUseTopWallpaperAsTarget(true);
            }
            wallpaperController.mService.showWallpaperIfNeed(w);
            RecentsAnimationController recentsAnimationController = wallpaperController.mService.getRecentsAnimationController();
            boolean animationWallpaper = (w.mAppToken == null || w.mAppToken.getAnimation() == null || !w.mAppToken.getAnimation().getShowWallpaper()) ? false : true;
            boolean hasWallpaper = (w.mAttrs.flags & DumpState.DUMP_DEXOPT) != 0 || animationWallpaper;
            boolean isRecentsTransitionTarget = recentsAnimationController != null && recentsAnimationController.isWallpaperVisible(w);
            String str2;
            StringBuilder stringBuilder2;
            if (isRecentsTransitionTarget) {
                if (WindowManagerDebugConfig.DEBUG_WALLPAPER) {
                    str2 = TAG;
                    stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("Found recents animation wallpaper target: ");
                    stringBuilder2.append(w);
                    Slog.v(str2, stringBuilder2.toString());
                }
                wallpaperController.mFindResults.setWallpaperTarget(w);
                return true;
            } else if (hasWallpaper && w.isOnScreen() && (wallpaperController.mWallpaperTarget == w || w.isDrawFinishedLw())) {
                if (WindowManagerDebugConfig.DEBUG_WALLPAPER) {
                    str2 = TAG;
                    stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("Found wallpaper target: ");
                    stringBuilder2.append(w);
                    Slog.v(str2, stringBuilder2.toString());
                }
                wallpaperController.mFindResults.setWallpaperTarget(w);
                if (w == wallpaperController.mWallpaperTarget && w.mWinAnimator.isAnimationSet() && WindowManagerDebugConfig.DEBUG_WALLPAPER) {
                    str2 = TAG;
                    stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("Win ");
                    stringBuilder2.append(w);
                    stringBuilder2.append(": token animating, looking behind.");
                    Slog.v(str2, stringBuilder2.toString());
                }
                return true;
            } else {
                if (w == winAnimator.mWindowDetachedWallpaper) {
                    if (WindowManagerDebugConfig.DEBUG_WALLPAPER_LIGHT) {
                        String str3 = TAG;
                        StringBuilder stringBuilder3 = new StringBuilder();
                        stringBuilder3.append("Found animating detached wallpaper target win: ");
                        stringBuilder3.append(w);
                        Slog.v(str3, stringBuilder3.toString());
                    }
                    wallpaperController.mFindResults.setUseTopWallpaperAsTarget(true);
                }
                return false;
            }
        }
        if (WindowManagerDebugConfig.DEBUG_WALLPAPER) {
            str = TAG;
            StringBuilder stringBuilder4 = new StringBuilder();
            stringBuilder4.append("Skipping hidden and not animating token: ");
            stringBuilder4.append(w);
            Slog.v(str, stringBuilder4.toString());
        }
        return false;
    }

    public WallpaperController(WindowManagerService service) {
        this.mService = service;
    }

    WindowState getWallpaperTarget() {
        return this.mWallpaperTarget;
    }

    boolean isWallpaperTarget(WindowState win) {
        return win == this.mWallpaperTarget;
    }

    boolean isBelowWallpaperTarget(WindowState win) {
        return this.mWallpaperTarget != null && this.mWallpaperTarget.mLayer >= win.mBaseLayer;
    }

    boolean isWallpaperVisible() {
        return isWallpaperVisible(this.mWallpaperTarget);
    }

    void startWallpaperAnimation(Animation a) {
        for (int curTokenNdx = this.mWallpaperTokens.size() - 1; curTokenNdx >= 0; curTokenNdx--) {
            ((WallpaperWindowToken) this.mWallpaperTokens.get(curTokenNdx)).startAnimation(a);
        }
    }

    private final boolean isWallpaperVisible(WindowState wallpaperTarget) {
        RecentsAnimationController recentsAnimationController = this.mService.getRecentsAnimationController();
        boolean isAnimatingWithRecentsComponent = recentsAnimationController != null && recentsAnimationController.isWallpaperVisible(wallpaperTarget);
        if (WindowManagerDebugConfig.DEBUG_WALLPAPER) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Wallpaper vis: target ");
            stringBuilder.append(wallpaperTarget);
            stringBuilder.append(", obscured=");
            stringBuilder.append(wallpaperTarget != null ? Boolean.toString(wallpaperTarget.mObscured) : "??");
            stringBuilder.append(" animating=");
            Object valueOf = (wallpaperTarget == null || wallpaperTarget.mAppToken == null) ? null : Boolean.valueOf(wallpaperTarget.mAppToken.isSelfAnimating());
            stringBuilder.append(valueOf);
            stringBuilder.append(" prev=");
            stringBuilder.append(this.mPrevWallpaperTarget);
            stringBuilder.append(" recentsAnimationWallpaperVisible=");
            stringBuilder.append(isAnimatingWithRecentsComponent);
            Slog.v(str, stringBuilder.toString());
        }
        if ((wallpaperTarget == null || (wallpaperTarget.mObscured && !isAnimatingWithRecentsComponent && (wallpaperTarget.mAppToken == null || !wallpaperTarget.mAppToken.isSelfAnimating()))) && this.mPrevWallpaperTarget == null) {
            return false;
        }
        return true;
    }

    boolean isWallpaperTargetAnimating() {
        return this.mWallpaperTarget != null && this.mWallpaperTarget.mWinAnimator.isAnimationSet() && (this.mWallpaperTarget.mAppToken == null || !this.mWallpaperTarget.mAppToken.isWaitingForTransitionStart());
    }

    void updateWallpaperVisibility() {
        boolean visible = isWallpaperVisible(this.mWallpaperTarget);
        for (int curTokenNdx = this.mWallpaperTokens.size() - 1; curTokenNdx >= 0; curTokenNdx--) {
            ((WallpaperWindowToken) this.mWallpaperTokens.get(curTokenNdx)).updateWallpaperVisibility(visible);
        }
    }

    void hideDeferredWallpapersIfNeeded() {
        if (this.mDeferredHideWallpaper != null) {
            hideWallpapers(this.mDeferredHideWallpaper);
            this.mDeferredHideWallpaper = null;
        }
    }

    void hideWallpapers(WindowState winGoingAway) {
        if (this.mWallpaperTarget != null && (this.mWallpaperTarget != winGoingAway || this.mPrevWallpaperTarget != null)) {
            return;
        }
        if (this.mService.mAppTransition.isRunning()) {
            this.mDeferredHideWallpaper = winGoingAway;
            return;
        }
        boolean wasDeferred = this.mDeferredHideWallpaper == winGoingAway;
        int i = this.mWallpaperTokens.size() - 1;
        while (true) {
            int i2 = i;
            if (i2 >= 0) {
                WallpaperWindowToken token = (WallpaperWindowToken) this.mWallpaperTokens.get(i2);
                token.hideWallpaperToken(wasDeferred, "hideWallpapers");
                if (WindowManagerDebugConfig.DEBUG_WALLPAPER_LIGHT && !token.isHidden()) {
                    String str = TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Hiding wallpaper ");
                    stringBuilder.append(token);
                    stringBuilder.append(" from ");
                    stringBuilder.append(winGoingAway);
                    stringBuilder.append(" target=");
                    stringBuilder.append(this.mWallpaperTarget);
                    stringBuilder.append(" prev=");
                    stringBuilder.append(this.mPrevWallpaperTarget);
                    stringBuilder.append("\n");
                    stringBuilder.append(Debug.getCallers(5, "  "));
                    Slog.d(str, stringBuilder.toString());
                }
                i = i2 - 1;
            } else {
                return;
            }
        }
    }

    boolean updateWallpaperOffset(WindowState wallpaperWin, int dw, int dh, boolean sync) {
        int offset;
        int i;
        WindowState windowState = wallpaperWin;
        boolean rawChanged = false;
        float defaultWallpaperX = wallpaperWin.isRtl() ? 1.0f : 0.0f;
        float wpx = this.mLastWallpaperX >= 0.0f ? this.mLastWallpaperX : defaultWallpaperX;
        float wpxs = this.mLastWallpaperXStep >= 0.0f ? this.mLastWallpaperXStep : -1.0f;
        int availw = (windowState.mFrame.right - windowState.mFrame.left) - dw;
        int offset2 = availw > 0 ? -((int) ((((float) availw) * wpx) + 0.5f)) : 0;
        if (this.mLastWallpaperDisplayOffsetX != Integer.MIN_VALUE) {
            offset2 += this.mLastWallpaperDisplayOffsetX;
        }
        int xOffset = offset2;
        if (!(windowState.mWallpaperX == wpx && windowState.mWallpaperXStep == wpxs)) {
            windowState.mWallpaperX = wpx;
            windowState.mWallpaperXStep = wpxs;
            rawChanged = true;
        }
        float wpy = this.mLastWallpaperY >= 0.0f ? this.mLastWallpaperY : 0.5f;
        float wpys = this.mLastWallpaperYStep >= 0.0f ? this.mLastWallpaperYStep : -1.0f;
        int availh = (windowState.mFrame.bottom - windowState.mFrame.top) - dh;
        if (availh > 0) {
            defaultWallpaperX = wpy;
            offset = -((int) ((((float) availh) * defaultWallpaperX) + 0.5f));
        } else {
            defaultWallpaperX = wpy;
            offset = 0;
        }
        if (mUsingHwNavibar) {
            offset = 0;
        }
        if (this.mLastWallpaperDisplayOffsetY != Integer.MIN_VALUE) {
            offset += this.mLastWallpaperDisplayOffsetY;
        }
        int yOffset = offset;
        if (!(windowState.mWallpaperY == defaultWallpaperX && windowState.mWallpaperYStep == wpys)) {
            windowState.mWallpaperY = defaultWallpaperX;
            windowState.mWallpaperYStep = wpys;
            rawChanged = true;
        }
        boolean rawChanged2 = rawChanged;
        boolean changed = windowState.mWinAnimator.setWallpaperOffset(xOffset, yOffset);
        float f;
        int i2;
        int i3;
        if (!rawChanged2 || (windowState.mAttrs.privateFlags & 4) == 0) {
            f = wpys;
            i2 = availh;
            i3 = yOffset;
        } else {
            try {
                String str;
                if (WindowManagerDebugConfig.DEBUG_WALLPAPER) {
                    try {
                        str = TAG;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Report new wp offset ");
                        stringBuilder.append(windowState);
                        stringBuilder.append(" x=");
                        stringBuilder.append(windowState.mWallpaperX);
                        stringBuilder.append(" y=");
                        stringBuilder.append(windowState.mWallpaperY);
                        Slog.v(str, stringBuilder.toString());
                    } catch (RemoteException e) {
                        i = xOffset;
                        f = wpys;
                        i2 = availh;
                        i3 = yOffset;
                    }
                }
                if (sync) {
                    this.mWaitingOnWallpaper = windowState;
                }
                try {
                    try {
                        try {
                            windowState.mClient.dispatchWallpaperOffsets(windowState.mWallpaperX, windowState.mWallpaperY, windowState.mWallpaperXStep, windowState.mWallpaperYStep, sync);
                            if (sync && this.mWaitingOnWallpaper != null) {
                                long start = SystemClock.uptimeMillis();
                                if (this.mLastWallpaperTimeoutTime + 10000 < start) {
                                    try {
                                        if (WindowManagerDebugConfig.DEBUG_WALLPAPER) {
                                            Slog.v(TAG, "Waiting for offset complete...");
                                        }
                                        this.mService.mWindowMap.wait(WALLPAPER_TIMEOUT);
                                    } catch (InterruptedException e2) {
                                    }
                                    if (WindowManagerDebugConfig.DEBUG_WALLPAPER) {
                                        Slog.v(TAG, "Offset complete!");
                                    }
                                    if (WALLPAPER_TIMEOUT + start < SystemClock.uptimeMillis()) {
                                        str = TAG;
                                        StringBuilder stringBuilder2 = new StringBuilder();
                                        stringBuilder2.append("Timeout waiting for wallpaper to offset: ");
                                        stringBuilder2.append(windowState);
                                        Slog.i(str, stringBuilder2.toString());
                                        this.mLastWallpaperTimeoutTime = start;
                                    }
                                }
                                this.mWaitingOnWallpaper = null;
                            }
                        } catch (RemoteException e3) {
                        }
                    } catch (RemoteException e4) {
                        i = xOffset;
                        i3 = yOffset;
                    }
                } catch (RemoteException e5) {
                    i = xOffset;
                    i2 = availh;
                    i3 = yOffset;
                }
            } catch (RemoteException e6) {
                i = xOffset;
                f = wpys;
                i2 = availh;
                i3 = yOffset;
            }
        }
        return changed;
    }

    void setWindowWallpaperPosition(WindowState window, float x, float y, float xStep, float yStep) {
        if (window.mWallpaperX != x || window.mWallpaperY != y) {
            window.mWallpaperX = x;
            window.mWallpaperY = y;
            window.mWallpaperXStep = xStep;
            window.mWallpaperYStep = yStep;
            updateWallpaperOffsetLocked(window, true);
        }
    }

    void setWindowWallpaperDisplayOffset(WindowState window, int x, int y) {
        if (window.mWallpaperDisplayOffsetX != x || window.mWallpaperDisplayOffsetY != y) {
            window.mWallpaperDisplayOffsetX = x;
            window.mWallpaperDisplayOffsetY = y;
            updateWallpaperOffsetLocked(window, true);
        }
    }

    Bundle sendWindowWallpaperCommand(WindowState window, String action, int x, int y, int z, Bundle extras, boolean sync) {
        WindowState windowState = window;
        if (windowState == this.mWallpaperTarget || windowState == this.mPrevWallpaperTarget) {
            boolean doWait = sync;
            for (int curTokenNdx = this.mWallpaperTokens.size() - 1; curTokenNdx >= 0; curTokenNdx--) {
                ((WallpaperWindowToken) this.mWallpaperTokens.get(curTokenNdx)).sendWindowWallpaperCommand(action, x, y, z, extras, sync);
            }
        }
        return null;
    }

    private void updateWallpaperOffsetLocked(WindowState changingTarget, boolean sync) {
        DisplayContent displayContent = changingTarget.getDisplayContent();
        if (displayContent != null) {
            DisplayInfo displayInfo = displayContent.getDisplayInfo();
            int dw = displayInfo.logicalWidth;
            int dh = displayInfo.logicalHeight;
            WindowState target = this.mWallpaperTarget;
            if (target != null) {
                if (target.mWallpaperX >= 0.0f) {
                    this.mLastWallpaperX = target.mWallpaperX;
                } else if (changingTarget.mWallpaperX >= 0.0f) {
                    this.mLastWallpaperX = changingTarget.mWallpaperX;
                }
                if (target.mWallpaperY >= 0.0f) {
                    this.mLastWallpaperY = target.mWallpaperY;
                } else if (changingTarget.mWallpaperY >= 0.0f) {
                    this.mLastWallpaperY = changingTarget.mWallpaperY;
                }
                if (target.mWallpaperDisplayOffsetX != Integer.MIN_VALUE) {
                    this.mLastWallpaperDisplayOffsetX = target.mWallpaperDisplayOffsetX;
                } else if (changingTarget.mWallpaperDisplayOffsetX != Integer.MIN_VALUE) {
                    this.mLastWallpaperDisplayOffsetX = changingTarget.mWallpaperDisplayOffsetX;
                }
                if (target.mWallpaperDisplayOffsetY != Integer.MIN_VALUE) {
                    this.mLastWallpaperDisplayOffsetY = target.mWallpaperDisplayOffsetY;
                } else if (changingTarget.mWallpaperDisplayOffsetY != Integer.MIN_VALUE) {
                    this.mLastWallpaperDisplayOffsetY = changingTarget.mWallpaperDisplayOffsetY;
                }
                if (target.mWallpaperXStep >= 0.0f) {
                    this.mLastWallpaperXStep = target.mWallpaperXStep;
                } else if (changingTarget.mWallpaperXStep >= 0.0f) {
                    this.mLastWallpaperXStep = changingTarget.mWallpaperXStep;
                }
                if (target.mWallpaperYStep >= 0.0f) {
                    this.mLastWallpaperYStep = target.mWallpaperYStep;
                } else if (changingTarget.mWallpaperYStep >= 0.0f) {
                    this.mLastWallpaperYStep = changingTarget.mWallpaperYStep;
                }
            }
            for (int curTokenNdx = this.mWallpaperTokens.size() - 1; curTokenNdx >= 0; curTokenNdx--) {
                ((WallpaperWindowToken) this.mWallpaperTokens.get(curTokenNdx)).updateWallpaperOffset(dw, dh, sync);
            }
        }
    }

    void clearLastWallpaperTimeoutTime() {
        this.mLastWallpaperTimeoutTime = 0;
    }

    void wallpaperCommandComplete(IBinder window) {
        if (this.mWaitingOnWallpaper != null && this.mWaitingOnWallpaper.mClient.asBinder() == window) {
            this.mWaitingOnWallpaper = null;
            this.mService.mWindowMap.notifyAll();
        }
    }

    void wallpaperOffsetsComplete(IBinder window) {
        if (this.mWaitingOnWallpaper != null && this.mWaitingOnWallpaper.mClient.asBinder() == window) {
            this.mWaitingOnWallpaper = null;
            this.mService.mWindowMap.notifyAll();
        }
    }

    private void findWallpaperTarget(DisplayContent dc) {
        this.mFindResults.reset();
        if (dc.isStackVisible(5)) {
            this.mFindResults.setUseTopWallpaperAsTarget(true);
        }
        dc.forAllWindows(this.mFindWallpaperTargetFunction, true);
        if (this.mFindResults.wallpaperTarget == null && this.mFindResults.useTopWallpaperAsTarget) {
            this.mFindResults.setWallpaperTarget(this.mFindResults.topWallpaper);
        }
    }

    private boolean isFullscreen(LayoutParams attrs) {
        return attrs.x == 0 && attrs.y == 0 && attrs.width == -1 && attrs.height == -1;
    }

    /* JADX WARNING: Missing block: B:51:0x010a, code skipped:
            return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void updateWallpaperWindowsTarget(DisplayContent dc, FindWallpaperTargetResult result) {
        WindowState wallpaperTarget = result.wallpaperTarget;
        if (this.mWallpaperTarget == wallpaperTarget || (this.mPrevWallpaperTarget != null && this.mPrevWallpaperTarget == wallpaperTarget)) {
            if (!(this.mPrevWallpaperTarget == null || this.mPrevWallpaperTarget.isAnimatingLw())) {
                if (WindowManagerDebugConfig.DEBUG_WALLPAPER_LIGHT) {
                    Slog.v(TAG, "No longer animating wallpaper targets!");
                }
                this.mPrevWallpaperTarget = null;
                this.mWallpaperTarget = wallpaperTarget;
            }
            return;
        }
        if (WindowManagerDebugConfig.DEBUG_WALLPAPER_LIGHT) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("New wallpaper target: ");
            stringBuilder.append(wallpaperTarget);
            stringBuilder.append(" prevTarget: ");
            stringBuilder.append(this.mWallpaperTarget);
            Slog.v(str, stringBuilder.toString());
        }
        this.mPrevWallpaperTarget = null;
        WindowState prevWallpaperTarget = this.mWallpaperTarget;
        this.mWallpaperTarget = wallpaperTarget;
        if (wallpaperTarget != null && prevWallpaperTarget != null) {
            boolean oldAnim = prevWallpaperTarget.isAnimatingLw();
            boolean foundAnim = wallpaperTarget.isAnimatingLw();
            if (WindowManagerDebugConfig.DEBUG_WALLPAPER_LIGHT) {
                String str2 = TAG;
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("New animation: ");
                stringBuilder2.append(foundAnim);
                stringBuilder2.append(" old animation: ");
                stringBuilder2.append(oldAnim);
                Slog.v(str2, stringBuilder2.toString());
            }
            if (foundAnim && oldAnim && dc.getWindow(new -$$Lambda$WallpaperController$Gy7houdzET4VmpY0QJ2v-NX1b7k(prevWallpaperTarget)) != null) {
                boolean oldTargetHidden = false;
                boolean newTargetHidden = wallpaperTarget.mAppToken != null && wallpaperTarget.mAppToken.hiddenRequested;
                if (prevWallpaperTarget.mAppToken != null && prevWallpaperTarget.mAppToken.hiddenRequested) {
                    oldTargetHidden = true;
                }
                if (WindowManagerDebugConfig.DEBUG_WALLPAPER_LIGHT) {
                    String str3 = TAG;
                    StringBuilder stringBuilder3 = new StringBuilder();
                    stringBuilder3.append("Animating wallpapers: old: ");
                    stringBuilder3.append(prevWallpaperTarget);
                    stringBuilder3.append(" hidden=");
                    stringBuilder3.append(oldTargetHidden);
                    stringBuilder3.append(" new: ");
                    stringBuilder3.append(wallpaperTarget);
                    stringBuilder3.append(" hidden=");
                    stringBuilder3.append(newTargetHidden);
                    Slog.v(str3, stringBuilder3.toString());
                }
                this.mPrevWallpaperTarget = prevWallpaperTarget;
                if (newTargetHidden && !oldTargetHidden) {
                    if (WindowManagerDebugConfig.DEBUG_WALLPAPER_LIGHT) {
                        Slog.v(TAG, "Old wallpaper still the target.");
                    }
                    this.mWallpaperTarget = prevWallpaperTarget;
                } else if (newTargetHidden == oldTargetHidden && !this.mService.mOpeningApps.contains(wallpaperTarget.mAppToken) && (this.mService.mOpeningApps.contains(prevWallpaperTarget.mAppToken) || this.mService.mClosingApps.contains(prevWallpaperTarget.mAppToken))) {
                    this.mWallpaperTarget = prevWallpaperTarget;
                }
                result.setWallpaperTarget(wallpaperTarget);
            }
        }
    }

    static /* synthetic */ boolean lambda$updateWallpaperWindowsTarget$1(WindowState prevWallpaperTarget, WindowState w) {
        return w == prevWallpaperTarget;
    }

    private void updateWallpaperTokens(boolean visible) {
        for (int curTokenNdx = this.mWallpaperTokens.size() - 1; curTokenNdx >= 0; curTokenNdx--) {
            WallpaperWindowToken token = (WallpaperWindowToken) this.mWallpaperTokens.get(curTokenNdx);
            token.updateWallpaperWindows(visible);
            token.getDisplayContent().assignWindowLayers(false);
        }
    }

    void adjustWallpaperWindows(DisplayContent dc) {
        String str;
        StringBuilder stringBuilder;
        boolean z = false;
        this.mService.mRoot.mWallpaperMayChange = false;
        findWallpaperTarget(dc);
        updateWallpaperWindowsTarget(dc, this.mFindResults);
        if (this.mWallpaperTarget != null && isWallpaperVisible(this.mWallpaperTarget)) {
            z = true;
        }
        boolean visible = z;
        if (WindowManagerDebugConfig.DEBUG_WALLPAPER) {
            str = TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Wallpaper visibility: ");
            stringBuilder.append(visible);
            Slog.v(str, stringBuilder.toString());
        }
        if (visible) {
            if (this.mWallpaperTarget.mWallpaperX >= 0.0f) {
                this.mLastWallpaperX = this.mWallpaperTarget.mWallpaperX;
                this.mLastWallpaperXStep = this.mWallpaperTarget.mWallpaperXStep;
            }
            if (this.mWallpaperTarget.mWallpaperY >= 0.0f) {
                this.mLastWallpaperY = this.mWallpaperTarget.mWallpaperY;
                this.mLastWallpaperYStep = this.mWallpaperTarget.mWallpaperYStep;
            }
            if (this.mWallpaperTarget.mWallpaperDisplayOffsetX != Integer.MIN_VALUE) {
                this.mLastWallpaperDisplayOffsetX = this.mWallpaperTarget.mWallpaperDisplayOffsetX;
            }
            if (this.mWallpaperTarget.mWallpaperDisplayOffsetY != Integer.MIN_VALUE) {
                this.mLastWallpaperDisplayOffsetY = this.mWallpaperTarget.mWallpaperDisplayOffsetY;
            }
        }
        updateWallpaperTokens(visible);
        if (WindowManagerDebugConfig.DEBUG_WALLPAPER_LIGHT) {
            str = TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("New wallpaper: target=");
            stringBuilder.append(this.mWallpaperTarget);
            stringBuilder.append(" prev=");
            stringBuilder.append(this.mPrevWallpaperTarget);
            Slog.d(str, stringBuilder.toString());
        }
    }

    boolean processWallpaperDrawPendingTimeout() {
        if (this.mWallpaperDrawState != 1) {
            return false;
        }
        this.mWallpaperDrawState = 2;
        if (WindowManagerDebugConfig.DEBUG_APP_TRANSITIONS || WindowManagerDebugConfig.DEBUG_WALLPAPER) {
            Slog.v(TAG, "*** WALLPAPER DRAW TIMEOUT");
        }
        if (this.mService.getRecentsAnimationController() != null) {
            this.mService.getRecentsAnimationController().cancelAnimation(2, "wallpaperDrawPendingTimeout");
        }
        return true;
    }

    boolean wallpaperTransitionReady() {
        boolean transitionReady = true;
        boolean wallpaperReady = true;
        int curTokenIndex = this.mWallpaperTokens.size() - 1;
        while (curTokenIndex >= 0 && 1 != null) {
            if (((WallpaperWindowToken) this.mWallpaperTokens.get(curTokenIndex)).hasVisibleNotDrawnWallpaper()) {
                wallpaperReady = false;
                if (this.mWallpaperDrawState != 2) {
                    transitionReady = false;
                }
                if (this.mWallpaperDrawState == 0) {
                    this.mWallpaperDrawState = 1;
                    this.mService.mH.removeMessages(39);
                    this.mService.mH.sendEmptyMessageDelayed(39, 500);
                }
                if (WindowManagerDebugConfig.DEBUG_APP_TRANSITIONS || WindowManagerDebugConfig.DEBUG_WALLPAPER) {
                    String str = TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Wallpaper should be visible but has not been drawn yet. mWallpaperDrawState=");
                    stringBuilder.append(this.mWallpaperDrawState);
                    Slog.v(str, stringBuilder.toString());
                }
            } else {
                curTokenIndex--;
            }
        }
        if (wallpaperReady) {
            this.mWallpaperDrawState = 0;
            this.mService.mH.removeMessages(39);
        }
        return transitionReady;
    }

    void adjustWallpaperWindowsForAppTransitionIfNeeded(DisplayContent dc, ArraySet<AppWindowToken> openingApps) {
        boolean adjust = false;
        if ((dc.pendingLayoutChanges & 4) != 0) {
            adjust = true;
        } else {
            for (int i = openingApps.size() - 1; i >= 0; i--) {
                if (((AppWindowToken) openingApps.valueAt(i)).windowsCanBeWallpaperTarget()) {
                    adjust = true;
                    break;
                }
            }
        }
        if (adjust) {
            adjustWallpaperWindows(dc);
        }
    }

    void addWallpaperToken(WallpaperWindowToken token) {
        this.mWallpaperTokens.add(token);
    }

    void removeWallpaperToken(WallpaperWindowToken token) {
        this.mWallpaperTokens.remove(token);
    }

    Bitmap screenshotWallpaperLocked() {
        if (!this.mService.mPolicy.isScreenOn()) {
            return null;
        }
        WindowState wallpaperWindowState = getTopVisibleWallpaper();
        if (wallpaperWindowState == null) {
            return null;
        }
        Rect bounds = wallpaperWindowState.getBounds();
        bounds.offsetTo(0, 0);
        GraphicBuffer wallpaperBuffer = SurfaceControl.captureLayers(wallpaperWindowState.getSurfaceControl().getHandle(), bounds, 1.0f);
        if (wallpaperBuffer != null) {
            return Bitmap.createHardwareBitmap(wallpaperBuffer);
        }
        Slog.w(TAG, "Failed to screenshot wallpaper");
        return null;
    }

    private WindowState getTopVisibleWallpaper() {
        this.mTmpTopWallpaper = null;
        for (int curTokenNdx = this.mWallpaperTokens.size() - 1; curTokenNdx >= 0; curTokenNdx--) {
            ((WallpaperWindowToken) this.mWallpaperTokens.get(curTokenNdx)).forAllWindows((ToBooleanFunction) new -$$Lambda$WallpaperController$3kGUJhX6nW41Z26JaiCQelxXZr8(this), true);
        }
        return this.mTmpTopWallpaper;
    }

    public static /* synthetic */ boolean lambda$getTopVisibleWallpaper$2(WallpaperController wallpaperController, WindowState w) {
        WindowStateAnimator winAnim = w.mWinAnimator;
        if (winAnim == null || !winAnim.getShown() || winAnim.mLastAlpha <= 0.0f) {
            return false;
        }
        wallpaperController.mTmpTopWallpaper = w;
        return true;
    }

    void dump(PrintWriter pw, String prefix) {
        pw.print(prefix);
        pw.print("mWallpaperTarget=");
        pw.println(this.mWallpaperTarget);
        if (this.mPrevWallpaperTarget != null) {
            pw.print(prefix);
            pw.print("mPrevWallpaperTarget=");
            pw.println(this.mPrevWallpaperTarget);
        }
        pw.print(prefix);
        pw.print("mLastWallpaperX=");
        pw.print(this.mLastWallpaperX);
        pw.print(" mLastWallpaperY=");
        pw.println(this.mLastWallpaperY);
        if (this.mLastWallpaperDisplayOffsetX != Integer.MIN_VALUE || this.mLastWallpaperDisplayOffsetY != Integer.MIN_VALUE) {
            pw.print(prefix);
            pw.print("mLastWallpaperDisplayOffsetX=");
            pw.print(this.mLastWallpaperDisplayOffsetX);
            pw.print(" mLastWallpaperDisplayOffsetY=");
            pw.println(this.mLastWallpaperDisplayOffsetY);
        }
    }
}
