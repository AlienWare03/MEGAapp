package com.projectmega.menu;

import android.app.Application;
import android.content.Context;

/**
 * Created by Justin on 1/13/2016.
 */
public class MyApplication extends Application {
    private static MyApplication sInstance;

    public static MyApplication getInstance(){
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance=this;
    }
    public static Context getAppContext(){
        return sInstance.getApplicationContext();
    }
}
