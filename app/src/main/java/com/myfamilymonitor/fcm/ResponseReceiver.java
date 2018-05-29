package com.myfamilymonitor.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.myfamilymonitor.R;
import com.myfamilymonitor.activity.SplashActivity;
import com.myfamilymonitor.utils.Constants;

/**
 * Created by rajashekar on 29/12/16.
 */

public class ResponseReceiver extends BroadcastReceiver {
    public static final String ACTION_RESP = "com.bjppartyapp.main.MenuHolderActivity";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals(ACTION_RESP)) {
            Log.e("ResponseReceiver", "onReceive = " + ACTION_RESP);
            String notificationMessage = "";
            if(intent.getExtras()!=null) {
                notificationMessage = intent.getExtras().getString("notificationMessage");
            }
            Intent intent1 = new Intent(context, SplashActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Bundle bundle = new Bundle();
            bundle.putString(Constants.INTENT_KEY, ""/*Constants.NOTIFICATION_FRAGMENT*/);
            intent1.putExtra(Constants.INTENT_KEY, bundle);

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code*/, intent1, PendingIntent.FLAG_ONE_SHOT);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                notificationBuilder.setSmallIcon(R.mipmap.app_logo)
                        .setContentText(notificationMessage)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);
            } else {
                notificationBuilder.setSmallIcon(R.mipmap.app_logo)
                        .setContentText(notificationMessage)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);
            }

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0/*  ID of notification */, notificationBuilder.build());

        }

    }
}
