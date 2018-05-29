package com.myfamilymonitor.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myfamilymonitor.R;
import com.myfamilymonitor.adapter.CallLogsRCVAdapter;
import com.myfamilymonitor.dao.CallLogs;
import com.myfamilymonitor.listners.AppListeners;
import com.myfamilymonitor.utils.Util;

import java.util.ArrayList;

public class CallLogsFragment extends BaseFragment implements AppListeners.OnItemClickListener {
    private RecyclerView callLogsRCV;
    private String TAG = CallLogsFragment.class.getSimpleName();

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            IntentFilter iff = new IntentFilter(getResources().getString(R.string.call_logs_tab));
            iff.addAction(getResources().getString(R.string.call_logs_tab));
            LocalBroadcastManager.getInstance(getActivity()).registerReceiver(onNotice, iff);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.call_logs_fragment, container, false);
        initViews(view);
        setListAdapter();
        return view;

    }

    private void initViews(View view) {
        callLogsRCV = view.findViewById(R.id.call_logs_RCV);
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

    private void setListAdapter() {
        ArrayList<CallLogs> mListItems = Util._db.getAllCallLogs();
        callLogsRCV.setHasFixedSize(true);
        CallLogsRCVAdapter adapter = new CallLogsRCVAdapter(getActivity(), mListItems);
        callLogsRCV.setAdapter(adapter);
        adapter.setClickListener(this);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        callLogsRCV.setLayoutManager(llm);
    }

    @Override
    public void onClick(Object object) {

    }

    @Override
    public void onPause() {
        super.onPause();
        if (getActivity() != null)
            LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(onNotice);
    }

    private BroadcastReceiver onNotice = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "onReceive VisitorsImages");
            if (intent.getAction() != null && intent.getAction().equalsIgnoreCase(getResources().getString(R.string.call_logs_tab))) {
                setListAdapter();
            }
        }
    };
}
