package com.example.daisongsong.myapplicationselfbinder.date;

import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by daisongsong on 2016/2/26.
 */
public class BnDateInterface extends Binder implements IDateInterface{
    SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    public String getDateString() {
        return mSimpleDateFormat.format(new Date());
    }

    @Override
    public IBinder asBinder() {
        return this;
    }

    @Override
    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        if(code == 1){
            String s = getDateString();
            reply.writeNoException();
            reply.writeString(s);
            return true;
        }
        return super.onTransact(code, data, reply, flags);
    }
}
