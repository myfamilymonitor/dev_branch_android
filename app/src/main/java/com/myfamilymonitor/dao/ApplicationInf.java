package com.myfamilymonitor.dao;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class ApplicationInf implements Serializable {
    public String AppPackageName = "",
            AppName = "",
            WorkedOnTime = "",
            StartTime = "",
            EndTime = "";
    public Drawable Icon;
}
