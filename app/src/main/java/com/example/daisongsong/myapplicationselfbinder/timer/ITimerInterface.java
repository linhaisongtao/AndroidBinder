package com.example.daisongsong.myapplicationselfbinder.timer;

import android.os.IInterface;

/**
 * Created by daisongsong on 2016/2/29.
 */
public interface ITimerInterface extends IInterface {

    void nowTime(String time);

    void onStop();
}
