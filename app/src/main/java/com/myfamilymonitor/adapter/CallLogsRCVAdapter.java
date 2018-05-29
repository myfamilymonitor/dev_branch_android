package com.myfamilymonitor.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myfamilymonitor.R;
import com.myfamilymonitor.dao.CallLogs;
import com.myfamilymonitor.listners.AppListeners;
import com.myfamilymonitor.utils.Util;

import java.util.ArrayList;

public class CallLogsRCVAdapter extends RecyclerView.Adapter<CallLogsRCVAdapter.ViewHolder> {

    private Context context;
    private ArrayList<CallLogs> callLogsArrayList;
    private AppListeners.OnItemClickListener onItemClickListener;


    public CallLogsRCVAdapter(Context context, ArrayList<CallLogs> callLogsArrayList) {
        this.context = context;
        this.callLogsArrayList = callLogsArrayList;
    }

    @NonNull
    @Override
    public CallLogsRCVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.call_logs_list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CallLogsRCVAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final CallLogs callLogs = callLogsArrayList.get(position);
        holder.phoneNumberTV.setText(callLogs.PhoneNumber);
        holder.callTypeTV.setText(callLogs.CallType);
        holder.callDurationTV.setText(callLogs.Duration);
        holder.startTimeTV.setText(Util.convertLongToDate(callLogs.Date));
        if (callLogs.CachedName != null) {
            holder.endTimeTV.setVisibility(View.VISIBLE);
            holder.endTimeTitleTV.setVisibility(View.VISIBLE);
            holder.endTimeTV.setText(callLogs.CachedName);
        } else {
            holder.endTimeTV.setVisibility(View.GONE);
            holder.endTimeTitleTV.setVisibility(View.GONE);
        }
        holder.list_row_RL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null)
                    onItemClickListener.onClick(callLogsArrayList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return callLogsArrayList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView phoneNumberTV, callTypeTV, callDurationTV, startTimeTV, endTimeTV, endTimeTitleTV;
        LinearLayout list_row_RL;

        ViewHolder(View view) {
            super(view);
            phoneNumberTV = view.findViewById(R.id.phone_number_TV);
            callTypeTV = view.findViewById(R.id.call_type_TV);
            callDurationTV = view.findViewById(R.id.call_duration_TV);
            startTimeTV = view.findViewById(R.id.start_time_TV);
            endTimeTV = view.findViewById(R.id.end_time_TV);

            TextView phoneNumberTitleTV = view.findViewById(R.id.ph_num_TV);
            TextView callTypeTitleTV = view.findViewById(R.id.call_typ_TV);
            TextView callDurationTitleTV = view.findViewById(R.id.call_du_TV);
            TextView startTimeTitleTV = view.findViewById(R.id.strt_time_TV);
            endTimeTitleTV = view.findViewById(R.id.e_time_TV);

            phoneNumberTV.setTypeface(Util.setProximaNova_Regular(context));
            callTypeTV.setTypeface(Util.setProximaNova_Regular(context));
            callDurationTV.setTypeface(Util.setProximaNova_Regular(context));
            startTimeTV.setTypeface(Util.setProximaNova_Regular(context));
            endTimeTV.setTypeface(Util.setProximaNova_Regular(context));

            phoneNumberTitleTV.setTypeface(Util.setProximaNova_Semibold(context));
            callTypeTitleTV.setTypeface(Util.setProximaNova_Semibold(context));
            callDurationTitleTV.setTypeface(Util.setProximaNova_Semibold(context));
            startTimeTitleTV.setTypeface(Util.setProximaNova_Semibold(context));
            endTimeTitleTV.setTypeface(Util.setProximaNova_Semibold(context));

            list_row_RL = view.findViewById(R.id.list_row_RL);
        }
    }

    public void setClickListener(AppListeners.OnItemClickListener itemClickListener) {
        this.onItemClickListener = itemClickListener;
    }

}
