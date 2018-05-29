package com.myfamilymonitor.listners;

import android.os.Bundle;
import android.os.Parcelable;

import com.myfamilymonitor.utils.Util;

import java.util.ArrayList;

/**
 * Created by Rajashekar.Nimmala on 5/10/2017.
 */

public class AppListeners {

    public interface getReminderListener extends Parcelable {
        void OnGetReminderListener(String reminderTime);
    }

    public interface getSelectedContacts extends Parcelable {
        void onContactsSelectedListener(Bundle selectedContactObjects);

    }

    public interface SingleDialogListener {
        void onSingleButton(Util.ButtonNavigation value);
    }

    public interface MyListener {
        void onCallBack();
    }

    public interface AppLozicListener {
        void onGroupCreated(ArrayList<Object> objectArrayList);
    }

    public interface OnItemClickListener {
        public void onClick(Object object);
    }

    public interface RecyclerViewOnItemClickListener {
        public void onClick(Object object);
    }

    public interface IGetValue {
        public void getEditTextValue(Object editTextValue, String imageURI);
    }
}
