package com.myfamilymonitor.intent_service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

public class GetApplicationsInfoIntentService extends IntentService {
    private String TAG = GetCallLogIntentService.class.getSimpleName();

    public GetApplicationsInfoIntentService() {
        super("GetApplicationsInfoIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.e(TAG, "onHandleIntent");
    }

}
