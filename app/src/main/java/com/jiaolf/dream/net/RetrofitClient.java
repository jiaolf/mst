package com.jiaolf.dream.net;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClient {
    private Retrofit mRetrofit;
    private OkHttpClient mOkHttpClient;

    private static RetrofitClient retrofitClient;
    public static final String BASE_URL = "http://xxx.com";

    private RetrofitClient() {
        // token失效的拦截器
        Interceptor tokenInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);

                // 通过相应状态码来判断是否为token失效
                if(response.code() == 401){
                    // 如果失效了，则跳转到登录页面...
                }

                return response;
            }
        };


        // 日志拦截器
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(message -> {
//            StringBuilder mMessage = new StringBuilder();
//            if ((message.startsWith("{") && message.endsWith("}"))
//                    || (message.startsWith("[") && message.endsWith("]"))) {
//                message = JsonUtil.formatJson(JsonUtil.decodeUnicode(message));
//            }
//            mMessage.append(message.concat("\n"));
//
//            Log.i("RetrofitClient", mMessage.toString());
            Log.i("RetrofitClient", message);
        });

        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // 缓存拦截器
        /*Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                //有网的时候,读接口上的@Headers里的注解配置
                String cacheControl = request.cacheControl().toString();
                //没有网络并且添加了注解,才使用缓存.
                if (!AtApplication.getInstance().isNetWorkConnected()
                        && !TextUtils.isEmpty(cacheControl)) {
                    //重置请求体;
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }

                //如果没有添加注解,则不缓存
                if (TextUtils.isEmpty(cacheControl) || "no-store".contains(cacheControl)) {
                    //响应头设置成无缓存
                    cacheControl = "no-store";
                } else if (AtApplication.getInstance().isNetWorkConnected()) {
                    //如果有网络,则将缓存的过期事件,设置为0,获取最新数据
                    cacheControl = "public, max-age=" + 0;
                } else {
                    //...如果无网络,则根据@headers注解的设置进行缓存.
                }
                Response response = chain.proceed(request);
                return response.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            }
        };*/

        // cache
//        File cachePath = AtApplication.getInstance().getExternalCacheDir();
//        if (cachePath == null) cachePath = AtApplication.getInstance().getCacheDir();
//        long cacheSize = 10 * 1024 * 1024; // 10M
//        Cache cache = new Cache(cachePath, cacheSize);

        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)//写操作 超时时间
                .readTimeout(30, TimeUnit.SECONDS)//读操作 超时时间
                .retryOnConnectionFailure(true)//错误重连
                .addInterceptor(httpLoggingInterceptor)
                //.addInterceptor(cacheInterceptor) // 只能缓存Get请求，但app中基本上是post请求，所以这个缓存对本应用意义不大
                //.cache(cache)
                .build();


        Gson gson = new GsonBuilder().setLenient().create();
        mRetrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                //.addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 是否支持RxJava
                .baseUrl(BASE_URL)
                .build();

    }

    public static RetrofitClient getInstance() {
        if (retrofitClient == null) {
            synchronized (RetrofitClient.class) {
                retrofitClient = new RetrofitClient();
            }
        }
        return retrofitClient;
    }

    Retrofit getRetrofit() {
        return mRetrofit;
    }

}
