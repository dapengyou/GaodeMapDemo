package com.test.gaodemapdemo;

import android.app.Application;
import android.content.Context;

/**
 * Created by lady_zhou on 2017/9/5.
 */

public class MyApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getmContext() {
        return mContext;
    }

}
