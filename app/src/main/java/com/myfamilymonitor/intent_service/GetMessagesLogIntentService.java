package com.myfamilymonitor.intent_service;

import android.Manifest;
import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.myfamilymonitor.dao.MessageDAO;
import com.myfamilymonitor.utils.Util;

import java.util.ArrayList;

public class GetMessagesLogIntentService extends IntentService {
    private String TAG = GetCallLogIntentService.class.getSimpleName();

    public GetMessagesLogIntentService() {
        super("GetMessagesLogIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.e(TAG, "onHandleIntent");
        getAllSms("inbox");
        getAllSms("sent");
        getAllSms("draft");
    }

    private void getAllSms(String folderName) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            Util.requestReadSMSPermission(this);
        } else {
            ArrayList<MessageDAO> lstSms = new ArrayList<>();
            MessageDAO objSms;
            Uri message = Uri.parse("content://sms/" + folderName);
            ContentResolver cr = this.getContentResolver();

            Cursor cursor = cr.query(message, null, null, null, null);
            if (cursor != null) {
                int totalSMS = cursor.getCount();
                if (cursor.moveToFirst()) {
                    for (int i = 0; i < totalSMS; i++) {
                        objSms = new MessageDAO();
                        objSms.PhoneNumber = (cursor.getString(cursor.getColumnIndexOrThrow("_id")));
                        objSms.Address = (cursor.getString(cursor.getColumnIndexOrThrow("Address")));
                        objSms.Message = (cursor.getString(cursor.getColumnIndexOrThrow("body")));
                        objSms.ReadState = (cursor.getString(cursor.getColumnIndex("read")));
                        objSms.Time = (cursor.getString(cursor.getColumnIndexOrThrow("date")));
                        objSms.MessageType = folderName;
                        lstSms.add(objSms);
                        cursor.moveToNext();
                    }
                }
                cursor.close();
            }
            if (lstSms.size() > 0) {
                Util._db.insertOrUpdateMessagesLog(lstSms);
            }
        }
    }
}
