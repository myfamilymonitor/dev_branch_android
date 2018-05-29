package com.myfamilymonitor.fcm;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.myfamilymonitor.dao.DataMessage;
import com.myfamilymonitor.database.DataBaseHelper;
import com.myfamilymonitor.utils.Constants;
import com.myfamilymonitor.utils.Util;

/**
 * Created by Rajashekar on 31/05/17.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    private String AuthKey;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        SharedPreferences pref = getApplicationContext().getSharedPreferences(Constants.APP_PREFERENCE_NAME, 0);
        AuthKey = pref.getString("AuthKey", "");
        Log.e(TAG, "AuthKey = " + AuthKey);

        Log.i(TAG, "RemoteMessage : " + remoteMessage.toString());
        Log.i(TAG, "RemoteMessage Notification  : " + remoteMessage.getNotification());
//        Log.i(TAG, "RemoteMessage Notification - Click Action : " + remoteMessage.getNotification().getClickAction());
        Log.i(TAG, "RemoteMessage Data  : " + remoteMessage.getData());
        Log.i(TAG, "RemoteMessage Collapse Key  : " + remoteMessage.getCollapseKey());
        Log.i(TAG, "RemoteMessage Message Id Key  : " + remoteMessage.getMessageId());
        Log.i(TAG, "RemoteMessage Sent Time  : " + remoteMessage.getSentTime());
        Log.i(TAG, "RemoteMessage Ttl : " + remoteMessage.getTtl());
        Log.i(TAG, "RemoteMessage To : " + remoteMessage.getTo());
        Log.i(TAG, "RemoteMessage MessageType : " + remoteMessage.getMessageType());
        Log.d(TAG, "From: " + remoteMessage.getFrom());


        if (remoteMessage.getData() != null) {

            DataMessage dataMessage = new DataMessage();

            dataMessage.MessageBody = remoteMessage.getData().get("MessageBody");
            dataMessage.Title = remoteMessage.getData().get("Title");
            dataMessage.MessageCode = remoteMessage.getData().get("MessageCode");
            dataMessage.MessageReceivedDate = remoteMessage.getData().get("Date");
            dataMessage.MessageType = remoteMessage.getData().get("Type");

            Log.d("MessageBody: ", " = " + dataMessage.MessageBody);
            Log.d("Title: ", " = " + dataMessage.Title);
            Log.d("MessageCode: ", " = " + dataMessage.MessageCode);

        }
    }

    public void initDB(Context ctx) {
        try {
            if (Util._db == null) {
                Util._db = new DataBaseHelper(ctx);
                Util._db.open();
            } else if (!Util._db.isOpen()) {
                Util._db.open();
            }
            Util.BackupDatabase();
            Util.CreateCMSAppFolder(getApplicationContext());
        } catch (Exception e) {

        }
    }
}