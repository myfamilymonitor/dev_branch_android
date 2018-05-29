package com.myfamilymonitor.intent_service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

public class IntentServiceToSyncAllDataToServer extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public IntentServiceToSyncAllDataToServer() {
        super("IntentServiceToSyncAllDataToServer");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
