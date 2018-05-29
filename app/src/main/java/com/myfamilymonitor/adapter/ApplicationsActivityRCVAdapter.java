package com.myfamilymonitor.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.myfamilymonitor.R;
import com.myfamilymonitor.dao.ApplicationInf;
import com.myfamilymonitor.listners.AppListeners;

import java.util.ArrayList;

public class ApplicationsActivityRCVAdapter extends RecyclerView.Adapter<ApplicationsActivityRCVAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ApplicationInf> applicationInfArrayList;
    private AppListeners.OnItemClickListener onItemClickListener;


    public ApplicationsActivityRCVAdapter(Context context, ArrayList<ApplicationInf> callLogsArrayList) {
        this.context = context;
        this.applicationInfArrayList = callLogsArrayList;
    }

    @NonNull
    @Override
    public ApplicationsActivityRCVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.application_activity_rcv_row, parent, false);
        return new ApplicationsActivityRCVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ApplicationsActivityRCVAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final ApplicationInf applicationInf = applicationInfArrayList.get(position);
        holder.phoneNumberTV.setText(applicationInf.AppName);
        Drawable image = applicationInf.Icon;
        holder.appIconIMV.setImageDrawable(image);
        holder.applicationListRowRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null)
                    onItemClickListener.onClick(applicationInfArrayList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return applicationInfArrayList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView phoneNumberTV;
        ImageView appIconIMV;
        Switch enableDisableSW;
        RelativeLayout applicationListRowRL;

        ViewHolder(View view) {
            super(view);
            phoneNumberTV = view.findViewById(R.id.list_app_name);
            appIconIMV = view.findViewById(R.id.app_icon);
            enableDisableSW = view.findViewById(R.id.simpleSwitch);

            applicationListRowRL = view.findViewById(R.id.application_list_row_RL);
        }
    }

    public void setClickListener(AppListeners.OnItemClickListener itemClickListener) {
        this.onItemClickListener = itemClickListener;
    }

}
