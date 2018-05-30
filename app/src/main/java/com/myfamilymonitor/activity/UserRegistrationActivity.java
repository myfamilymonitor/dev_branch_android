package com.myfamilymonitor.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lamudi.phonefield.PhoneEditText;
import com.myfamilymonitor.R;
import com.myfamilymonitor.dao.User;
import com.myfamilymonitor.intent_service.GetApplicationsInfoIntentService;
import com.myfamilymonitor.intent_service.GetBrowserHistoryIntentService;
import com.myfamilymonitor.intent_service.GetCallLogIntentService;
import com.myfamilymonitor.intent_service.GetMessagesLogIntentService;
import com.myfamilymonitor.network.HttpPostData;
import com.myfamilymonitor.network.WebURLs;
import com.myfamilymonitor.utils.Constants;
import com.myfamilymonitor.utils.Util;

import org.json.JSONException;
import org.json.JSONObject;

public class UserRegistrationActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout accountInfoLL, accountTypeLL, registerParentLL, registerChildLL, alreadyRegisteredParentLL;
    private Button yesBTN, noBTN, parentBTN, childBTN, parentRegisterBTN, childRegisterBTN, alreadyRegisterParentBTN;
    private EditText parentRegisterEmailET, parentRegisterPasswordET, parentRegisterRePasswordET,
            childRegisterEmailET, childRegisterPasswordET, childRegisterRePasswordET, alreadyRegisterEmailET, alreadyRegisterPasswordET;
    private PhoneEditText parentRegisterPhNoET, childRegisterPhNoET, childRegisterParentPhNoET;
    private String gMailAccount;
    private User user;
    private String registrationStatus = "";
    private String userToLogin = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.user_registration_activity);
        initViews();
        gMailAccount = getLogedInAccount();
    }

    private String getLogedInAccount() {
        AccountManager manager = (AccountManager) getSystemService(ACCOUNT_SERVICE);
        String gmail = null;
        if (manager != null) {
            Account[] list = manager.getAccounts();
            for (Account account : list) {
                if (account.type.equalsIgnoreCase("com.google")) {
                    gmail = account.name;
                    break;
                }
            }
        }
        return gmail;
    }


    private void initViews() {
        // linear layout initialisation
        accountInfoLL = findViewById(R.id.account_info_LL);
        accountTypeLL = findViewById(R.id.account_type_LL);
        registerParentLL = findViewById(R.id.register_parent_LL);
        registerChildLL = findViewById(R.id.register_child_LL);
        alreadyRegisteredParentLL = findViewById(R.id.already_registered_parent_LL);


        // Button initialisation
        yesBTN = findViewById(R.id.yes_BTN);
        noBTN = findViewById(R.id.no_BTN);
        parentBTN = findViewById(R.id.parent_BTN);
        childBTN = findViewById(R.id.child_BTN);
        parentRegisterBTN = findViewById(R.id.register_parent_BTN);
        childRegisterBTN = findViewById(R.id.register_child_BTN);
        alreadyRegisterParentBTN = findViewById(R.id.already_register_parent_BTN);


        // EditText initialisation
        parentRegisterEmailET = findViewById(R.id.parent_register_email_ET);
        parentRegisterPasswordET = findViewById(R.id.parent_register_password_ET);
        parentRegisterRePasswordET = findViewById(R.id.parent_register_re_enter_password_ET);
        childRegisterEmailET = findViewById(R.id.child_register_email_ET);
        childRegisterPasswordET = findViewById(R.id.child_register_password_ET);
        childRegisterRePasswordET = findViewById(R.id.child_register_re_enter_password_ET);
        parentRegisterPhNoET = findViewById(R.id.parent_register_ph_no_ET);
        childRegisterPhNoET = findViewById(R.id.child_register_ph_no_ET);
        childRegisterParentPhNoET = findViewById(R.id.child_register_parent_ph_no_ET);
        alreadyRegisterEmailET = findViewById(R.id.already_register_email_ET);
        alreadyRegisterPasswordET = findViewById(R.id.already_register_password_ET);


        TextView accountTV = findViewById(R.id.account_TV);
        TextView hereAsTV = findViewById(R.id.here_as_TV);
        TextView parentRegisterTV = findViewById(R.id.parent_register_TV);
        TextView childParentPhRegisterTV = findViewById(R.id.child_parent_ph_register_TV);
        TextView childRegisterTV = findViewById(R.id.child_register_TV);


        // set font type
        yesBTN.setTypeface(Util.setProximaNova_Regular(this));
        noBTN.setTypeface(Util.setProximaNova_Regular(this));
        parentBTN.setTypeface(Util.setProximaNova_Regular(this));
        childBTN.setTypeface(Util.setProximaNova_Regular(this));
        parentRegisterBTN.setTypeface(Util.setProximaNova_Regular(this));
        childRegisterBTN.setTypeface(Util.setProximaNova_Regular(this));
        alreadyRegisterParentBTN.setTypeface(Util.setProximaNova_Regular(this));
        parentRegisterEmailET.setTypeface(Util.setProximaNova_Regular(this));
        alreadyRegisterEmailET.setTypeface(Util.setProximaNova_Regular(this));
        alreadyRegisterPasswordET.setTypeface(Util.setProximaNova_Regular(this));
        parentRegisterPasswordET.setTypeface(Util.setProximaNova_Regular(this));
        parentRegisterRePasswordET.setTypeface(Util.setProximaNova_Regular(this));
        childRegisterEmailET.setTypeface(Util.setProximaNova_Regular(this));
        childRegisterPasswordET.setTypeface(Util.setProximaNova_Regular(this));
        childRegisterRePasswordET.setTypeface(Util.setProximaNova_Regular(this));
        accountTV.setTypeface(Util.setProximaNova_Semibold(this));
        hereAsTV.setTypeface(Util.setProximaNova_Semibold(this));
        parentRegisterTV.setTypeface(Util.setProximaNova_Regular(this));
        childRegisterTV.setTypeface(Util.setProximaNova_Regular(this));
        childParentPhRegisterTV.setTypeface(Util.setProximaNova_Regular(this));

        // set setOnClickListener
        yesBTN.setOnClickListener(this);
        noBTN.setOnClickListener(this);
        parentBTN.setOnClickListener(this);
        childBTN.setOnClickListener(this);
        parentRegisterBTN.setOnClickListener(this);
        childRegisterBTN.setOnClickListener(this);
        alreadyRegisterParentBTN.setOnClickListener(this);

        accountInfoLL.setVisibility(View.VISIBLE);

        parentRegisterPhNoET.setDefaultCountry("IN");
        childRegisterPhNoET.setDefaultCountry("IN");
        childRegisterParentPhNoET.setDefaultCountry("IN");
    }


    @Override
    public void doRequest(String url) {
        if (url.equalsIgnoreCase(WebURLs.APP_BASE_URL_PARENT)) {
            HttpPostData httpPostData = new HttpPostData("Registering user please wait..!", url, getValues(), this);
            httpPostData.execute();
        } else if (url.equalsIgnoreCase(WebURLs.APP_BASE_URL_CHILD)) {
            HttpPostData httpPostData = new HttpPostData("Registering user please wait..!", url, getValues(), this);
            httpPostData.execute();
        }
    }

    @Override
    public void setContentLayout(int layout) {

    }

    @Override
    public void parseJsonResponse(String response, String requestType) {
        try {
            Log.i(TAG, "-------------SyncDataToServer Response---------- :" + response);
            JSONObject obj = new JSONObject(response);
            Log.e(TAG, "JSONObject result = " + obj.toString());
            if (obj.getString("status").equalsIgnoreCase("Success")) {
                Util.updateUserApplicationStatus(UserRegistrationActivity.this, obj.getString("authkey"), user);
                launchMainActivity();
            }
            if (obj.getString("status").equalsIgnoreCase("Failed") && obj.getString("message").trim().equalsIgnoreCase("Email is already registered")) {
                Log.e(TAG, "Email is already registered");
            } else {
                Toast.makeText(UserRegistrationActivity.this, "Something went wrong..!!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception" + e);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public String getValues() {
        SharedPreferences pref = UserRegistrationActivity.this.getSharedPreferences(Constants.APP_PREFERENCE_NAME, 0);
        String deviceToken = pref.getString(Constants.DEVICE_TOKEN, "");
        Log.e(TAG, "deviceToken = " + deviceToken);

        JSONObject logInObject = new JSONObject();

        try {
            logInObject.put("email", user.Email);
            logInObject.put("password", user.Password);
            if (user.RoleCode.equalsIgnoreCase(Constants.CHILD)) {
                logInObject.put("name", user.Password);
                logInObject.put("childMobileNumber", user.PhoneNumber);
                logInObject.put("parentMobileNumber", user.ParentPhoneNumber);
                logInObject.put("deviceId", deviceToken);
            } else {
                logInObject.put("parentMobileNumber", user.PhoneNumber);
                logInObject.put("deviceId", deviceToken);
            }
        } catch (JSONException e) {
            Log.e(TAG, "JSONException" + e);
        }
        return logInObject.toString();
    }

    @Override
    public void onClick(View v) {
        if (v == yesBTN) {
            registrationStatus = "Yes";
            accountInfoLL.setVisibility(View.GONE);
            accountTypeLL.setVisibility(View.VISIBLE);
            registerParentLL.setVisibility(View.GONE);
            registerChildLL.setVisibility(View.GONE);
            alreadyRegisteredParentLL.setVisibility(View.GONE);
//            login();
        } else if (v == noBTN) {
            accountInfoLL.setVisibility(View.GONE);
            accountTypeLL.setVisibility(View.VISIBLE);
            registerParentLL.setVisibility(View.GONE);
            registerChildLL.setVisibility(View.GONE);
            alreadyRegisteredParentLL.setVisibility(View.GONE);
        } else if (v == parentBTN) {
            if (registrationStatus.equalsIgnoreCase("Yes")) {
                userToLogin = Constants.PARENT;
                accountTypeLL.setVisibility(View.GONE);
                registerParentLL.setVisibility(View.GONE);
                accountInfoLL.setVisibility(View.GONE);
                registerChildLL.setVisibility(View.GONE);
                alreadyRegisteredParentLL.setVisibility(View.VISIBLE);
                if (gMailAccount != null) {
                    alreadyRegisterEmailET.setText(gMailAccount);
                }
            } else {
                accountTypeLL.setVisibility(View.GONE);
                registerParentLL.setVisibility(View.VISIBLE);
                accountInfoLL.setVisibility(View.GONE);
                registerChildLL.setVisibility(View.GONE);
                alreadyRegisteredParentLL.setVisibility(View.GONE);
            }
            if (gMailAccount != null) {
                parentRegisterEmailET.setText(gMailAccount);
            }
        } else if (v == childBTN) {
            if (registrationStatus.equalsIgnoreCase("Yes")) {
                userToLogin = Constants.CHILD;
                accountTypeLL.setVisibility(View.GONE);
                registerChildLL.setVisibility(View.GONE);
                alreadyRegisteredParentLL.setVisibility(View.VISIBLE);
                registerParentLL.setVisibility(View.GONE);
                accountInfoLL.setVisibility(View.GONE);
                if (gMailAccount != null) {
                    alreadyRegisterEmailET.setText(gMailAccount);
                }
            } else {
                accountTypeLL.setVisibility(View.GONE);
                registerChildLL.setVisibility(View.VISIBLE);
                alreadyRegisteredParentLL.setVisibility(View.GONE);
                registerParentLL.setVisibility(View.GONE);
                accountInfoLL.setVisibility(View.GONE);
            }
            if (gMailAccount != null) {
                childRegisterEmailET.setText(gMailAccount);
            }
        } else if (v == parentRegisterBTN) {
            if (validateParentFields()) {
                doRequest(WebURLs.APP_BASE_URL_PARENT);
            }
        } else if (v == childRegisterBTN) {
            if (validateChildFields()) {
                doRequest(WebURLs.APP_BASE_URL_CHILD);
            }
        } else if (v == alreadyRegisterParentBTN) {
            if (validateRegisteredUserFields()) {
                if (userToLogin.equalsIgnoreCase(Constants.CHILD)) {
                    doRequest(WebURLs.APP_BASE_URL_CHILD);
                } else {
                    doRequest(WebURLs.APP_BASE_URL_PARENT);
                }
            }
        }
    }

    private void launchMainActivity() {
        if (user != null && user.RoleCode.equalsIgnoreCase(Constants.CHILD)) {
            callServices();
            Intent intent = new Intent(UserRegistrationActivity.this, TabBarActivityChildView.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        } else if (user != null && user.RoleCode.equalsIgnoreCase(Constants.PARENT)) {
            Intent intent = new Intent(UserRegistrationActivity.this, TabBarActivityParentView.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        }
    }

    /*private void login() {
        Intent intent = new Intent(UserRegistrationActivity.this, LogInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }*/
    private boolean validateChildFields() {
        if (Util.NullChecker(childRegisterEmailET.getText().toString()).isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.please_enter_email_id), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Util.isValidEmail(childRegisterEmailET.getText().toString())) {
            Toast.makeText(this, getResources().getString(R.string.please_enter_valid_email_id), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (Util.NullChecker(childRegisterPasswordET.getText().toString()).isEmpty() && Util.NullChecker(childRegisterRePasswordET.getText().toString()).isEmpty()) {
            if (!childRegisterPasswordET.getText().toString().equalsIgnoreCase(childRegisterRePasswordET.getText().toString())) {
                Toast.makeText(this, getResources().getString(R.string.please_enter_password), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if (childRegisterPhNoET.getPhoneNumber().length() == 0 && !childRegisterPhNoET.isValid()) {
            Toast.makeText(this, getResources().getString(R.string.please_enter_phone_number), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (childRegisterParentPhNoET.getPhoneNumber().length() == 0 && !childRegisterParentPhNoET.isValid()) {
            Toast.makeText(this, getResources().getString(R.string.please_enter_parent_phone_number), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            user = new User();
            user.RoleCode = Constants.CHILD;
            user.PhoneNumber = childRegisterPhNoET.getPhoneNumber();
            user.Email = childRegisterEmailET.getText().toString();
            user.Password = childRegisterPasswordET.getText().toString();
            user.ParentPhoneNumber = childRegisterParentPhNoET.getPhoneNumber();
            Util._db.InsertUpdateUser(user);
            return true;
        }
    }

    private boolean validateParentFields() {
        if (Util.NullChecker(parentRegisterEmailET.getText().toString()).isEmpty() && Util.isValidEmail(parentRegisterEmailET.getText().toString())) {
            Toast.makeText(this, getResources().getString(R.string.please_enter_email_id), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (parentRegisterPasswordET.getText().toString().length() > 5 && parentRegisterRePasswordET.getText().toString().length() > 5) {
            if (!parentRegisterPasswordET.getText().toString().equalsIgnoreCase(parentRegisterRePasswordET.getText().toString())) {
                Toast.makeText(this, getResources().getString(R.string.please_enter_password), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if (!parentRegisterPhNoET.isValid()) {
            Toast.makeText(this, getResources().getString(R.string.please_enter_phone_number), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            user = new User();
            user.RoleCode = Constants.PARENT;
            user.PhoneNumber = parentRegisterPhNoET.getPhoneNumber();
            user.Email = parentRegisterEmailET.getText().toString();
            user.Password = parentRegisterPasswordET.getText().toString();
            Util._db.InsertUpdateUser(user);
            return true;
        }
    }


    private boolean validateRegisteredUserFields() {
        if (Util.NullChecker(alreadyRegisterEmailET.getText().toString()).isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.please_enter_email_id), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Util.isValidEmail(alreadyRegisterEmailET.getText().toString())) {
            Toast.makeText(this, getResources().getString(R.string.please_enter_valid_email_id), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (alreadyRegisterPasswordET.getText().toString().length() > 0) {
            Toast.makeText(this, getResources().getString(R.string.please_enter_password), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            user = new User();
            user.RoleCode = userToLogin;
            user.Email = parentRegisterEmailET.getText().toString();
            user.Password = parentRegisterPasswordET.getText().toString();
            Util._db.InsertUpdateUser(user);
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        if (accountInfoLL.getVisibility() == View.VISIBLE) {
            registrationStatus = "";
            super.onBackPressed();
        } else if (accountTypeLL.getVisibility() == View.VISIBLE) {
            registrationStatus = "";
            accountInfoLL.setVisibility(View.VISIBLE);
            accountTypeLL.setVisibility(View.GONE);
            registerParentLL.setVisibility(View.GONE);
            registerChildLL.setVisibility(View.GONE);
            alreadyRegisteredParentLL.setVisibility(View.GONE);
        } else if (registerParentLL.getVisibility() == View.VISIBLE) {
            registrationStatus = "";
            accountInfoLL.setVisibility(View.GONE);
            accountTypeLL.setVisibility(View.VISIBLE);
            registerParentLL.setVisibility(View.GONE);
            registerChildLL.setVisibility(View.GONE);
            alreadyRegisteredParentLL.setVisibility(View.GONE);
        } else if (registerChildLL.getVisibility() == View.VISIBLE) {
            registrationStatus = "";
            accountInfoLL.setVisibility(View.GONE);
            accountTypeLL.setVisibility(View.VISIBLE);
            registerParentLL.setVisibility(View.GONE);
            registerChildLL.setVisibility(View.GONE);
            alreadyRegisteredParentLL.setVisibility(View.GONE);
        } else if (alreadyRegisteredParentLL.getVisibility() == View.VISIBLE) {
            accountInfoLL.setVisibility(View.GONE);
            alreadyRegisteredParentLL.setVisibility(View.GONE);
            accountTypeLL.setVisibility(View.VISIBLE);
            registerParentLL.setVisibility(View.GONE);
            registerChildLL.setVisibility(View.GONE);
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
