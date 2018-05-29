package com.myfamilymonitor.receiver;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.myfamilymonitor.dao.User;
import com.myfamilymonitor.intent_service.GetCallLogIntentService;
import com.myfamilymonitor.utils.Util;

public class MyCallListener extends BroadcastReceiver {
    TelephonyManager telephonyManager;

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {

        phoneStateListener phoneListener = new phoneStateListener(context);
        telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        assert telephonyManager != null;
        telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);

    }

    class phoneStateListener extends PhoneStateListener {

        private Context mContext;
        private boolean ringing = false;
        private boolean call_received = false;


        //Constructor
        phoneStateListener(Context context) {
            mContext = context;

        }


        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            try {
                User user = Util._db.getAppUser();
                if (user.RoleCode.equalsIgnoreCase("CHILD")) {
                    Intent i = new Intent(mContext, GetCallLogIntentService.class);
                    mContext.startService(i);
                }
               /* switch (state) {
                    case TelephonyManager.CALL_STATE_RINGING:
                        ringing = true;
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                        call_received = true;
                        break;
                    case TelephonyManager.CALL_STATE_IDLE:
                        // If phone was ringing(ringing=true) and not received(call_received=false) , then it is a missed call
                        if (ringing && !call_received) {
                            Toast.makeText(mContext, "missed call : " + incomingNumber, Toast.LENGTH_LONG).show();

                            ringing = false;
                        }
                        call_received = false;
                        break;
                }*/
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
        }
    }
}