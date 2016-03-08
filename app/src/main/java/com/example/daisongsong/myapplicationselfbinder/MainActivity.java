package com.example.daisongsong.myapplicationselfbinder;

import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.daisongsong.myapplicationselfbinder.date.BpDateInterface;
import com.example.daisongsong.myapplicationselfbinder.date.IDateInterface;
import com.example.daisongsong.myapplicationselfbinder.timer.BnTimerInterface;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private ITimeService mITimeService;

    private TextView mTextViewTime;
    private TextView mTextViewTime1;
    private TextView mTextViewTime2;
    private TextView mTextViewExist;
    private TextView mTextViewTimer;
    private IDateInterface mIDateInterface;

    private IBinder mITimerInterface = new BnTimerInterface() {
        @Override
        public void nowTime(final String time) {
            //can not operate View , because not main thread!
            System.out.println("MainActivity.nowTime threadId=" + Thread.currentThread().getId());
            mTextViewTimer.post(new Runnable() {
                @Override
                public void run() {
                    System.out.println("MainActivity.run mainThreadId=" + Thread.currentThread().getId());
                    mTextViewTimer.setText(time);
                }
            });
        }

        @Override
        public void onStop() {
            mTextViewTimer.post(new Runnable() {
                @Override
                public void run() {
                    mTextViewTimer.setText("stopped");
                }
            });
        }
    };

    public MainActivity() {
        super();
        Log.e("tag", "MainActivity constuctor");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("tag", "MainActivity onCreate");
        setContentView(R.layout.activity_main);

        mTextViewTime = (TextView) findViewById(R.id.mTextViewTime);
        mTextViewTime1 = (TextView) findViewById(R.id.mTextViewTime1);
        mTextViewTime2 = (TextView) findViewById(R.id.mTextViewTime2);
        mTextViewExist = (TextView) findViewById(R.id.mTextViewExist);
        mTextViewTimer = (TextView) findViewById(R.id.mTextViewTimer);

        Intent intent = new Intent("aa.bb.cc");
        intent.setPackage("com.example.daisongsong.myapplicationselfbinder");
//        bindService(intent, new ServiceConnection() {
//            @Override
//            public void onServiceConnected(ComponentName name, IBinder service) {
//                mITimeService = new TimeServiceProxy(service);
//            }
//
//            @Override
//            public void onServiceDisconnected(ComponentName name) {
//
//            }
//        }, Context.BIND_AUTO_CREATE);


        findViewById(R.id.mButtonStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mITimeService != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    mTextViewTime.setText("" + sdf.format(new Date(mITimeService.getCurrentTime())));
                } else {
                    mTextViewTime.setText("Error");
                }
            }
        });

        findViewById(R.id.mButtonStart1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mITimeService != null) {
                    mIDateInterface = mITimeService.getDateInterface();
                    BpDateInterface bpDateInterface = (BpDateInterface) mIDateInterface;
                    IBinder binder = bpDateInterface.getRemoteBinder();
                    mTextViewTime1.setText(mIDateInterface
                            + "\nbinder=" + binder);
                } else {
                }
            }
        });

        findViewById(R.id.mButtonStart2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIDateInterface != null) {
                    try {
                        mTextViewTime2.setText(mIDateInterface.getDateString());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        findViewById(R.id.mButtonCheckExist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mITimeService != null) {
                    mTextViewExist.setText("" + mITimeService.isBinderExist(mIDateInterface.asBinder()));
                }
            }
        });

        findViewById(R.id.mButtonTimer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mITimeService != null){
                    mITimeService.startTimer(mITimerInterface);
                }
            }
        });


        findViewById(R.id.mButtonStopTimer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mITimeService != null){
                    mITimeService.stopTimer(mITimerInterface);
                }
            }
        });
    }
}
