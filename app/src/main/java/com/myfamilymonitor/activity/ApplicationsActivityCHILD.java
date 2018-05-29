package com.myfamilymonitor.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.myfamilymonitor.R;
import com.myfamilymonitor.adapter.ApplicationsActivityRCVAdapter;
import com.myfamilymonitor.dao.ApplicationInf;
import com.myfamilymonitor.listners.AppListeners;
import com.myfamilymonitor.listners.NetworkStateReceiver;
import com.myfamilymonitor.utils.Util;

import java.util.ArrayList;
import java.util.List;

public class ApplicationsActivityCHILD extends BaseActivity implements NetworkStateReceiver.NetworkStateReceiverListener, View.OnClickListener, AppListeners.OnItemClickListener {
    private RecyclerView applicationsRCV;
    private String TAG = ApplicationsActivityCHILD.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.applcaitions_activity);
        initViews();
        setListAdapter();
    }

    private void initViews() {
        applicationsRCV = findViewById(R.id.applications_RCV);
    }

    private void setListAdapter() {

        ArrayList<ApplicationInf> mListItems = getInstalledApps();
        Util._db.insertOrUpdateApplicationsInfo(mListItems);
        ArrayList<ApplicationInf> applicationInfArrayListFromDB = Util._db.getAllApplicationsInfo();

        applicationsRCV.setHasFixedSize(true);
        ApplicationsActivityRCVAdapter adapter = new ApplicationsActivityRCVAdapter(ApplicationsActivityCHILD.this, applicationInfArrayListFromDB);
        applicationsRCV.setAdapter(adapter);
        adapter.setClickListener(this);
        LinearLayoutManager llm = new LinearLayoutManager(ApplicationsActivityCHILD.this);
        applicationsRCV.setLayoutManager(llm);

    }

    private ArrayList<ApplicationInf> getInstalledApps() {
        ArrayList<ApplicationInf> applicationInfArrayList = new ArrayList<>();
        List<PackageInfo> packs = getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
            if ((!isSystemPackage(p))) {
                ApplicationInf applicationInf = new ApplicationInf();
                applicationInf.AppName = p.applicationInfo.loadLabel(getPackageManager()).toString();
                applicationInf.Icon = p.applicationInfo.loadIcon(getPackageManager());
                applicationInfArrayList.add(applicationInf);
            }
        }
        return applicationInfArrayList;
    }

    private boolean isSystemPackage(PackageInfo pkgInfo) {
        return (pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void setContentLayout(int layout) {

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

    @Override
    public void onClick(Object object) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("");
        LocalBroadcastManager.getInstance(ApplicationsActivityCHILD.this).registerReceiver(onNotice, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(ApplicationsActivityCHILD.this).unregisterReceiver(onNotice);
    }

    private BroadcastReceiver onNotice = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "onNotice");
        }
    };
}
