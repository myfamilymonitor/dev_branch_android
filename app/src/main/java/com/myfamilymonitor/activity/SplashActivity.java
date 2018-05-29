package com.myfamilymonitor.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.myfamilymonitor.R;
import com.myfamilymonitor.dao.User;
import com.myfamilymonitor.intent_service.GetApplicationsInfoIntentService;
import com.myfamilymonitor.intent_service.GetBrowserHistoryIntentService;
import com.myfamilymonitor.intent_service.GetCallLogIntentService;
import com.myfamilymonitor.intent_service.GetMessagesLogIntentService;
import com.myfamilymonitor.utils.Constants;
import com.myfamilymonitor.utils.Util;

import java.io.File;

import me.leolin.shortcutbadger.ShortcutBadger;

import static java.lang.System.out;

public class SplashActivity extends BaseActivity {
    private String userPhoneNumber;
    private User user;
//    private String AuthKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.CodeFont);
        setContentView(R.layout.activity_splash);
        TextView splashTV = findViewById(R.id.splash_tv);
        splashTV.setTypeface(Util.setProximaNova_Semibold(this));
        ShortcutBadger.applyCount(SplashActivity.this, 0);
        fab.setVisibility(View.GONE);
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "Permission is granted");
                Util.requestCameraPermission(this);
            } else {
                Log.i(TAG, "Permission is revoked");
                Util.requestPermission(this);
            }
        } else {
            final int SPLASH_DISPLAY_LENGTH = 3000;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    SharedPreferences prefs = getSharedPreferences(Constants.APP_PREFERENCE_NAME, MODE_PRIVATE);
                    Boolean isFirstStart = prefs.getBoolean(Constants.LAUNCH_APP_FOR_FIRST_TIME, true);
                    String userStatus = prefs.getString(Constants.USER_STATUS, "");
                    userPhoneNumber = prefs.getString(Constants.USER_PHONE_NUMBER, "");
                    Log.e(TAG, "isFirstStart = " + isFirstStart + " -- userPhoneNumber = " + userPhoneNumber);
                    Log.e(TAG, "SplashActivity------ userStatus = " + userStatus);
                    if (isFirstStart) {
                        File myDir = new File(getCacheDir(), "MyFamilyMonitor");
                        if (!myDir.exists())
                            myDir.mkdirs();
                        Log.e("appDirectory", "MyFamilyMonitor folder = " + out.toString());
                        RegisterUser();
                    } else {
                        user = Util._db.getAppUser();
                        Log.e("isFirstStart", "user = " + user.RoleCode);
                        callServices();
                        Intent intent = new Intent(SplashActivity.this, LogInActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        finish();
                    }
                }
            }, SPLASH_DISPLAY_LENGTH);
        }
    }

    private void callServices() {
        if (user.RoleCode.equalsIgnoreCase("CHILD")) {

            Intent callLogService = new Intent(this, GetCallLogIntentService.class);
            startService(callLogService);

            Intent messagesService = new Intent(this, GetMessagesLogIntentService.class);
            startService(messagesService);

            Intent applicationsService = new Intent(this, GetApplicationsInfoIntentService.class);
            startService(applicationsService);

            Intent browserService = new Intent(this, GetBrowserHistoryIntentService.class);
            startService(browserService);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void RegisterUser() {
        Intent intent = new Intent(SplashActivity.this, UserRegistrationActivity.class);
//        Intent intent = new Intent(SplashActivity.this, TabBarActivityChildView.class);
//        Intent intent = new Intent(SplashActivity.this, TabBarActivityParentView.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    @Override
    public void setContentLayout(int layout) {

    }

    @Override
    public void doRequest(String url) {

    }

    @Override
    public void parseJsonResponse(String response, String requestType) {

    }

    @Override
    public String getValues() {
        return null;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.e("requestCode", " = read and write permission given..!");
//                if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)) {
                Util.requestCameraPermission(this);
//                }
            } else {
                Toast.makeText(SplashActivity.this, "Need your permission to proceed further.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        if (requestCode == 2) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.e("requestCode", " = CAMERA permission given..!");
//                if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG)) {
                Util.requestReadCallLogsPermission(this);
//                }
            } else {
                Toast.makeText(SplashActivity.this, "Need your permission to proceed further.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        if (requestCode == 3) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.e("requestCode", " = CAMERA permission given..!");
//                if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)) {
                Util.requestReadSMSPermission(this);
//                }
            } else {
                Toast.makeText(SplashActivity.this, "Need your permission to proceed further.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        if (requestCode == 4) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.e("requestCode", " = Read SMS logs permission given..!");
                SharedPreferences prefs = getSharedPreferences(Constants.APP_PREFERENCE_NAME, MODE_PRIVATE);
                Boolean isFirstStart = prefs.getBoolean(Constants.LAUNCH_APP_FOR_FIRST_TIME, true);
                String userStatus = prefs.getString(Constants.USER_STATUS, "");
                userPhoneNumber = prefs.getString(Constants.USER_PHONE_NUMBER, "");
                Log.e(TAG, "isFirstStart = " + isFirstStart + " -- userPhoneNumber = " + userPhoneNumber);
                Log.e(TAG, "SplashActivity------ userStatus = " + userStatus);
                if (isFirstStart) {
                    File myDir = new File(getCacheDir(), "MyFamilyMonitor");
                    if (!myDir.exists())
                        myDir.mkdirs();
                    Log.e("appDirectory", "MyFamilyMonitor folder = " + out.toString());
                    RegisterUser();
                } else {
                    user = Util._db.getAppUser();
                    Log.e("isFirstStart", "user = " + user.RoleCode);
                    callServices();
                    Intent intent = new Intent(SplashActivity.this, LogInActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                }
            } else {
                Toast.makeText(SplashActivity.this, "Need your permission to proceed further.", Toast.LENGTH_SHORT).show();
                finish();
            }

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
