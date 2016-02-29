package com.example.daisongsong.myapplicationselfbinder;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

import com.example.daisongsong.myapplicationselfbinder.date.IDateInterface;
import com.example.daisongsong.myapplicationselfbinder.timer.ITimerInterface;

/**
 * Created by daisongsong on 2016/2/26.
 */
public interface ITimeService extends IInterface {

    long getCurrentTime();

    IDateInterface getDateInterface();

    boolean isBinderExist(IBinder iBinder);

    void startTimer(IBinder iTimerInterface);

    void stopTimer(IBinder iTimerInterface);
}
