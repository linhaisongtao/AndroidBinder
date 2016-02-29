package com.example.daisongsong.myapplicationselfbinder.date;

import android.os.IInterface;
import android.os.RemoteException;

/**
 * Created by daisongsong on 2016/2/26.
 */
public interface IDateInterface extends IInterface{

    String getDateString() throws RemoteException;
}
