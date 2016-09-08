package com.example.servicelearn.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.servicelearn.R;
import com.example.servicelearn.service.MyService;

public class SecondActivity extends Activity {

    public static final String TAG = "bearyangSecondActivity";

    private Button btnBindService;
    private Button btnUnbindService;

    private MyService.DownloadBinder downloadBinder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG, "onServiceConnected, componentName: " + componentName.toString());
            downloadBinder = (MyService.DownloadBinder) iBinder;
            downloadBinder.startDownload();
            downloadBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            //当和service的连接意外丢失时，系统会调用这个方法。如果是客户端解除绑定，系统不会调用这个方法。
            Log.d(TAG, "onServiceDisconnected, componentName: " + componentName.toString());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate of SecondActivity...");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initViews();
    }

    private void initViews() {
        btnBindService = (Button) findViewById(R.id.btn_bind_service);
        btnUnbindService = (Button) findViewById(R.id.btn_unbind_service);

        btnBindService.setOnClickListener(btnOnClickListener);
        btnUnbindService.setOnClickListener(btnOnClickListener);
    }

    private View.OnClickListener btnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_bind_service:
                    Intent bindIntent = new Intent(SecondActivity.this, MyService.class);
                    bindService(bindIntent, connection, BIND_AUTO_CREATE);
                    break;
                case R.id.btn_unbind_service:
                    unbindService(connection);
                    break;
                default:break;
            }
        }
    };
}
