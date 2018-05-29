package com.myfamilymonitor.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myfamilymonitor.R;
import com.myfamilymonitor.listners.NetworkStateReceiver;
import com.myfamilymonitor.utils.Util;

public class TabBarActivityChildView extends BaseActivity implements NetworkStateReceiver.NetworkStateReceiverListener, View.OnClickListener {
    private String TAG = TabBarActivityChildView.class.getSimpleName();
    private LinearLayout ApplicationLL, SetTimeLL, TrackLocationLL, BrowserLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_bar_activity_child_view);
        initViews();
    }

    private void initViews() {

        // LinearLayout view initialisation 
        ApplicationLL = findViewById(R.id.application_LL);
        SetTimeLL = findViewById(R.id.set_time_LL);
        TrackLocationLL = findViewById(R.id.track_location_LL);
        BrowserLL = findViewById(R.id.browser_LL);

        // TextView view initialisation
        TextView appTV = findViewById(R.id.application_TV);
        TextView setTimeTV = findViewById(R.id.set_time_TV);
        TextView trackLocationTV = findViewById(R.id.track_TV);
        TextView browserTV = findViewById(R.id.browser_TV);


        // TextView setTypeface
        appTV.setTypeface(Util.setProximaNova_Semibold(TabBarActivityChildView.this));
        setTimeTV.setTypeface(Util.setProximaNova_Semibold(TabBarActivityChildView.this));
        trackLocationTV.setTypeface(Util.setProximaNova_Semibold(TabBarActivityChildView.this));
        browserTV.setTypeface(Util.setProximaNova_Semibold(TabBarActivityChildView.this));

        ApplicationLL.setOnClickListener(this);
        SetTimeLL.setOnClickListener(this);
        TrackLocationLL.setOnClickListener(this);
        BrowserLL.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tab_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
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
    public void networkAvailable() {

    }

    @Override
    public void networkUnavailable() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("");
        LocalBroadcastManager.getInstance(TabBarActivityChildView.this).registerReceiver(onNotice, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(TabBarActivityChildView.this).unregisterReceiver(onNotice);
    }

    private BroadcastReceiver onNotice = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "onNotice");
        }
    };

    @Override
    public void onClick(View v) {
        if (v == ApplicationLL) {
            Intent appIntent = new Intent(this, ApplicationsActivityCHILD.class);
            appIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            appIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(appIntent);
            overridePendingTransition(0, 0);
        } else if (v == SetTimeLL) {
            Intent setTimeIntent = new Intent(this, SetTimeActivity.class);
            setTimeIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            setTimeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(setTimeIntent);
            overridePendingTransition(0, 0);
        } else if (v == TrackLocationLL) {
            Intent trackLocationIntent = new Intent(this, TrackLocationActivity.class);
            trackLocationIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            trackLocationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(trackLocationIntent);
            overridePendingTransition(0, 0);
        } else if (v == BrowserLL) {
            Intent browserIntent = new Intent(this, BrowserLimitationActivity.class);
            browserIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(browserIntent);
            overridePendingTransition(0, 0);
        }
    }
}
