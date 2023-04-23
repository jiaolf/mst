package com.jiaolf.dream;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.IntentService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.util.LruCache;
import android.widget.Toast;

import com.jiaolf.dream.mvp.MVPDemoActivity;
import com.jiaolf.dream.mvvm.MVVMDemoActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Handler handler;
        Looper looper;

        //使用bundle可以
        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);

        List<String> list = new ArrayList<>();
        //以后可以最小SDK设置为24(Android7.0),这样就可以使用java8的stream
        //list.stream().findFirst();

        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                int arg = msg.arg1;
                Toast.makeText(MainActivity.this, ">>"+arg, Toast.LENGTH_SHORT).show();
            }
        };

        // 子线程向主线程发消息
        new Thread(() -> {
            Message msg = Message.obtain();
            msg.arg1 = 3;
            handler.sendMessageDelayed(msg, 5000);
        }).start();


        // 主线程给子线程发消息
        class MyThread extends Thread{
            Looper mLooper;
            @Override
            public void run() {
                Looper.prepare(); // sThreadLocal.set(new Looper(quitAllowed));
                mLooper = Looper.myLooper(); //sThreadLocal.get();
                Looper.loop();
            }

        };
        MyThread thread = new MyThread();
        thread.setName("MyThread");
        thread.start();

        // 等待一会儿。。。thread.start();调用之后，run方法并不会立即就执行，所以稍等下
        while(thread.mLooper == null){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        // handle手柄、把手...      handler处理者
        Handler childHandler = new Handler(thread.mLooper){
            @Override
            public void handleMessage(@NonNull Message msg) {
                Log.i("jlf", "childHandler所在的线程是-->>"+Thread.currentThread().getName());
                Log.i("jlf", "childHandler --->> msg.what:" + msg.what);
            }
        };

        // 主线程给子线程发消息，比如IntentService，
        childHandler.sendEmptyMessage(10);
        childHandler.sendEmptyMessage(8);
        childHandler.sendEmptyMessage(6);

        // 一个消息，handler是怎么从子线程发送，又在主线程中接收的？
        // 1.这个Handler是在主线程中new的，Handler的Looper是MainLooper
        // 2.消息在子线程由Handler发送后，最终会进入主线程对应的MessageQueue中
        // 3.主线程的Looper.loop()方法会取出Message，并调用msg.target.dispatchMessage方法，
        // 而在dispatchMessage方法中，又调用了handleMessage方法，而我们在主线程中new的这个Handler
        // 又会重写HandleMessage方法，最终就拿到了message对象。


        // Looper.prepare方法会new一个Looper对象，并把这个Looper对象set到ThreadLocal中
        // 默认一个线程，
        // 内部封装了Looper对象
        HandlerThread handlerThread;

        IntentService intentService;

        //------------- bindService ----------------
        Intent serviceIntent = new Intent(this, MyService2.class);
        ServiceConnection serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                //IBinder
                // 客户端通过IBinder与绑定服务通信
                /*
                您可以将多个客户端同时连接到某项服务。但是，系统会缓存 IBinder 服务通信通道。换言之，
                只有在第一个客户端绑定服务时，系统才会调用服务的 onBind() 方法来生成 IBinder。
                 然后，系统会将该 IBinder 传递至绑定到同一服务的所有其他客户端，无需再次调用 onBind()。
                 */
                Log.i("jlf", "服务连接成功啦~~~");
                MyService2.MyBinder myBinder = (MyService2.MyBinder) service;
                MyService2 service2 = myBinder.getService();
                service2.sayHello("小李");
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        bindService(serviceIntent,serviceConnection, Context.BIND_AUTO_CREATE);
        //----------------------------------------------

        LruCache<String, File> lruCache = new LruCache<>(100);// 最多100个文件


        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 8;

        // 内存缓存。 内存的1/8， 单位KB。
        LruCache<String, Bitmap> memoryCache = new LruCache<String,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap bmp) {
                // getRowBytes
                // Return the number of bytes between rows in the bitmap's pixels.
                return bmp.getRowBytes() * bmp.getHeight() / 1024; //KB
            }
        };

        //-----------------------------------------------

        Messenger messenger;


        findViewById(R.id.btn1).setOnClickListener(v -> {
            startActivity(new Intent(this, MVPDemoActivity.class));
        });

        findViewById(R.id.btn2).setOnClickListener(v -> {
            startActivity(new Intent(this, MVVMDemoActivity.class));
        });
    }

    private void test_okhttp() {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();

        /**
         * 首先将 Request 封装为一个 RealCall, 这个RealCall实现了Call接口，Call接口定义的就是一次
         * 网络**请求**和其对应的**响应**的抽象
         */
        Request request = new Request.Builder()
                .url("http://xxx.com")
                .get()
                .build();

        // Call接口就是【一次网络请求和其响应的抽象】
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

}