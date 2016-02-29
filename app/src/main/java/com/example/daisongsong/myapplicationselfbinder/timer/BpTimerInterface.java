package com.example.daisongsong.myapplicationselfbinder.timer;

import android.os.IBinder;
import android.os.Parcel;

/**
 * Created by daisongsong on 2016/2/29.
 */
public class BpTimerInterface implements ITimerInterface {
    private IBinder mRemote;

    public BpTimerInterface(IBinder remote) {
        mRemote = remote;
    }

    @Override
    public void nowTime(String time) {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            data.writeString(time);
            mRemote.transact(1, data, reply, 0);
            reply.readException();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            mRemote.transact(2, data, reply, 0);
            reply.readException();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public IBinder asBinder() {
        return mRemote;
    }
}
