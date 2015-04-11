package com.deston.AutoRecall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

public class PhoneStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (TelephonyManager.ACTION_PHONE_STATE_CHANGED.equals(action)) {
            TelephonyManager tm = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            switch (tm.getCallState()) {
                case TelephonyManager.CALL_STATE_IDLE:
                    HomeActivity.sIsCallActive = false;
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    HomeActivity.sIsCallActive = true;
                    break;
                default:
                    break;
            }
        }
    }
}