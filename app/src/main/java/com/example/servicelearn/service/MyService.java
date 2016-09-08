package com.example.servicelearn.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    public static final String TAG = "bearyangMyService";
    private boolean startFlag;
    private boolean bindFlag;

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
        Log.d(TAG, "onCreate...");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startFlag = true;
        Log.d(TAG, "onStartCommand...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; startFlag && i < 100; i++) {
                    Log.d(TAG, "onStartCommand, i: " + i);
                    if (i == 50) {
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
        startFlag = false;
        Log.d(TAG, "onDestory...");
    }

    @Override
    public IBinder onBind(Intent intent) {
        bindFlag = true;
        Log.d(TAG, "onBind...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; bindFlag && i < 100; i++) {
                    Log.d(TAG, "onBind, i: " + i);
                    if (i == 50) {
                        stopSelf();//not works, only can stop by starting way of startService()
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
        bindFlag = false;
        Log.d(TAG, "onUnbind...");
        return true;
//        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        bindFlag = true;
        Log.d(TAG, "onRebind...");
        super.onRebind(intent);
    }
}
