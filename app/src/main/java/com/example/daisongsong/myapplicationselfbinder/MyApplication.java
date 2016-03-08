package com.example.daisongsong.myapplicationselfbinder;

import android.app.Application;
import android.util.Log;

/**
 * Created by daisongsong on 2016/3/3.
 */
public class MyApplication extends Application{

    public MyApplication() {
        super();
        Log.e("tag", "MyApplication constuctor");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("tag", "MyApplication onCreate");
    }
}
