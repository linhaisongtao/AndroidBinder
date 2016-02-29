package com.example.daisongsong.myapplicationselfbinder.timer;

import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/**
 * Created by daisongsong on 2016/2/29.
 */
public abstract class BnTimerInterface extends Binder implements ITimerInterface{


    @Override
    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        switch (code){
            case 1:
                String s = data.readString();
                nowTime(s);
                reply.writeNoException();
                break;
            case 2:
                onStop();
                reply.writeNoException();
            default:
                break;
        }
        return super.onTransact(code, data, reply, flags);
    }

    @Override
    public IBinder asBinder() {
        return this;
    }
}
