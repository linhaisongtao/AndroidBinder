package com.example.daisongsong.myapplicationselfbinder;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by daisongsong on 2016/2/26.
 */
public class Service1 extends Service{
    private BnTimeService mBnTimeService = new BnTimeService();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBnTimeService;
    }
}
