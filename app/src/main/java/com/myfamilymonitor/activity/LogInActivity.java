package com.myfamilymonitor.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.myfamilymonitor.R;
import com.myfamilymonitor.dao.User;
import com.myfamilymonitor.intent_service.GetApplicationsInfoIntentService;
import com.myfamilymonitor.intent_service.GetBrowserHistoryIntentService;
import com.myfamilymonitor.intent_service.GetCallLogIntentService;
import com.myfamilymonitor.intent_service.GetMessagesLogIntentService;
import com.myfamilymonitor.utils.Constants;
import com.myfamilymonitor.utils.Util;

public class LogInActivity extends BaseActivity implements View.OnClickListener {
    private EditText passwordET;
    private Button loginBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        passwordET = findViewById(R.id.password_ET);
        loginBTN = findViewById(R.id.login_BTN);
        loginBTN.setOnClickListener(this);
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
    public void onClick(View v) {
        if (v == loginBTN) {
            Util.hide_keyboard_from(LogInActivity.this, passwordET);
            SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_PREFERENCE_NAME, MODE_PRIVATE);
            if (sharedPreferences.getString(Constants.LOGIN, "").equalsIgnoreCase(passwordET.getText().toString())) {
                User user = Util._db.getAppUser();
                if (user != null && user.RoleCode.equalsIgnoreCase("CHILD")) {
                    callServices();
                    Intent intent = new Intent(LogInActivity.this, TabBarActivityChildView.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                } else if (user != null && user.RoleCode.equalsIgnoreCase("PARENT")) {
                    Intent intent = new Intent(LogInActivity.this, TabBarActivityParentView.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                }
            } else {
                Toast.makeText(LogInActivity.this, getResources().getString(R.string.invalid_password), Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void callServices() {
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
