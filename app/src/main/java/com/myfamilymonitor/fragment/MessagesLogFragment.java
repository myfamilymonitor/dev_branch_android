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
import com.myfamilymonitor.adapter.MessageLogsRCVAdapter;
import com.myfamilymonitor.dao.MessageDAO;
import com.myfamilymonitor.listners.AppListeners;
import com.myfamilymonitor.utils.Util;

import java.util.ArrayList;

public class MessagesLogFragment extends BaseFragment implements AppListeners.OnItemClickListener {
    private RecyclerView messagesRCV;
    private String TAG = CallLogsFragment.class.getSimpleName();

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            IntentFilter iff = new IntentFilter(getResources().getString(R.string.messages_tab));
            iff.addAction(getResources().getString(R.string.messages_tab));
            LocalBroadcastManager.getInstance(getActivity()).registerReceiver(onNotice, iff);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.messages_log_fragment, container, false);
        initViews(view);
        setListAdapter();
        return view;

    }

    private void initViews(View view) {
        messagesRCV = view.findViewById(R.id.messages_log_RCV);
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
        ArrayList<MessageDAO> mListItems = Util._db.getAllMessages();
        messagesRCV.setHasFixedSize(true);
        MessageLogsRCVAdapter adapter = new MessageLogsRCVAdapter(getActivity(), mListItems);
        messagesRCV.setAdapter(adapter);
        adapter.setClickListener(this);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        messagesRCV.setLayoutManager(llm);
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
