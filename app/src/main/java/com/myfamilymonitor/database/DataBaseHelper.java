package com.myfamilymonitor.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import com.myfamilymonitor.R;
import com.myfamilymonitor.dao.ApplicationInf;
import com.myfamilymonitor.dao.BrowserInfo;
import com.myfamilymonitor.dao.CallLogs;
import com.myfamilymonitor.dao.MessageDAO;
import com.myfamilymonitor.dao.MessageInfo;
import com.myfamilymonitor.dao.User;
import com.myfamilymonitor.utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class DataBaseHelper {
    private final Context context;
    /*
     * Object which used to create or copy database In our application this
     * object is used to copy the database from assets file
     */
    // private String[] imageurls,leadids;

    private DatabaseHelper dataHelper;
    // Object is used to perform database related functions
    private SQLiteDatabase db;
    // Path of database
    public static String DB_PATH = "data/data/com.myfamilymonitor/databases/";
    // Variable which defines the Datbase name
    public static String DATABASE_NAME = "MyFamilyMonitorApp_DB";
    // Variable which defines the Database version
    private static int DATABASE_VERSION = 1;
    // Variables which defines the table name

    private String TAG = DatabaseHelper.class.getSimpleName();
    // TODO Tables for App user
    public static String TABLE_USER = "Users";
    public static String TABLE_CALL_LOGS = "CallLogs";
    public static String TABLE_MESSAGES = "Messages";
    public static String TABLE_APPLICATIONS = "Applications";
    public static String TABLE_BROWSER_HISTORY = "BrowsingHistory";


    // CREATE TABLE FOR USER
    private static final String CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS " + TABLE_USER + "(" + "_id" + " integer NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + "AppUserID" + " TEXT,"
            + "FirstName" + " TEXT,"
            + "LastName" + " TEXT,"
            + "Email" + " TEXT,"
            + "Password" + " TEXT,"
            + "PhoneNumber" + " TEXT,"
            + "RoleCode" + " TEXT,"
            + "PhotoPath" + " TEXT,"
            + "Address" + " TEXT,"
            + "City" + " TEXT,"
            + "State" + " TEXT,"
            + "Pincode" + " TEXT,"
            + "Action" + " TEXT" + ")";


    // CREATE TABLE FOR CALL LOGS
    private static final String CREATE_TABLE_CALL_LOGS = "CREATE TABLE IF NOT EXISTS " + TABLE_CALL_LOGS + "(" + "_id" + " integer NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + "PhoneNumber" + " TEXT,"
            + "CallType" + " TEXT,"
            + "Date" + " TIMESTAMP,"
            + "CachedName" + " TEXT,"
            + "ActionType" + " TEXT,"
            + "Duration" + " TEXT" + ")";

    // CREATE TABLE FOR MESSAGES
    private static final String CREATE_TABLE_MESSAGES = "CREATE TABLE IF NOT EXISTS " + TABLE_MESSAGES + "(" + "_id" + " integer NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + "PhoneNumber" + " TEXT,"
            + "Address" + " TEXT,"
            + "Message" + " TEXT,"
            + "ReadState" + " TEXT,"
            + "Time" + " TIMESTAMP,"
            + "MessageType" + " TEXT" + ")";


    // CREATE TABLE FOR APPLICATIONS
    private static final String CREATE_TABLE_APPLICATIONS = "CREATE TABLE IF NOT EXISTS " + TABLE_APPLICATIONS + "(" + "_id" + " integer NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + "AppPackageName" + " TEXT,"
            + "AppName" + " TEXT,"
            + "WorkedOnTime" + " TEXT,"
            + "StartTime" + " TEXT,"
            + "Icon" + " TEXT,"
            + "EndTime" + " TEXT" + ")";

    // CREATE TABLE FOR BROWSER HISTORY
    private static final String CREATE_TABLE_BROWSING_HISTORY = "CREATE TABLE IF NOT EXISTS " + TABLE_BROWSER_HISTORY + "(" + "_id" + " integer NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + "URL" + " TEXT,"
            + "BrowsingTime" + " TEXT,"
            + "StartTime" + " TEXT,"
            + "EndTime" + " TEXT" + ")";


    // TODO CREATE INDEXES FOR RESPECTIVE TABLES
    private static final String createMyFamilyMonitorUserIndex = "CREATE UNIQUE INDEX UX_Users ON Users(AppUserID)";
    private static final String createMyFamilyMonitorCallLogsIndex = "CREATE UNIQUE INDEX UX_CallLogs ON CallLogs(Date)";
    private static final String createMyFamilyMonitorMessagesIndex = "CREATE UNIQUE INDEX UX_Messages ON Messages(Time)";
    private static final String createMyFamilyMonitorAppllicationIndex = "CREATE UNIQUE INDEX UX_Applications ON Applications(AppPackageName,StartTime)";
    private static final String createMyFamilyMonitorBrowserHistoryIndex = "CREATE UNIQUE INDEX UX_BrowsingHistory ON BrowsingHistory(URL,StartTime)";


//    private static final String createLocationTableIndex = "CREATE UNIQUE INDEX UX_Location ON Location()";

    /**
     * Parameterized Constructor to initialize dataHelper Object
     *
     * @param ctx - stores the current Activity context
     */
    public DataBaseHelper(Context ctx) {
        this.context = ctx;
        dataHelper = new DatabaseHelper(context);
    }

    public User getAppUser() {
        User user = new User();

        try {
            Cursor c = db.rawQuery("select * from " + TABLE_USER, null);
            if (c.moveToFirst()) {

                user.AppUserID = c.getString(c.getColumnIndex("AppUserID"));
                user.FirstName = c.getString(c.getColumnIndex("FirstName"));
                user.LastName = c.getString(c.getColumnIndex("LastName"));
                user.Email = c.getString(c.getColumnIndex("Email"));
                user.Password = c.getString(c.getColumnIndex("Password"));
                user.PhoneNumber = c.getString(c.getColumnIndex("PhoneNumber"));
                user.RoleCode = c.getString(c.getColumnIndex("RoleCode"));
                user.PhotoPath = c.getString(c.getColumnIndex("PhotoPath"));
                user.Address = c.getString(c.getColumnIndex("Address"));
                user.City = c.getString(c.getColumnIndex("City"));
                user.State = c.getString(c.getColumnIndex("State"));
                user.Pincode = c.getString(c.getColumnIndex("Pincode"));
                user.Action = c.getString(c.getColumnIndex("Action"));
            } else {
                return user;
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    // to get all call logs
    public ArrayList<CallLogs> getAllCallLogs() {
        ArrayList<CallLogs> callLogs = new ArrayList<>();
        try {
            Cursor cursor = db.rawQuery("select * from " + TABLE_CALL_LOGS, null);
            if (cursor != null) {
                if (cursor.getCount() != 0) {
                    for (int i = 0; i <= cursor.getCount(); i++) {
                        CallLogs callLog = new CallLogs();
                        if (!cursor.isLast()) {
                            cursor.moveToNext();
                            callLog.PhoneNumber = cursor.getString(cursor.getColumnIndex("PhoneNumber"));
                            callLog.CallType = cursor.getString(cursor.getColumnIndex("CallType"));
                            callLog.Date = cursor.getString(cursor.getColumnIndex("Date"));
                            callLog.CachedName = cursor.getString(cursor.getColumnIndex("CachedName"));
                            callLog.Duration = cursor.getString(cursor.getColumnIndex("Duration"));
                        }
                        callLogs.add(callLog);
                    }
                }
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return callLogs;
    }

    // to get all call logs JSONArray
    public JSONArray getAllCallLogsJArray() {
        JSONArray callLogs = new JSONArray();
        try {
            Cursor cursor = db.rawQuery("select * from " + TABLE_CALL_LOGS, null);
            if (cursor != null) {
                if (cursor.getCount() != 0) {
                    for (int i = 0; i <= cursor.getCount(); i++) {
                        JSONObject callLog = new JSONObject();
                        if (!cursor.isLast()) {
                            cursor.moveToNext();
                            callLog.putOpt("PhoneNumber", cursor.getString(cursor.getColumnIndex("PhoneNumber")));
                            callLog.putOpt("CallType", cursor.getString(cursor.getColumnIndex("CallType")));
                            callLog.putOpt("Date", cursor.getString(cursor.getColumnIndex("Date")));
                            callLog.putOpt("CachedName", cursor.getString(cursor.getColumnIndex("CachedName")));
                            callLog.putOpt("Duration", cursor.getString(cursor.getColumnIndex("Duration")));
                        }
                        callLogs.put(callLog);
                    }
                }
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return callLogs;
    }


    public ArrayList<MessageDAO> getAllMessages() {
        ArrayList<MessageDAO> messageDAOInfos = new ArrayList<>();

        try {
            Cursor cursor = db.rawQuery("select * from " + TABLE_MESSAGES, null);
            if (cursor != null) {
                if (cursor.getCount() != 0) {
                    for (int i = 0; i <= cursor.getCount(); i++) {
                        MessageDAO messageDAOInfo = new MessageDAO();
                        if (!cursor.isLast()) {
                            cursor.moveToNext();
                            messageDAOInfo.PhoneNumber = cursor.getString(cursor.getColumnIndex("PhoneNumber"));
                            messageDAOInfo.Address = cursor.getString(cursor.getColumnIndex("Address"));
                            messageDAOInfo.Message = cursor.getString(cursor.getColumnIndex("Message"));
                            messageDAOInfo.ReadState = cursor.getString(cursor.getColumnIndex("ReadState"));
                            messageDAOInfo.Time = cursor.getString(cursor.getColumnIndex("Time"));
                            messageDAOInfo.MessageType = cursor.getString(cursor.getColumnIndex("MessageType"));
                        }
                        messageDAOInfos.add(messageDAOInfo);
                    }
                }
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return messageDAOInfos;
    }


    // to get all messages
    public JSONArray getAllMessagesJArray() {
        JSONArray messageInfos = new JSONArray();

        try {
            Cursor cursor = db.rawQuery("select * from " + TABLE_MESSAGES, null);
            if (cursor != null) {
                if (cursor.getCount() != 0) {
                    for (int i = 0; i <= cursor.getCount(); i++) {
                        JSONObject messageInfo = new JSONObject();
                        if (!cursor.isLast()) {
                            cursor.moveToNext();
                            messageInfo.putOpt("PhoneNumber", cursor.getString(cursor.getColumnIndex("PhoneNumber")));
                            messageInfo.putOpt("MessageType", cursor.getString(cursor.getColumnIndex("MessageType")));
                            messageInfo.putOpt("SentOrReceivedTime", cursor.getString(cursor.getColumnIndex("SentOrReceivedTime")));
                            messageInfo.putOpt("MessageDAO", cursor.getString(cursor.getColumnIndex("MessageDAO")));
                        }
                        messageInfos.put(messageInfo);
                    }
                }
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return messageInfos;
    }

    // to get all ApplicationsInfo
    public ArrayList<ApplicationInf> getAllApplicationsInfo() {
        ArrayList<ApplicationInf> applicationInfs = new ArrayList<>();

        try {
            Cursor cursor = db.rawQuery("select * from " + TABLE_APPLICATIONS, null);
            if (cursor != null) {
                if (cursor.getCount() != 0) {
                    for (int i = 0; i <= cursor.getCount(); i++) {
                        ApplicationInf applicationInf = new ApplicationInf();
                        if (!cursor.isLast()) {
                            cursor.moveToNext();
                            applicationInf.AppPackageName = cursor.getString(cursor.getColumnIndex("AppPackageName"));
                            applicationInf.AppName = cursor.getString(cursor.getColumnIndex("AppName"));
                            applicationInf.WorkedOnTime = cursor.getString(cursor.getColumnIndex("WorkedOnTime"));
                            applicationInf.StartTime = cursor.getString(cursor.getColumnIndex("Date"));
                            applicationInf.EndTime = cursor.getString(cursor.getColumnIndex("CachedName"));
                        }
                        applicationInfs.add(applicationInf);
                    }
                }
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return applicationInfs;
    }


    public JSONArray getAllApplicationsInfoJArray() {
        JSONArray applicationInfs = new JSONArray();

        try {
            Cursor cursor = db.rawQuery("select * from " + TABLE_APPLICATIONS, null);
            if (cursor != null) {
                if (cursor.getCount() != 0) {
                    for (int i = 0; i <= cursor.getCount(); i++) {
                        JSONObject applicationInf = new JSONObject();
                        if (!cursor.isLast()) {
                            cursor.moveToNext();
                            applicationInf.putOpt("AppPackageName", cursor.getString(cursor.getColumnIndex("AppPackageName")));
                            applicationInf.putOpt("AppName", cursor.getString(cursor.getColumnIndex("AppName")));
                            applicationInf.putOpt("WorkedOnTime", cursor.getString(cursor.getColumnIndex("WorkedOnTime")));
                            applicationInf.putOpt("Date", cursor.getString(cursor.getColumnIndex("Date")));
                            applicationInf.putOpt("CachedName", cursor.getString(cursor.getColumnIndex("CachedName")));
                        }
                        applicationInfs.put(applicationInf);
                    }
                }
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return applicationInfs;
    }


    // to get all BrowsingData
    public ArrayList<BrowserInfo> getBrowsingData() {
        ArrayList<BrowserInfo> browserInfos = new ArrayList<>();

        try {
            Cursor cursor = db.rawQuery("select * from " + TABLE_BROWSER_HISTORY, null);
            if (cursor != null) {
                if (cursor.getCount() != 0) {
                    for (int i = 0; i <= cursor.getCount(); i++) {
                        BrowserInfo browserInfo = new BrowserInfo();
                        if (!cursor.isLast()) {
                            cursor.moveToNext();
                            browserInfo.URL = cursor.getString(cursor.getColumnIndex("URL"));
                            browserInfo.BrowsingTime = cursor.getString(cursor.getColumnIndex("BrowsingTime"));
                            browserInfo.StartTime = cursor.getString(cursor.getColumnIndex("Date"));
                            browserInfo.EndTime = cursor.getString(cursor.getColumnIndex("CachedName"));
                        }
                        browserInfos.add(browserInfo);
                    }
                }
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return browserInfos;
    }


    // to get all BrowsingData
    public JSONArray getBrowsingDataJArray() {
        JSONArray browserInfos = new JSONArray();

        try {
            Cursor cursor = db.rawQuery("select * from " + TABLE_BROWSER_HISTORY, null);
            if (cursor != null) {
                if (cursor.getCount() != 0) {
                    for (int i = 0; i <= cursor.getCount(); i++) {
                        JSONObject browserInfo = new JSONObject();
                        if (!cursor.isLast()) {
                            cursor.moveToNext();
                            browserInfo.putOpt("URL", cursor.getString(cursor.getColumnIndex("URL")));
                            browserInfo.putOpt("BrowsingTime", cursor.getString(cursor.getColumnIndex("BrowsingTime")));
                            browserInfo.putOpt("Date", cursor.getString(cursor.getColumnIndex("Date")));
                            browserInfo.putOpt("CachedName", cursor.getString(cursor.getColumnIndex("CachedName")));
                        }
                        browserInfos.put(browserInfo);
                    }
                }
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return browserInfos;
    }

    public Integer getAppUserID() {
        Integer AppUserID = 0;

        try {
            Cursor c = db.rawQuery("select AppUserID from " + TABLE_USER, null);
            if (c.moveToFirst()) {
                AppUserID = Integer.valueOf(c.getString(c.getColumnIndex("AppUserID")));
            } else {
                AppUserID = 0;
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AppUserID;
    }

    // TODO uncomment when needed.
    public JSONObject getAllDataAndPushToServer(Context context) {
        JSONObject jsonObject = new JSONObject();

        SharedPreferences pref = context.getSharedPreferences(Constants.APP_PREFERENCE_NAME, 0);
        String AuthKey = pref.getString("AuthKey", "");
        Log.e("getTaskDetails", "AuthKey = " + AuthKey);

        //TODO forming array of task and requests with respective to action type.
        JSONArray jArrayCallLogs;
        JSONArray jArrayMessages;
        JSONArray jArrayApplicationsInfo;
        JSONArray jArrayBrowserData;

        jArrayCallLogs = getAllCallLogsJArray();
        jArrayMessages = getAllMessagesJArray();
        jArrayApplicationsInfo = getAllApplicationsInfoJArray();
        jArrayBrowserData = getBrowsingDataJArray();

        try {
            User appUser = getAppUser();
            jsonObject.putOpt("PhoneNumber", appUser.PhoneNumber);
            jsonObject.putOpt("RoleCode", appUser.RoleCode);
            jsonObject.putOpt("DeviceType", Constants.deviceType);
            jsonObject.putOpt("AuthKey", AuthKey);
            jsonObject.putOpt("CallLogs", jArrayCallLogs);
            jsonObject.putOpt("Messages", jArrayMessages);
            jsonObject.putOpt("ApplicationsInfo", jArrayApplicationsInfo);
            jsonObject.putOpt("BrowserData", jArrayBrowserData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public void insertOrUpdateCallLog(ArrayList<CallLogs> callLogs) {
        for (CallLogs logs : callLogs) {

            ContentValues values = new ContentValues();

            values.put("PhoneNumber", logs.PhoneNumber);
            values.put("CallType", logs.CallType);
            values.put("Date", logs.Date);
            values.put("CachedName", logs.CachedName);
            values.put("Duration", logs.Duration);
            values.put("ActionType", "INSERT");

            if (isExists(TABLE_CALL_LOGS, "where Date = '" + logs.Date + "'")) {
                db.update(TABLE_CALL_LOGS, values, "Date = '" + logs.Date + "'", null);
            } else {
                db.insert(TABLE_CALL_LOGS, null, values);
            }
        }
    }

    public void insertOrUpdateMessagesLog(ArrayList<MessageDAO> allSms) {
        for (MessageDAO logs : allSms) {

            ContentValues values = new ContentValues();

            values.put("PhoneNumber", logs.PhoneNumber);
            values.put("Address", logs.Address);
            values.put("MessageDAO", logs.Message);
            values.put("ReadState", logs.ReadState);
            values.put("Time", logs.Time);
            values.put("MessageType", logs.MessageType);

            if (isExists(TABLE_MESSAGES, "where Time = '" + logs.Time + "'")) {
                db.update(TABLE_MESSAGES, values, "Time = '" + logs.Time + "'", null);
            } else {
                db.insert(TABLE_MESSAGES, null, values);
            }
        }
    }

    public void insertOrUpdateApplicationsInfo(ArrayList<ApplicationInf> mListItems) {
        for (ApplicationInf applicationInf : mListItems) {

            ContentValues values = new ContentValues();

            values.put("AppName", applicationInf.AppName);
            values.put("AppPackageName", applicationInf.AppPackageName);
            values.put("EndTime", applicationInf.EndTime);
            values.put("StartTime", applicationInf.StartTime);
            values.put("WorkedOnTime", applicationInf.WorkedOnTime);
            Bitmap bitmap = ((BitmapDrawable) applicationInf.Icon).getBitmap();
            values.put("Icon", bitmap.toString());

            if (isExists(TABLE_APPLICATIONS, "where Time = '" + applicationInf.AppPackageName + "'")) {
                db.update(TABLE_APPLICATIONS, values, "Time = '" + applicationInf.AppPackageName + "'", null);
            } else {
                db.insert(TABLE_APPLICATIONS, null, values);
            }
        }

    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        private Context context;

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        /**
         * This method is used to check whether the database already exist in
         * device.
         *
         * @return dataBase existence
         */
        public boolean checkDataBase() {

            try {
                File f = new File(DB_PATH + DATABASE_NAME);

                return f.exists();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        public void onCreate(SQLiteDatabase db) {
            try {
                // create tables query
                db.execSQL(CREATE_TABLE_USER);
                db.execSQL(CREATE_TABLE_CALL_LOGS);
                db.execSQL(CREATE_TABLE_MESSAGES);
                db.execSQL(CREATE_TABLE_APPLICATIONS);
                db.execSQL(CREATE_TABLE_BROWSING_HISTORY);
                // create indexes query
                db.execSQL(createMyFamilyMonitorUserIndex);
                db.execSQL(createMyFamilyMonitorCallLogsIndex);
                db.execSQL(createMyFamilyMonitorMessagesIndex);
                db.execSQL(createMyFamilyMonitorAppllicationIndex);
                db.execSQL(createMyFamilyMonitorBrowserHistoryIndex);

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("DBHelper", "onCreate Exception " + e);
            }
        }

        /**
         * This method is used to upgrade the database
         */

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL(context.getResources().getString(R.string.DROP_TABLE_IF_EXISTS) + CREATE_TABLE_USER);
                db.execSQL(context.getResources().getString(R.string.DROP_TABLE_IF_EXISTS) + CREATE_TABLE_CALL_LOGS);
                db.execSQL(context.getResources().getString(R.string.DROP_TABLE_IF_EXISTS) + CREATE_TABLE_MESSAGES);
                db.execSQL(context.getResources().getString(R.string.DROP_TABLE_IF_EXISTS) + CREATE_TABLE_APPLICATIONS);
                db.execSQL(context.getResources().getString(R.string.DROP_TABLE_IF_EXISTS) + CREATE_TABLE_BROWSING_HISTORY);

                db.execSQL(context.getResources().getString(R.string.DROP_TABLE_IF_EXISTS) + createMyFamilyMonitorUserIndex);
                db.execSQL(context.getResources().getString(R.string.DROP_TABLE_IF_EXISTS) + createMyFamilyMonitorCallLogsIndex);
                db.execSQL(context.getResources().getString(R.string.DROP_TABLE_IF_EXISTS) + createMyFamilyMonitorMessagesIndex);
                db.execSQL(context.getResources().getString(R.string.DROP_TABLE_IF_EXISTS) + createMyFamilyMonitorAppllicationIndex);
                db.execSQL(context.getResources().getString(R.string.DROP_TABLE_IF_EXISTS) + createMyFamilyMonitorBrowserHistoryIndex);

                onCreate(db);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * This method will open database.if it is not exist than it will copied
     * from existing resource file
     *
     * @return
     * @throws SQLException
     */

    public DataBaseHelper open() throws SQLException {
        try {
            boolean isExist = dataHelper.checkDataBase();
            if (!isExist) {
                db = dataHelper.getWritableDatabase();
            }
            db = dataHelper.getWritableDatabase();
        } catch (Exception e) {
            Log.e(TAG, "Exception = " + e.getMessage());
        }
        return this;
    }

    /**
     * This method is used to call checkDatabase method.
     *
     * @return
     */
    public boolean isDBExist() {

        try {
            return dataHelper.checkDataBase();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This method is used to close the dataHelper object.
     */
    public void close() {
        try {
            db.close();
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isOpen() {
        try {
            return db.isOpen();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean isColumnExists(SQLiteDatabase dbnew,
                                          String columname, String tablename) {
        boolean isclumexists = false;

        try {
            Cursor c = dbnew.rawQuery("select * from " + tablename + " limit 1", null);

            if (c.getColumnIndex(columname) != -1) {
                isclumexists = true;
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return isclumexists;
    }


    // TODO insert/updates application user
    public void InsertUpdateUser(User user) {
        Log.i(TAG, "-----Insert User----:" + user.Email);
        try {
            ContentValues values = new ContentValues();
            values.put("AppUserID", user.AppUserID);
            values.put("FirstName", user.FirstName);
            values.put("LastName", user.LastName);
            values.put("Email", user.Email);
            values.put("Password", user.Password);
            values.put("PhoneNumber", user.PhoneNumber);
            values.put("RoleCode", user.RoleCode);
            values.put("PhotoPath", user.PhotoPath);
            values.put("Address", user.Address);
            values.put("City", user.City);
            values.put("State", user.State);
            values.put("Pincode", user.Pincode);
            values.put("Action", user.Action);
            if (isExists(TABLE_USER, "where AppUserID = '" + user.AppUserID + "'")) {
                db.update(TABLE_USER, values, "AppUserID = '" + user.AppUserID + "'", null);
            } else {
                Log.i(TAG, "-----Insert----:" + user.Email);
                db.insert(TABLE_USER, null, values);
            }

        } catch (Exception e) {
            Log.i(TAG, "-----Insert User Exception----:" + e.getMessage());
        }
    }


    public void InsertUpdateCallLogs(ArrayList<CallLogs> callLogs) {
        for (CallLogs logs : callLogs) {
            ContentValues values = new ContentValues();
            values.put("PhoneNumber", logs.PhoneNumber);
            values.put("CallType", logs.CallType);
            values.put("Date", logs.Date);
            values.put("CachedName", logs.CachedName);
            values.put("Duration", logs.Duration);
            if (isExists(TABLE_CALL_LOGS, "where PhoneNumber = '" + logs.PhoneNumber + "' AND Date = '" + logs.Date + "'")) {
                db.update(TABLE_CALL_LOGS, values, "PhoneNumber = '" + logs.PhoneNumber + "' AND Date = '" + logs.Date + "'", null);
            } else {
                db.insert(TABLE_CALL_LOGS, null, values);
            }
        }
    }

    public void InsertUpdateMessages(ArrayList<MessageInfo> callLogs) {
        for (MessageInfo messageInfo : callLogs) {
            ContentValues values = new ContentValues();
            values.put("PhoneNumber", messageInfo.PhoneNumber);
            values.put("MessageType", messageInfo.MessageType);
            values.put("SentOrReceivedTime", messageInfo.SentOrReceivedTime);
            values.put("MessageDAO", messageInfo.Message);
            if (isExists(TABLE_MESSAGES, "where PhoneNumber = '" + messageInfo.PhoneNumber + "' AND SentOrReceivedTime = '" + messageInfo.SentOrReceivedTime + "'")) {
                db.update(TABLE_MESSAGES, values, "PhoneNumber = '" + messageInfo.PhoneNumber + "' AND SentOrReceivedTime = '" + messageInfo.SentOrReceivedTime + "'", null);
            } else {
                db.insert(TABLE_MESSAGES, null, values);
            }
        }
    }

    public void InsertUpdateApplicationsInfo(ArrayList<ApplicationInf> callLogs) {
        for (ApplicationInf applicationInf : callLogs) {
            ContentValues values = new ContentValues();
            values.put("AppPackageName", applicationInf.AppPackageName);
            values.put("AppName", applicationInf.AppName);
            values.put("WorkedOnTime", applicationInf.WorkedOnTime);
            values.put("Date", applicationInf.StartTime);
            values.put("CachedName", applicationInf.EndTime);
            if (isExists(TABLE_APPLICATIONS, "where PhoneNumber = '" + applicationInf.AppPackageName + "' AND Date = '" + applicationInf.StartTime + "'")) {
                db.update(TABLE_APPLICATIONS, values, "PhoneNumber = '" + applicationInf.AppPackageName + "' AND Date = '" + applicationInf.StartTime + "'", null);
            } else {
                db.insert(TABLE_APPLICATIONS, null, values);
            }
        }
    }

    public void InsertUpdateBrowserInfo(ArrayList<BrowserInfo> callLogs) {
        for (BrowserInfo browserInfo : callLogs) {
            ContentValues values = new ContentValues();
            values.put("URL", browserInfo.URL);
            values.put("BrowsingTime", browserInfo.BrowsingTime);
            values.put("Date", browserInfo.StartTime);
            values.put("CachedName", browserInfo.EndTime);
            if (isExists(TABLE_BROWSER_HISTORY, "where PhoneNumber = '" + browserInfo.URL + "' AND Date = '" + browserInfo.StartTime + "'")) {
                db.update(TABLE_BROWSER_HISTORY, values, "PhoneNumber = '" + browserInfo.URL + "' AND Date = '" + browserInfo.StartTime + "'", null);
            } else {
                db.insert(TABLE_BROWSER_HISTORY, null, values);
            }
        }
    }


    public boolean isExists(String Table_Name, String where_condition) {
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("Select * from " + Table_Name + " " + where_condition, null);
            if (cursor.moveToFirst()) {
                return true;
            }
            cursor.close();
        } catch (Exception e) {
            if (cursor != null) {
                cursor.close();
            }
        }
        return false;
    }

    public boolean isDBDeleted() {
        this.db.beginTransaction();
        try {
            this.db.delete(TABLE_USER, null, null);
            this.db.delete(TABLE_CALL_LOGS, null, null);
            this.db.delete(TABLE_MESSAGES, null, null);
            this.db.delete(TABLE_APPLICATIONS, null, null);
            this.db.delete(TABLE_BROWSER_HISTORY, null, null);
            this.db.setTransactionSuccessful();
            this.db.endTransaction();
            this.db.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void updateRow(String tableName, String key, String value, String whereClause, String whereClauseValue) {
        ContentValues cv = new ContentValues();
        cv.put(key, value);
        db.update(tableName, cv, whereClause + "='" + whereClauseValue + "'", null);
    }

    public Bitmap getImage(String newsFeedID) {

        String qu = "select IMAGE  from NewsFeed where NewsFeedID='" + newsFeedID + "'";
        Cursor cur = db.rawQuery(qu, null);

        if (cur.moveToFirst()) {
            if (cur.getBlob(0) != null) {
                byte[] imgByte = cur.getBlob(0);
                return BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
            }
            cur.close();
        }
        if (cur != null && !cur.isClosed()) {
            cur.close();
        }

        return null;
    }

    public int getNotificationUnRedCount() {

        int unRedCount = 0;

        /*String query = "select * From " + TABLE_NOTIFICATIONS + " WHERE IsRead = '0'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            unRedCount = cursor.getCount();
            cursor.close();
        }*/
        return unRedCount;
    }
}


