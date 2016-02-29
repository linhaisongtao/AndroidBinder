package com.example.daisongsong.myapplicationselfbinder.date;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/**
 * Created by daisongsong on 2016/2/26.
 */
public class BpDateInterface implements IDateInterface{
    private IBinder mRemoteBinder;

    public BpDateInterface(IBinder remoteBinder) {
        mRemoteBinder = remoteBinder;
    }

    @Override
    public String getDateString() throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        mRemoteBinder.transact(1, data, reply, 0);
        reply.readException();
        return reply.readString();
    }

    @Override
    public IBinder asBinder() {
        return mRemoteBinder;
    }

    public IBinder getRemoteBinder() {
        return mRemoteBinder;
    }
}
