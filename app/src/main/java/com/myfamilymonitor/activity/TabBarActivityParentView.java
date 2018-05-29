package com.myfamilymonitor.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myfamilymonitor.R;
import com.myfamilymonitor.adapter.CustomViewPager;
import com.myfamilymonitor.adapter.PagerAdapter;
import com.myfamilymonitor.fragment.ApplicationsInfoFragment;
import com.myfamilymonitor.fragment.BrowserHistoryFragment;
import com.myfamilymonitor.fragment.CallLogsFragment;
import com.myfamilymonitor.fragment.MessagesLogFragment;
import com.myfamilymonitor.listners.NetworkStateReceiver;
import com.myfamilymonitor.utils.Constants;
import com.myfamilymonitor.utils.Util;

import java.util.List;
import java.util.Vector;

public class TabBarActivityParentView extends BaseActivity implements NetworkStateReceiver.NetworkStateReceiverListener, View.OnClickListener {
    private CustomViewPager viewPager;
    private String TAG = TabBarActivityParentView.class.getSimpleName();
    private LinearLayout appsLL, callsLL, smsLL, webLL;
    private int pageSelectedPosition = 0;
    private ImageView appsIMV, callsIMV, smsIMV, webIMV;
    private TextView appsTV, callsTV, smsTV, webTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_bar_activity_parent_view_new);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.call_logs_tab));
        }
        initViews();
        initialisePaging();
    }

    private void initViews() {

        viewPager = findViewById(R.id.viewpager);

        appsLL = findViewById(R.id.ll_apps);
        callsLL = findViewById(R.id.ll_calls);
        smsLL = findViewById(R.id.ll_sms);
        webLL = findViewById(R.id.ll_web);

        appsIMV = findViewById(R.id.iv_apps);
        callsIMV = findViewById(R.id.iv_calls);
        smsIMV = findViewById(R.id.iv_sms);
        webIMV = findViewById(R.id.iv_web);

        appsTV = findViewById(R.id.tv_apps);
        callsTV = findViewById(R.id.tv_calls);
        smsTV = findViewById(R.id.tv_sms);
        webTV = findViewById(R.id.tv_web);

        appsTV.setTypeface(Util.setProximaNova_Semibold(TabBarActivityParentView.this));
        callsTV.setTypeface(Util.setProximaNova_Semibold(TabBarActivityParentView.this));
        smsTV.setTypeface(Util.setProximaNova_Semibold(TabBarActivityParentView.this));
        webTV.setTypeface(Util.setProximaNova_Semibold(TabBarActivityParentView.this));

        appsLL.setOnClickListener(this);
        callsLL.setOnClickListener(this);
        smsLL.setOnClickListener(this);
        webLL.setOnClickListener(this);
    }

    @Override
    public void setContentLayout(int layout) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
        IntentFilter intentFilter = new IntentFilter(Constants.CHANGE_LANGUAGE);
        intentFilter.addAction(Constants.DISMISS_DIALOG);
        LocalBroadcastManager.getInstance(TabBarActivityParentView.this).registerReceiver(onNotice, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(TabBarActivityParentView.this).unregisterReceiver(onNotice);
    }

    private BroadcastReceiver onNotice = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "onNotice");
        }
    };

    @Override
    public void onClick(View v) {
        if (v == appsLL) {
            changeFragment(0);
        } else if (v == callsLL) {
            changeFragment(1);
        } else if (v == smsLL) {
            changeFragment(2);
        } else if (v == webLL) {
            changeFragment(3);
        }

    }

    @Override
    public void networkAvailable() {

    }

    @Override
    public void networkUnavailable() {

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

    private void initialisePaging() {

        List<Fragment> fragments = new Vector<>();
        fragments.add(Fragment.instantiate(this, ApplicationsInfoFragment.class.getName()));  //1
        fragments.add(Fragment.instantiate(this, CallLogsFragment.class.getName()));  //2
        fragments.add(Fragment.instantiate(this, MessagesLogFragment.class.getName()));   //3
        fragments.add(Fragment.instantiate(this, BrowserHistoryFragment.class.getName()));   //4

        PagerAdapter mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragments);

        viewPager.setAdapter(mPagerAdapter);
        viewPager.requestTransparentRegion(viewPager);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setCurrentItem(pageSelectedPosition);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e("onPageScrolled", "position = " + position);
            }

            @Override
            public void onPageSelected(int position) {
                pageSelectedPosition = position;
                if (position == 0) {
                    Log.i(TAG, "Setting screen name: ApplicationsInfoFragment");
                    appsIMV.setImageResource(R.mipmap.ic_apps_white);
                    callsIMV.setImageResource(R.mipmap.ic_call);
                    smsIMV.setImageResource(R.mipmap.ic_sms);
                    webIMV.setImageResource(R.mipmap.ic_web);

                    appsTV.setTextColor(getResources().getColor(R.color.white));
                    callsTV.setTextColor(getResources().getColor(R.color.colorPrimaryLight));
                    smsTV.setTextColor(getResources().getColor(R.color.colorPrimaryLight));
                    webTV.setTextColor(getResources().getColor(R.color.colorPrimaryLight));
                } else if (position == 1) {
                    Log.i(TAG, "Setting screen name: CallLogsFragment");
                    appsIMV.setImageResource(R.mipmap.ic_apps);
                    callsIMV.setImageResource(R.mipmap.ic_call_white);
                    smsIMV.setImageResource(R.mipmap.ic_sms);
                    webIMV.setImageResource(R.mipmap.ic_web);

                    appsTV.setTextColor(getResources().getColor(R.color.colorPrimaryLight));
                    callsTV.setTextColor(getResources().getColor(R.color.white));
                    smsTV.setTextColor(getResources().getColor(R.color.colorPrimaryLight));
                    webTV.setTextColor(getResources().getColor(R.color.colorPrimaryLight));
                } else if (position == 2) {
                    Log.i(TAG, "Setting screen name: MessagesLogFragment");
                    appsIMV.setImageResource(R.mipmap.ic_apps);
                    callsIMV.setImageResource(R.mipmap.ic_call);
                    smsIMV.setImageResource(R.mipmap.ic_sms_white);
                    webIMV.setImageResource(R.mipmap.ic_web);

                    appsTV.setTextColor(getResources().getColor(R.color.colorPrimaryLight));
                    callsTV.setTextColor(getResources().getColor(R.color.colorPrimaryLight));
                    smsTV.setTextColor(getResources().getColor(R.color.white));
                    webTV.setTextColor(getResources().getColor(R.color.colorPrimaryLight));
                } else if (position == 3) {
                    Log.i(TAG, "Setting screen name: BrowserHistoryFragment");
                    appsIMV.setImageResource(R.mipmap.ic_apps);
                    callsIMV.setImageResource(R.mipmap.ic_call);
                    smsIMV.setImageResource(R.mipmap.ic_sms);
                    webIMV.setImageResource(R.mipmap.ic_web_white);

                    appsTV.setTextColor(getResources().getColor(R.color.colorPrimaryLight));
                    callsTV.setTextColor(getResources().getColor(R.color.colorPrimaryLight));
                    smsTV.setTextColor(getResources().getColor(R.color.colorPrimaryLight));
                    webTV.setTextColor(getResources().getColor(R.color.white));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state != ViewPager.SCROLL_STATE_IDLE) {
                    final int childCount = viewPager.getChildCount();
                    for (int i = 0; i < childCount; i++)
                        viewPager.getChildAt(i).setLayerType(View.LAYER_TYPE_NONE, null);
                }
            }
        });
    }

    public void changeFragment(int position) {
        CustomViewPager pager = findViewById(R.id.viewpager);
        pager.setCurrentItem(position);
    }
}
