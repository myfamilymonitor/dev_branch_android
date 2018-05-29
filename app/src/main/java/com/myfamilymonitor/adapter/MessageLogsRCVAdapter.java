package com.myfamilymonitor.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myfamilymonitor.R;
import com.myfamilymonitor.dao.MessageDAO;
import com.myfamilymonitor.listners.AppListeners;
import com.myfamilymonitor.utils.Util;

import java.util.ArrayList;

public class MessageLogsRCVAdapter extends RecyclerView.Adapter<MessageLogsRCVAdapter.ViewHolder> {

    private Context context;
    private ArrayList<MessageDAO> MessageDAOArrayList;
    private AppListeners.OnItemClickListener onItemClickListener;


    public MessageLogsRCVAdapter(Context context, ArrayList<MessageDAO> MessageDAOArrayList) {
        this.context = context;
        this.MessageDAOArrayList = MessageDAOArrayList;
    }

    @NonNull
    @Override
    public MessageLogsRCVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_log_list_row_new, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MessageLogsRCVAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final MessageDAO MessageDAO = MessageDAOArrayList.get(position);
        holder.phoneNumberTV.setText(MessageDAO.PhoneNumber);
        holder.AddressTV.setText(MessageDAO.Address);
        holder.MessageTV.setText(MessageDAO.Message);
        holder.ReadState.setText(MessageDAO.ReadState);
        holder.TimeTV.setText(Util.convertLongToDate(MessageDAO.Time));
        holder.MessageTypeTV.setText(MessageDAO.MessageType);
        holder.list_row_RL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null)
                    onItemClickListener.onClick(MessageDAOArrayList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return MessageDAOArrayList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView phoneNumberTV, AddressTV, MessageTV, TimeTV, MessageTypeTV, ReadState;
        CardView list_row_RL;

        ViewHolder(View view) {
            super(view);
            phoneNumberTV = view.findViewById(R.id.phone_number_value_sms_TV);
            AddressTV = view.findViewById(R.id.address_sms_value_TV);
            MessageTV = view.findViewById(R.id.message_sms_value_TV);
            ReadState = view.findViewById(R.id.readState_sms_value_TV);
            TimeTV = view.findViewById(R.id.time_sms_value_TV);
            MessageTypeTV = view.findViewById(R.id.messageType_sms_value_TV);

            TextView phoneNumberTitleTV = view.findViewById(R.id.phone_number_sms_TV);
            TextView callTypeTitleTV = view.findViewById(R.id.address_sms_TV);
            TextView callDurationTitleTV = view.findViewById(R.id.message_sms_TV);
            TextView startTimeTitleTV = view.findViewById(R.id.readState_sms_TV);
            TextView endTimeTitleTV = view.findViewById(R.id.time_sms_TV);
            TextView messageTypeSmsTV = view.findViewById(R.id.messageType_sms_TV);

            phoneNumberTV.setTypeface(Util.setProximaNova_Regular(context));
            AddressTV.setTypeface(Util.setProximaNova_Regular(context));
            MessageTV.setTypeface(Util.setProximaNova_Regular(context));
            ReadState.setTypeface(Util.setProximaNova_Regular(context));
            TimeTV.setTypeface(Util.setProximaNova_Regular(context));
            MessageTypeTV.setTypeface(Util.setProximaNova_Regular(context));

            phoneNumberTitleTV.setTypeface(Util.setProximaNova_Semibold(context));
            callTypeTitleTV.setTypeface(Util.setProximaNova_Semibold(context));
            callDurationTitleTV.setTypeface(Util.setProximaNova_Semibold(context));
            startTimeTitleTV.setTypeface(Util.setProximaNova_Semibold(context));
            endTimeTitleTV.setTypeface(Util.setProximaNova_Semibold(context));
            messageTypeSmsTV.setTypeface(Util.setProximaNova_Semibold(context));

            list_row_RL = view.findViewById(R.id.message_CV);
        }
    }

    public void setClickListener(AppListeners.OnItemClickListener itemClickListener) {
        this.onItemClickListener = itemClickListener;
    }

}
