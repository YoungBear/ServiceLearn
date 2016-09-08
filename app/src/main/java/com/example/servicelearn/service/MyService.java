package com.example.servicelearn.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    public static final String TAG = "bearyangMyService";
    private boolean flag;

    private DownloadBinder mBinder = new DownloadBinder();

    public class DownloadBinder extends Binder {
        public void startDownload() {
            Log.d(TAG, "startDownload...");
        }

        public int getProgress() {
            Log.d(TAG, "getProgress...");
            return 0;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        flag = true;
        Log.d(TAG, "onCreate...");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; flag && i < 100; i++) {
                    Log.d(TAG, "onStartCommand, i: " + i);
                    if (i == 10) {
                        stopSelf();//works, service will be stopped
                    }
                    try {
                        Thread.sleep(1 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        flag = false;
        Log.d(TAG, "onDestory...");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; flag && i < 100; i++) {
                    Log.d(TAG, "onBind, i: " + i);
                    if (i == 10) {
                        stopSelf();//not works
                    }
                    try {
                        Thread.sleep(1 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind...");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d(TAG, "onRebind...");
        super.onRebind(intent);
    }
}
