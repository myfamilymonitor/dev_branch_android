package com.myfamilymonitor.intent_service;

import android.Manifest;
import android.app.IntentService;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CallLog;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.myfamilymonitor.R;
import com.myfamilymonitor.dao.CallLogs;
import com.myfamilymonitor.utils.Util;

import java.util.ArrayList;

public class GetCallLogIntentService extends IntentService {
    private String TAG = GetCallLogIntentService.class.getSimpleName();

    public GetCallLogIntentService() {
        super("GetCallLogIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.e(TAG, "onHandleIntent");
        getAllCallLogs();
    }

    public void getAllCallLogs() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            Util.requestReadCallLogsPermission(this);
        }else {
            Cursor cur = getApplicationContext().getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null,
                    null, CallLog.Calls.DATE + " DESC");
            if (cur != null && cur.getCount() > 0) {
                ArrayList<CallLogs> callLogs = new ArrayList<>();
                while (cur.moveToNext()) {
                    CallLogs callLog = new CallLogs();
                    callLog.PhoneNumber = cur.getString(cur.getColumnIndex(android.provider.CallLog.Calls.NUMBER));
                    String CallType = cur.getString(cur.getColumnIndex(android.provider.CallLog.Calls.TYPE));
                    if (CallType != null) {
                        if (CallType.equalsIgnoreCase("1")) {
                            CallType = "INCOMING";
                        } else if (CallType.equalsIgnoreCase("2")) {
                            CallType = "OUTGOING";
                        } else if (CallType.equalsIgnoreCase("3")) {
                            CallType = "MISSED";
                        } else if (CallType.equalsIgnoreCase("10")) {
                            CallType = "INCOMING-REJECTED";
                        }
                    } else {
                        CallType = "";
                    }
                    callLog.CallType = CallType;
                    callLog.Date = cur.getString(cur.getColumnIndex(android.provider.CallLog.Calls.DATE));
                    callLog.Duration = cur.getString(cur.getColumnIndex(android.provider.CallLog.Calls.DURATION));
                    callLog.CachedName = cur.getString(cur.getColumnIndex(android.provider.CallLog.Calls.CACHED_NAME));
//                    callLog.PhoneNumber = cur.getString(cur.getColumnIndex(android.provider.CallLog.Calls.NEW));

                    callLogs.add(callLog);
                    Log.e(TAG, "getAllCallLogs" + callLog.PhoneNumber);
                }
                cur.close();
                if (callLogs.size() > 0) {
                    Util._db.insertOrUpdateCallLog(callLogs);
                    Intent broadcastInt = new Intent();
                    broadcastInt.setAction(this.getResources().getString(R.string.call_logs_tab));
                    LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(broadcastInt);
                }
            }
        }
    }

}
