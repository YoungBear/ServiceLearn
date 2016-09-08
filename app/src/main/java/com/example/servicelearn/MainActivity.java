package com.example.servicelearn;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.servicelearn.activity.SecondActivity;
import com.example.servicelearn.service.MyService;

public class MainActivity extends Activity {
    public static final String TAG = "bearyangMainActivity";

    private Button btnStartService;
    private Button btnStopService;
    private Button btnBindService;
    private Button btnUnbindService;
    private Button btnStartSecondActivity;

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
        Log.d(TAG, "onCreate of MainActivity...");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }
    private void initViews() {
        btnStartService = (Button) findViewById(R.id.btn_start_service);
        btnStopService = (Button) findViewById(R.id.btn_stop_service);
        btnBindService = (Button) findViewById(R.id.btn_bind_service);
        btnUnbindService = (Button) findViewById(R.id.btn_unbind_service);

        btnStartSecondActivity = (Button) findViewById(R.id.btn_start_second_activity);

        btnStartService.setOnClickListener(btnOnClickListener);
        btnStopService.setOnClickListener(btnOnClickListener);
        btnBindService.setOnClickListener(btnOnClickListener);
        btnUnbindService.setOnClickListener(btnOnClickListener);

        btnStartSecondActivity.setOnClickListener(btnOnClickListener);
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart...");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, "onRestart...");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume...");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause...");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop...");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy...");
        super.onDestroy();
    }

    private View.OnClickListener btnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_start_service:
                    Intent startIntent = new Intent(MainActivity.this, MyService.class);
                    startService(startIntent);
                    break;
                case R.id.btn_stop_service:
                    Intent stopIntent = new Intent(MainActivity.this, MyService.class);
                    stopService(stopIntent);
                    break;
                case R.id.btn_bind_service:
                    Intent bindIntent = new Intent(MainActivity.this, MyService.class);
                    bindService(bindIntent, connection, BIND_AUTO_CREATE);
                    break;
                case R.id.btn_unbind_service:
                    unbindService(connection);
                    break;
                case R.id.btn_start_second_activity:
                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    startActivity(intent);
                default:break;
            }
        }
    };
}
