package com.myfamilymonitor.utils;

import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

/**
 * Created by Rajashekar.Nimmala on 1/12/2018.
 */

public class NotificationUtils {
    private Context mContext;

    public NotificationUtils(Context mContext) {
        this.mContext = mContext;
    }

    // Playing notification sound
    public void playNotificationSound() {
        try {
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(mContext, alarmSound);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

