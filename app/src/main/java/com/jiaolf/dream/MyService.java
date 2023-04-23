package com.jiaolf.dream;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyService extends Service {
    public MyService() {
    }

    private void test() {
        stopSelf(); //立即停止服务
        stopSelf();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int res = START_STICKY; // 返回start_sticky
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}