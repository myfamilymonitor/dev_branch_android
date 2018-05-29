package com.myfamilymonitor.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.myfamilymonitor.R;
import com.myfamilymonitor.database.DataBaseHelper;
import com.myfamilymonitor.network.HttpGetData;
import com.myfamilymonitor.network.HttpPostData;
import com.myfamilymonitor.network.IPostResponse;
import com.myfamilymonitor.utils.Constants;
import com.myfamilymonitor.utils.Util;

public abstract class BaseActivity extends AppCompatActivity implements IPostResponse {

    public static final String TAG = Constants.APP_NAME;
    public Toolbar toolbar;
    public LinearLayout lnr_content;
    public FloatingActionButton fab;
    public View v;
    public LayoutInflater inflater;
    public HttpPostData httpPostData;
    public HttpGetData httpGetData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        initDB(this);
        inflater = getLayoutInflater();
        toolbar = findViewById(R.id.toolbar);
        lnr_content = findViewById(R.id.lnr_content);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BaseActivity.this, "On Back Clicked....", Toast.LENGTH_LONG).show();
            }
        });
        fab = (FloatingActionButton) findViewById(R.id.fab);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fab.setImageDrawable(getResources().getDrawable(R.mipmap.app_logo, BaseActivity.this.getTheme()));
        } else {
            fab.setImageDrawable(getResources().getDrawable(R.mipmap.app_logo));
        }
        fab.setVisibility(View.GONE);
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    public abstract void setContentLayout(int layout);

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void initDB(Context ctx) {
        try {
            if (Util._db == null) {
                Util._db = new DataBaseHelper(ctx);
                Util._db.open();
            } else if (!Util._db.isOpen()) {
                Util._db.open();
            }
            Util.BackupDatabase();
            Util.CreateCMSAppFolder(getApplicationContext());
        } catch (Exception e) {
            Log.e(TAG, "Exception = " + e.getMessage());
        }
    }
}

