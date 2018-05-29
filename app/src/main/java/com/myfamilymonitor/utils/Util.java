package com.myfamilymonitor.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.myfamilymonitor.R;
import com.myfamilymonitor.activity.BaseActivity;
import com.myfamilymonitor.activity.SplashActivity;
import com.myfamilymonitor.dao.DataMessage;
import com.myfamilymonitor.dao.User;
import com.myfamilymonitor.database.DataBaseHelper;
import com.myfamilymonitor.intent_service.IntentServiceToSyncAllDataToServer;
import com.myfamilymonitor.listners.AppListeners;
import com.squareup.picasso.Transformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.leolin.shortcutbadger.ShortcutBadger;


/**
 * Created by Rajashekar.Nimmala on 5/3/2017.
 */

public class Util {

    private static String TAG = Util.class.getSimpleName();

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    public static Typeface setAvenirFont(Context context) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "AvenirLTStd_Black.otf");
        return font;
    }

    public static Typeface setAvenirFontMedium(Context context) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "AvenirLTStd_Medium.otf");
        return font;
    }

    public static Typeface setAvenirFontBook(Context context) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "AvenirLTStd_Book.otf");
        return font;
    }

    public static Typeface setProximaNova_Regular(Context context) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "ProximaNova_Regular.otf");
        return font;
    }

    public static Typeface setProximaNova_Semibold(Context context) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "ProximaNova_Semibold.otf");
        return font;
    }


    Context context;
    private static String blockedCharacterSet = " ";
    public static DataBaseHelper _db;
    public static String PREFERENCES = "ParentPreference";
    private WifiManager.MulticastLock mMulticastLock;
    private static int REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private static int REQUEST_CAMERA = 2;
    private static int REQUEST_CALL_LOGS_INFO = 3;
    private static int REQUEST_READ_SMS = 4;

    // Lock for wifi
    private WifiManager.WifiLock mWifiLock;

    //    public  static DBHelper db; //TODO
    public Util(Context context) {
        this.context = context;
    }

    public static Typeface typeface_AvenirLTStd_Black(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/AvenirLTStd_Black.ttf");
    }

    public static Typeface typeface_AvenirLTStd_BlackOblique(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/AvenirLTStd_BlackOblique.ttf");
    }

    public static Typeface typeface_AvenirLTStd_Book(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/AvenirLTStd_Book.ttf");
    }

    public static Typeface typeface_AvenirLTStd_BookOblique(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/AvenirLTStd_BookOblique.ttf");
    }

    public static Typeface typeface_AvenirLTStd_Heavy(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/AvenirLTStd_Heavy.ttf");
    }

    public static Typeface typeface_AvenirLTStd_HeavyOblique(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/AvenirLTStd_HeavyOblique.ttf");
    }

    public static Typeface typeface_AvenirLTStd_Light(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/AvenirLTStd_Light.ttf");
    }

    public static Typeface typeface_AvenirLTStd_LightOblique(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/AvenirLTStd_LightOblique.ttf");
    }

    public static Typeface typeface_AvenirLTStd_Medium(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/AvenirLTStd_Medium.ttf");
    }

    public static Typeface typeface_AvenirLTStd_MediumOblique(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/AvenirLTStd_MediumOblique.ttf");
    }

    public static Typeface typeface_AvenirLTStd_Oblique(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/AvenirLTStd_Oblique.ttf");
    }

    public static Typeface typeface_AvenirLTStd_Roman(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/AvenirLTStd_Roman.ttf");
    }

    //Email Pattern to check the entered email in any of the screen
    public static boolean isValidEmail(final CharSequence enteredEmail) {

        if (TextUtils.isEmpty(enteredEmail)) {
            return false;
        } else {
            return EMAIL_ADDRESS.matcher(enteredEmail).matches();
        }
    }

    //Email Pattern to check the entered email in any of the screen
    public static boolean isValidUserName(final CharSequence enteredUserName) {

        if (TextUtils.isEmpty(enteredUserName)) {
            return false;
        } else {
            return EMAIL_ADDRESS.matcher(enteredUserName).matches();
        }
    }


    public static final Pattern EMAIL_ADDRESS = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\'\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    //to check for the password -- is valid or not for the app requirement
    public static boolean isNotSpecialCharacters(final String enteredPassword) {
        Pattern pattern;
        Matcher matcher;
//        final String pwd_Pattern = "((?=.*[@#$%!^&*()]).{8,})";
        //pattern = Pattern.compile("[^Ws]");
        pattern = Pattern.compile("[a-zA-Z0-9]*");
        matcher = pattern.matcher(enteredPassword);

        return matcher.matches();
    }

    //to check for the password -- is valid or not for the app requirement
    public static boolean isValidSpecialCharacterWithSpace(final String enteredPassword) {
        Pattern pattern;
        Matcher matcher;
//        final String pwd_Pattern = "((?=.*[@#$%!^&*()]).{8,})";
        //pattern = Pattern.compile("[^Ws]");
        pattern = Pattern.compile("[\\s]*");
        matcher = pattern.matcher(enteredPassword);

        return matcher.matches();
    }


    //to hide the keyboard whenever we need to
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        View focusedView = activity.getCurrentFocus();
        if (focusedView != null) {
            inputMethodManager.hideSoftInputFromWindow(
                    focusedView.getWindowToken(), 0);
        }
    }

    //to check the password and confirm password entered by the user while signing to the app
    public static boolean checkPassWordAndConfirmPassword(String password, String confirmPassword) {
        boolean pstatus = false;
        if (confirmPassword != null && password != null) {
            if (password.equals(confirmPassword)) {
                pstatus = true;
            }
        }
        return pstatus;
    }

    //common alert dialog to display the alert in any of the screen
    public static void displayAlert(final Context context, String message) {
        final AlertDialog alertDialog = new AlertDialog.Builder(
                context).create();
        // Setting Dialog Title
        alertDialog.setTitle(context.getResources().getString(R.string.app_name));
        // Setting Dialog MessageDAO
        alertDialog.setMessage(message);
        // Setting Icon to Dialog
//        alertDialog.setIcon(R.drawable.logo);

        //this will not allow user to click outside of dialog and cancel the alert
        alertDialog.setCanceledOnTouchOutside(false);
        // Setting OK Button
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to execute after dialog closed
                //Toast.makeText(context, "You clicked on OK", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });
        // Showing Alert MessageDAO
        alertDialog.show();
    }


    //to get the saltkey for registering the uwer to the app
    public static byte[] getSaltKey() {
        final Random r = new SecureRandom();
        byte[] salt = new byte[32];
        r.nextBytes(salt);
        return salt;
    }

    //checking for the device type whether it is Tablet or Mobile
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    //to restrict the user to not enter space in any of the values
    public static InputFilter inputFilter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            if (source != null && blockedCharacterSet.contains(("" + source))) {
                return "";
            }
            return null;
        }
    };

    public static void BackupDatabase() {
        try {
            boolean success = true;
            File file = null;
            file = new File(Environment.getExternalStorageDirectory() + "");
            if (file.exists()) {
                success = true;
                Log.i(BaseActivity.TAG, "----------is File exits-----");
            } else {
                success = file.mkdir();
                Log.i(BaseActivity.TAG, "----------is File Create-----");
            }
            if (success) {
                //String inFileName = "/data/data/com.sygic.sdk.demo/databases/MOLS_DB.s3db";
                File dbFile = new File(DataBaseHelper.DB_PATH + DataBaseHelper.DATABASE_NAME);
                FileInputStream fis = new FileInputStream(dbFile);


                String outFileName = Environment.getExternalStorageDirectory() + "/MyFamilyMonitorApp_DB.db";
                Log.e(TAG, "outFileName = " + outFileName.toString());
                // Open the empty db as the output stream
                OutputStream output = new FileOutputStream(outFileName);

                // Transfer bytes from the inputfile to the outputfile
                byte[] buffer = new byte[1024];
                int length;
                while ((length = fis.read(buffer)) > 0) {
                    output.write(buffer, 0, length);
                }

                output.flush();
                output.close();
                fis.close();
            }
        } catch (Exception e) {

        }

    }


    public static boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {

                return false;
            }
        }
        return true;
    }

    public static boolean isJSONArray(String test) {
        try {
            new JSONArray(test);
        } catch (JSONException ex) {
            return false;
        }
        return true;
    }

    /*public static String getDeviceType(Context ctx){
        if(isTablet(ctx)){
            return Constants.deviceTypeTablet;
        }
        return Constants.deviceTypeMobile;
    }*/

    public static String getFormData(ContentValues contentValues) throws UnsupportedEncodingException {

        StringBuilder sb = new StringBuilder();
        boolean first = true;

        for (Map.Entry<String, Object> entry : contentValues.valueSet()) {
            if (first)
                first = false;
            else
                sb.append("&");

            sb.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            sb.append("=");
            sb.append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
        }
        return sb.toString();
    }

    public static String NullChecker(String var) {
        if (var == null) {
            return "";
        } else {
            if (var.equals("null")) {
                return "";
            } else {
                return var;
            }
        }
    }

    public static String[] getLiveStreamModes(Class<? extends Enum<?>> e) {
        return Arrays.toString(e.getEnumConstants()).replaceAll("^.|.$", "").split(", ");
    }

    public static boolean isErrorResponse(String resposne) {
        try {
            return new JSONObject(resposne).has("error");
        } catch (Exception e) {

        }
        return false;
    }

    public static boolean isOAuthRequired(String url) {

        switch (url) {
            /*case WebServiceUrls.REGISTER_WITH_USER:
                return false;
            case WebServiceUrls.VERIFY_USER:
                return false;
            case WebServiceUrls.RESEND_OTP:
                return false;
            case WebServiceUrls.RESET_PWD:
                return false;*/
            default:
                return true;
        }
    }

    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static byte[] convertFileToByte(File file) {
        byte[] bytesArray = new byte[(int) file.length()];
        try {
            FileInputStream fis = new FileInputStream(file);
            fis.read(bytesArray); //read file into bytes[]
            fis.close();
        } catch (Exception e) {
        }
        return bytesArray;
    }

    public static byte[] ShortToByte_ByteBuffer_Method(short[] input) {
        int index;
        int iterations = input.length;
        ByteBuffer bb = ByteBuffer.allocate(input.length * 2);
        for (index = 0; index != iterations; ++index) {
            bb.putShort(input[index]);
        }
        return bb.array();
    }

    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        if (context == null) {
            return isInBackground;
        }
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            if (runningProcesses != null) {
                for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                    if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                        for (String activeProcess : processInfo.pkgList) {
                            if (activeProcess.equals(context.getPackageName())) {
                                isInBackground = false;
                            }
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


    public static int convertDPtoPX(int value) {
        return (int) (value * Resources.getSystem().getDisplayMetrics().density);
    }

    public static String getTimeZone(String current_timeZone, Context context) {
     /* String[] timeZone =   context.getResources().getStringArray(R.array.time_zones);
        String[] timeZone_value =   context.getResources().getStringArray(R.array.time_zones_values);
        int i = 0;
        for (String s : timeZone_value){
            if(s.equalsIgnoreCase(current_timeZone)){
                return timeZone[i];
            }
            i++;
        }*/

        if (current_timeZone.equalsIgnoreCase("GMT+05:30")) {
            return "IST";
        } else if (current_timeZone.equalsIgnoreCase("GMT-08:00")) {
            return "PST";
        } else if (current_timeZone.equalsIgnoreCase("GMT-06:00")) {
            return "CST";
        } else if (current_timeZone.equalsIgnoreCase("GMT-05:00")) {
            return "EST";
        }

        return current_timeZone;
    }

    public static String convertDateToStandardForm(String date) {
        Date scheduleFrom = new Date();
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, MMM dd, yyyy | hh:mm aaa", Locale.getDefault());
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            scheduleFrom = simpleDateFormat.parse(date);
            return simpleDateFormat1.format(scheduleFrom);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String convertDateToCalendarFormat(String dateValue) {
        Date scheduleFrom = new Date();
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("EEE, MMM dd, yyyy | hh:mm aaa", Locale.getDefault());
            scheduleFrom = simpleDateFormat.parse(dateValue);
            return simpleDateFormat1.format(scheduleFrom);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void hide_keyboard_from(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void callIntentService(Context context, String TAG) {
        try {
            Intent i = new Intent(Intent.ACTION_SYNC, null, context, IntentServiceToSyncAllDataToServer.class);
            context.startService(i);
        } catch (Exception e) {
            Log.i(TAG, "-----------callErrorLogService Exception--------" + e.getMessage());
        }
    }

    public static void showChangeLanguageAlert(final Context context, final String changedLanguage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Change Language?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        _db.updateRow(DataBaseHelper.TABLE_USER, "LanguageCode", changedLanguage, "CMPUserID", String.valueOf(_db.getAppUserID()));
                        Intent broadcastInt = new Intent();
                        broadcastInt.setAction(Constants.CHANGE_LANGUAGE);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(broadcastInt);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void CreateCMSAppFolder(Context context) {
        Log.e(TAG, "CreateMyFamilyMonitorAppFolder = " + getOutputMediaFile(context));
    }

    /**
     * Create a File for saving an image or video
     */
    public static File getOutputMediaFile(Context context) {
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + context.getPackageName()
                + "/" + Constants.APP_FOLDER_NAME);
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                File wallpaperDirectory = new File(mediaStorageDir.toString());
                wallpaperDirectory.mkdirs();
                return wallpaperDirectory;
            }
        }
        return mediaStorageDir;
    }

    public static void showColseRequestAlert(final Activity context, final String RequestGUID) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Do you want to close this Request?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        context.finish();
                        Util.callIntentService(context, BaseActivity.TAG);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermission(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public static void requestCameraPermission(final Context context) {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.CAMERA)) {
            new AlertDialog.Builder(context)
                    .setMessage(context.getResources().getString(R.string.app_name) + " need Camera access")
                    .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
                        }
                    }).show();
        } else {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
        }
    }

    public static void requestReadSMSPermission(final Context context) {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_SMS)) {
            new AlertDialog.Builder(context)
                    .setMessage(context.getResources().getString(R.string.app_name) + " need read SMS permission")
                    .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_SMS}, REQUEST_READ_SMS);
                        }
                    }).show();
        } else {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.CAMERA}, REQUEST_READ_SMS);
        }
    }

    public static void BuildNotification(Context context, DataMessage dataMessage) {
        if (dataMessage != null && dataMessage.Title.length() > 0 && dataMessage.MessageBody.length() > 0) {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            intent.putExtras(bundle);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            int badgeCount = Util._db.getNotificationUnRedCount();
            Notification.Builder builder = new Notification.Builder(context)
                    .setSmallIcon(R.mipmap.notification_icon)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.notification_icon))
                    .setContentTitle(dataMessage.Title)
                    .setContentText(dataMessage.MessageBody)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notificationNew = builder.build();
            ShortcutBadger.applyNotification(context, notificationNew, badgeCount);
            notificationManager.notify(0, notificationNew);


            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                int notifyID = 1;
                String CHANNEL_ID = "my_channel_01";
                CharSequence name = "Rajya Sampark";
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
                Notification notification = new Notification.Builder(context)
                        .setContentTitle(dataMessage.Title)
                        .setContentText(dataMessage.MessageBody)
                        .setSmallIcon(R.mipmap.notification_icon)
                        .setChannelId(CHANNEL_ID)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .build();
                mNotificationManager.createNotificationChannel(mChannel);
                ShortcutBadger.applyNotification(context, notification, badgeCount);
                mNotificationManager.notify(notifyID, notification);
            }

        }
    }

    public static void requestReadCallLogsPermission(final Context context) {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_CALL_LOG)) {
            new AlertDialog.Builder(context).setMessage(context.getResources().getString(R.string.request_call_log_info))
                    .setPositiveButton(context.getResources().getString(R.string.allow), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context,
                                    new String[]{Manifest.permission.READ_CALL_LOG},
                                    REQUEST_CALL_LOGS_INFO);
                        }
                    }).show();

        } else {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_CALL_LOG}, REQUEST_CALL_LOGS_INFO);
        }
    }

    public static String convertLongToDate(String longDate) {
        if (longDate != null) {
            long val = Long.parseLong(longDate);
            Date date = new Date(val);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateText = df2.format(date);
            System.out.println(dateText);
            return dateText;
        }
        return "";
    }


    public static class CircleTransform implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());

            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }

            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(squaredBitmap,
                    BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);

            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);

            squaredBitmap.recycle();
            return bitmap;
        }

        @Override
        public String key() {
            return "circle";
        }
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public enum ButtonNavigation {
        HOME_SCREEN, SETTINGS, TRY_AGAIN, LOGIN_SCREEN, SAME_SCREEN, EXIT_APP, SIGNOUT, USER_SETTINGS
    }

    public static void showDialogTwoButton(Activity activity, String tittle, String message, String buttonOneText, String buttonTwoText,
                                           final ButtonNavigation leftButton,
                                           final ButtonNavigation rightButton,
                                           final AppListeners.SingleDialogListener buttonListener) {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);
        final FrameLayout frameView = new FrameLayout(activity);
        builder.setView(frameView);

        final android.app.AlertDialog alertDialog = builder.create();
        LayoutInflater inflater = alertDialog.getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.alert_dialog, frameView);
        alertDialog.show();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);

        Button leftBTN = dialoglayout.findViewById(R.id.project_cancelbtn);
        Button rightBTN = dialoglayout.findViewById(R.id.project_okbtn);

        leftBTN.setText(buttonOneText);
        rightBTN.setText(buttonTwoText);

        TextView titleTV = dialoglayout.findViewById(R.id.projectdialogtitle);
        titleTV.setTypeface(Util.getFontTypeForApp(activity.getApplicationContext()), Typeface.BOLD);
        TextView messageTV = dialoglayout.findViewById(R.id.projectdialog_message);
        messageTV.setTypeface(Util.getFontTypeForApp(activity.getApplicationContext()), Typeface.BOLD);
        titleTV.setText(tittle);

        messageTV.setText(message);

        leftBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                buttonListener.onSingleButton(leftButton);
            }
        });

        rightBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                buttonListener.onSingleButton(rightButton);
            }
        });
    }


    private static Typeface getFontTypeForApp(Context applicationContext) {
        return Typeface.createFromAsset(applicationContext.getResources().getAssets(),
                "AvenirNextLTPro_Bold.ttf");
    }

    public static String getUIDate(String mData) {
        String result = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        try {
            Date date = simpleDateFormat.parse(mData);
            result = simpleDateFormat1.format(date);
        } catch (Exception e) {
            Log.e("Raj", "date" + e);
        }
        return result;
    }

    public static String getNotificationUIDate(String mData) {
        String result = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        try {
            Date date = simpleDateFormat.parse(mData);
            result = simpleDateFormat1.format(date);
        } catch (Exception e) {
            Log.e("Raj", "date" + e);
        }
        return result;
    }

    public static String getUIDateNew(String mData) {
        String result = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, MMM dd, yyyy | hh:mm aaa", Locale.getDefault());
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        try {
            Date date = simpleDateFormat.parse(mData);
            result = simpleDateFormat1.format(date);
        } catch (Exception e) {
            Log.e("Raj", "date" + e);
        }
        return result;
    }

    public static void showAlert(final Activity context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Do you want to cancel?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        context.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void showLogOutAlert(final Activity context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Do you want to Logout?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        if (_db.isDBDeleted()) {
                            SharedPreferences sp = context.getSharedPreferences(Constants.APP_PREFERENCE_NAME, Activity.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putBoolean(Constants.LAUNCH_APP_FOR_FIRST_TIME, true);
                            editor.putString("AuthKey", "");
                            editor.apply();
                            Intent intent = new Intent(context, SplashActivity.class);
                            context.startActivity(intent);
                            context.finish();
                        } else {
                            Toast.makeText(context, "Log out Failed!", Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void AuthFailed(final Activity context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Authentication Failed..!")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        if (_db.isDBDeleted()) {
                            SharedPreferences sp = context.getSharedPreferences(Constants.APP_PREFERENCE_NAME, Activity.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putBoolean(Constants.LAUNCH_APP_FOR_FIRST_TIME, true);
                            editor.putString("AuthKey", "");
                            editor.apply();
                            Intent intent = new Intent(context, SplashActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent);
                            context.finish();
                        } else {
                            Toast.makeText(context, "Log out Failed!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            SharedPreferences sp = context.getSharedPreferences(Constants.APP_PREFERENCE_NAME, Activity.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putBoolean(Constants.LAUNCH_APP_FOR_FIRST_TIME, true);
                            editor.putString("AuthKey", "");
                            editor.apply();
                            Intent intent = new Intent(context, SplashActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent);
                            context.finish();
                        }

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void DeclinedUser(final Activity context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Registration Failed..!")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        context.finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    public static String getCurrentDate() {
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Calendar c = Calendar.getInstance();
        return simpleDateFormat1.format(c.getTime());
    }

    public static void getListViewSize(ListView myListView) {
        ListAdapter myListAdapter = myListView.getAdapter();
        if (myListAdapter == null) {
            //do nothing return null
            return;
        }
        //set listAdapter in loop for getting final size
        int totalHeight = 0;
        for (int size = 0; size < myListAdapter.getCount(); size++) {
            View listItem = myListAdapter.getView(size, null, myListView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        //setting listview item in adapter
        ViewGroup.LayoutParams params = myListView.getLayoutParams();
        params.height = totalHeight + (myListView.getDividerHeight() * (myListAdapter.getCount() - 1));
        myListView.setLayoutParams(params);
        // print height of adapter on log
        Log.e("height of listItem:", String.valueOf(totalHeight));
    }

    public static void requestPermission(final Context context) {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(context)
                    .setMessage(context.getResources().getString(R.string.request_external_storage_permision))
                    .setPositiveButton(context.getResources().getString(R.string.allow), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    REQUEST_WRITE_EXTERNAL_STORAGE);
                        }
                    }).show();

        } else {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
        }
    }

    public static String saveImageToInternalStorage(Bitmap bitmapImage, String fileName, Context context) {
        String imageLocation = "";
        File pictureFile = getAppMediaFile(fileName, context);
        if (pictureFile == null) {
            Log.e("saveImage", "Error creating media file, check storage permissions: ");// e.getMessage());
            return null;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
            imageLocation = pictureFile.toString();
        } catch (FileNotFoundException e) {
            Log.e("saveImage", "File not found: " + e.getMessage());
            imageLocation = "";
        } catch (IOException e) {
            Log.e("saveImage", "Error accessing file: " + e.getMessage());
            imageLocation = "";
        }
        Log.e("saveImage", " = " + imageLocation);
        return imageLocation;
    }


    public static String saveImageToInternalStorageNew(BufferedInputStream bis, String fileName, Context context) {
        String imageLocation = "";
        File pictureFile = getAppMediaFile(fileName, context);
        if (pictureFile == null) {
            Log.e("saveImageNew", "Error creating media file, check storage permissions: ");// e.getMessage());
            return null;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            int current = 0;
            byte[] data = new byte[1024];
            while ((current = bis.read(data)) != -1) {
                fos.write(data, 0, current);
            }
            /* Convert the Bytes read to a String. */
            // fos.write(baf.toByteArray());
            fos.flush();
            fos.close();
            imageLocation = pictureFile.toString();
        } catch (FileNotFoundException e) {
            Log.e("saveImageNew", "File not found: " + e.getMessage());
            imageLocation = "";
        } catch (IOException e) {
            Log.e("saveImageNew", "Error accessing file: " + e.getMessage());
            imageLocation = "";
        }
        Log.e("saveImageNew", " = " + imageLocation);
        return imageLocation;
    }

    /**
     * Create a File for saving an image or video
     */
    public static File getAppMediaFile(String fileName, Context context) {
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + context.getPackageName()
                + "/" + Constants.APP_FOLDER_NAME);
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + fileName);
        return mediaFile;
    }

    public static void showCompleteTaskAlert(final Activity context, final String TaskGUID) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Do you want to complete this Task?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        context.finish();
                        Util.callIntentService(context, BaseActivity.TAG);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void showColseTaskAlert(final Activity context, final String TaskGUID) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Do you want to close this Task?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        context.finish();
                        Util.callIntentService(context, BaseActivity.TAG);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void updateUserApplicationStatus(Context context, String AuthKey, User user) {
        SharedPreferences sp = context.getSharedPreferences(Constants.APP_PREFERENCE_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(Constants.LAUNCH_APP_FOR_FIRST_TIME, false);
        editor.putString(Constants.AUTH_KEY, AuthKey);
        editor.putString(Constants.USER_PHONE_NUMBER, user.PhoneNumber);
        editor.putString(Constants.LOGIN, user.Password);
        editor.apply();
    }

    public static boolean checkConnectivity(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo == null) {
            Log.i("checkConnectivity", "not connected");
            return false;
        } else {
            Log.i("checkConnectivity", "netInfo else = " + netInfo.toString());
            return true;
        }
    }

}
