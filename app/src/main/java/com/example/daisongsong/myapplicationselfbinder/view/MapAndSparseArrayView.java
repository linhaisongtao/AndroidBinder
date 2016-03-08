package com.example.daisongsong.myapplicationselfbinder.view;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daisongsong.myapplicationselfbinder.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by daisongsong on 2016/3/8.
 */
public class MapAndSparseArrayView extends FrameLayout implements View.OnClickListener {
    private static final int MAX = 50_000;

    private TextView mTextViewPerformance;

    private Map<Integer, String> mMap;
    private SparseArray<String> mArray;

    private Handler mHandler = new Handler();

    public MapAndSparseArrayView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setBackgroundColor(Color.LTGRAY);
        setPadding(10, 10, 10, 10);
        LayoutInflater.from(getContext()).inflate(R.layout.view_map_and_sparse_array, this, true);

        mTextViewPerformance = (TextView) findViewById(R.id.mTextViewPerformance);

        findViewById(R.id.mButtonMap).setOnClickListener(this);
        findViewById(R.id.mButtonSparseArray).setOnClickListener(this);
        findViewById(R.id.mButtonMemoryInfo).setOnClickListener(this);
        findViewById(R.id.mButtonClear).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mButtonMap:
                new Thread() {
                    @Override
                    public void run() {
                        super.run();

                        if (mMap == null) {
                            mMap = new HashMap<>();
                        } else {
                            mMap.clear();
                        }
                        final long start = System.currentTimeMillis();
                        for (int i = MAX - 1; i >= 0; --i) {
                            mMap.put(i, String.valueOf(i));
                        }
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                showMemoryInfo();
                                showTimeUsed(start, System.currentTimeMillis());
                            }
                        });
                    }
                }.start();
                break;
            case R.id.mButtonSparseArray:
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        if (mArray == null) {
                            mArray = new SparseArray<String>();
                        } else {
                            mArray.clear();
                        }
                        final long start = System.currentTimeMillis();
                        for (int i = MAX - 1; i >= 0; --i) {
                            mArray.put(i, String.valueOf(i));
                        }
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                showMemoryInfo();
                                showTimeUsed(start, System.currentTimeMillis());
                            }
                        });
                    }
                }.start();
                break;
            case R.id.mButtonMemoryInfo:
                showMemoryInfo();
                break;
            case R.id.mButtonClear:
                if (mMap != null) {
                    mMap.clear();
                }
                mMap = null;

                if (mArray != null) {
                    mArray.clear();
                }
                mArray = null;
                System.gc();
                break;
            default:
                break;
        }
    }

    private void showMemoryInfo() {
        ActivityManager activityManager = (ActivityManager) getContext().getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        mTextViewPerformance.setText(String.format("total=%dMB,available=%dMB", memoryInfo.totalMem >> 23, memoryInfo
                .availMem >> 23));
        Toast.makeText(getContext(), mTextViewPerformance.getText(), Toast.LENGTH_SHORT).show();
    }

    private void showTimeUsed(long start, long end) {
        mTextViewPerformance.setText(String.format("%d", end - start));
    }
}
