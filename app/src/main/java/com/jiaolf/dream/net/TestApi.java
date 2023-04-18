package com.jiaolf.dream.net;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Create by jiaolf on 2023/4/18
 */
public interface TestApi {

    @GET("userInfo")
    Call<BaseVO> getUserInfo();

}
