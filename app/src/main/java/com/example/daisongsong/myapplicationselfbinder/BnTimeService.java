package com.example.daisongsong.myapplicationselfbinder;

import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import com.example.daisongsong.myapplicationselfbinder.date.BnDateInterface;
import com.example.daisongsong.myapplicationselfbinder.date.IDateInterface;
import com.example.daisongsong.myapplicationselfbinder.timer.BpTimerInterface;
import com.example.daisongsong.myapplicationselfbinder.timer.ITimerInterface;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by daisongsong on 2016/2/26.
 */
public class BnTimeService extends Binder implements ITimeService {
    private BnDateInterface mBnDateInterface;

    private Map<IBinder, TimerThread> mIBinderBpTimerInterfaceMap = new HashMap<>();

    @Override
    public long getCurrentTime() {
        return System.currentTimeMillis();
    }

    @Override
    public IDateInterface getDateInterface() {
        return new BnDateInterface();
    }

    @Override
    public boolean isBinderExist(IBinder iBinder) {
        return iBinder == mBnDateInterface;
    }

    @Override
    public void startTimer(IBinder iTimerInterface) {
        if (mIBinderBpTimerInterfaceMap.get(iTimerInterface) == null) {
            TimerThread thread = new TimerThread(new BpTimerInterface(iTimerInterface));
            mIBinderBpTimerInterfaceMap.put(iTimerInterface, thread);
            thread.start();
        }
    }

    @Override
    public void stopTimer(IBinder iTimerInterface) {
        if(mIBinderBpTimerInterfaceMap.get(iTimerInterface) != null){
            TimerThread thread = mIBinderBpTimerInterfaceMap.remove(iTimerInterface);
            thread.stopTimer();
            thread.getITimerInterface().onStop();
        }
    }

    @Override
    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        if (code == 1) {
            long t = getCurrentTime();
            reply.writeNoException();
            reply.writeLong(t);
            return true;
        } else if (code == 2) {
            reply.writeNoException();
            if (mBnDateInterface == null) {
                mBnDateInterface = new BnDateInterface();
            }
            reply.writeStrongBinder(mBnDateInterface);
            return true;
        } else if (code == 3) {
            IBinder binder = data.readStrongBinder();
            reply.writeNoException();
            reply.writeBooleanArray(new boolean[]{isBinderExist(binder)});
            return true;
        } else if(code == 4){
            IBinder binder = data.readStrongBinder();
            startTimer(binder);
            reply.writeNoException();
            return true;
        }else if(code == 5){
            IBinder binder = data.readStrongBinder();
            stopTimer(binder);
            reply.writeNoException();
            return true;
        }
        return super.onTransact(code, data, reply, flags);
    }

    @Override
    public IBinder asBinder() {
        return this;
    }

    private class TimerThread extends Thread {
        private ITimerInterface mITimerInterface;
        private boolean mRunning = true;
        private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        public TimerThread(ITimerInterface ITimerInterface) {
            mITimerInterface = ITimerInterface;
        }

        @Override
        public void run() {
            super.run();
            while (mRunning) {
                try {
                    mITimerInterface.nowTime(mSimpleDateFormat.format(new Date()));
                    Thread.sleep(1_000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void stopTimer() {
            mRunning = false;
        }

        public ITimerInterface getITimerInterface() {
            return mITimerInterface;
        }
    }
}
