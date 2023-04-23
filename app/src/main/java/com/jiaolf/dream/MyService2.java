package com.jiaolf.dream;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService2 extends Service {
    public MyService2() {
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {

        MyBinder myBinder = new MyBinder();

        return myBinder;
    }

    public void sayHello(String name) {
        Log.i("jlf", "MyBinder--->>hello..." + name);
    }

    public class MyBinder extends Binder {
        MyService2 getService() {
            // 让客户端可以调用Service的public方法
            return MyService2.this;
        }
    }
}