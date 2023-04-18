package com.jiaolf.dream.net;

import retrofit2.Retrofit;

public class HttpUtil {
    private static HttpUtil httpUtil;
    private Retrofit retrofit;

    private TestApi api;

    private HttpUtil() {
        retrofit = RetrofitClient.getInstance().getRetrofit();

        api = retrofit.create(TestApi.class);
    }

    public static HttpUtil getInstance() {
        if (httpUtil == null) {
            synchronized (HttpUtil.class) {
                httpUtil = new HttpUtil();
            }
        }
        return httpUtil;
    }

    public TestApi getApi() {
        return api;
    }
}
