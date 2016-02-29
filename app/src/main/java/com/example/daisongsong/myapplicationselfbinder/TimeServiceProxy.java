package com.example.daisongsong.myapplicationselfbinder;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import com.example.daisongsong.myapplicationselfbinder.date.BpDateInterface;
import com.example.daisongsong.myapplicationselfbinder.date.IDateInterface;
import com.example.daisongsong.myapplicationselfbinder.timer.ITimerInterface;

/**
 * Created by daisongsong on 2016/2/26.
 */
public class TimeServiceProxy implements ITimeService {
    private IBinder mRemoteBinder;

    public TimeServiceProxy(IBinder remoteBinder) {
        mRemoteBinder = remoteBinder;
    }

    @Override
    public long getCurrentTime() {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            mRemoteBinder.transact(1, data, reply, 0);
            reply.readException();
            return reply.readLong();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public IDateInterface getDateInterface() {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            mRemoteBinder.transact(2, data, reply, 0);
            reply.readException();
            IBinder binder = reply.readStrongBinder();
            return new BpDateInterface(binder);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean isBinderExist(IBinder iBinder) {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            data.writeStrongBinder(iBinder);
            mRemoteBinder.transact(3, data, reply, 0);
            reply.readException();
            boolean[] bArr = new boolean[1];
            reply.readBooleanArray(bArr);
            return bArr[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void startTimer(IBinder iTimerInterface) {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            data.writeStrongBinder(iTimerInterface);
            mRemoteBinder.transact(4, data, reply, 0);
            reply.readException();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void stopTimer(IBinder iTimerInterface) {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            data.writeStrongBinder(iTimerInterface);
            mRemoteBinder.transact(5, data, reply, 0);
            reply.readException();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public IBinder asBinder() {
        return mRemoteBinder;
    }
}
