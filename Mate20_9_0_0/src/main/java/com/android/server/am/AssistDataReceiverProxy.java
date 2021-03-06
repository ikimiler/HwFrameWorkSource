package com.android.server.am;

import android.app.IAssistDataReceiver;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.IBinder.DeathRecipient;
import android.os.RemoteException;
import android.util.Log;
import com.android.server.am.AssistDataRequester.AssistDataRequesterCallbacks;

class AssistDataReceiverProxy implements AssistDataRequesterCallbacks, DeathRecipient {
    private static final String TAG = "ActivityManager";
    private String mCallerPackage;
    private IAssistDataReceiver mReceiver;

    public AssistDataReceiverProxy(IAssistDataReceiver receiver, String callerPackage) {
        this.mReceiver = receiver;
        this.mCallerPackage = callerPackage;
        linkToDeath();
    }

    public boolean canHandleReceivedAssistDataLocked() {
        return true;
    }

    public void onAssistDataReceivedLocked(Bundle data, int activityIndex, int activityCount) {
        if (this.mReceiver != null) {
            try {
                this.mReceiver.onHandleAssistData(data);
            } catch (RemoteException e) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to proxy assist data to receiver in package=");
                stringBuilder.append(this.mCallerPackage);
                Log.w("ActivityManager", stringBuilder.toString(), e);
            }
        }
    }

    public void onAssistScreenshotReceivedLocked(Bitmap screenshot) {
        if (this.mReceiver != null) {
            try {
                this.mReceiver.onHandleAssistScreenshot(screenshot);
            } catch (RemoteException e) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to proxy assist screenshot to receiver in package=");
                stringBuilder.append(this.mCallerPackage);
                Log.w("ActivityManager", stringBuilder.toString(), e);
            }
        }
    }

    public void onAssistRequestCompleted() {
        unlinkToDeath();
    }

    public void binderDied() {
        unlinkToDeath();
    }

    private void linkToDeath() {
        try {
            this.mReceiver.asBinder().linkToDeath(this, 0);
        } catch (RemoteException e) {
            Log.w("ActivityManager", "Could not link to client death", e);
        }
    }

    private void unlinkToDeath() {
        if (this.mReceiver != null) {
            this.mReceiver.asBinder().unlinkToDeath(this, 0);
        }
        this.mReceiver = null;
    }
}
