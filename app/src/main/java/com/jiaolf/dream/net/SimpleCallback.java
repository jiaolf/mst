package com.jiaolf.dream.net;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Create by jiaolf on 2020/02/21 下午 10:28
 */
public abstract class SimpleCallback<T> implements Callback<T> {
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.code() == 200) {
            onSuccess(response.body());
        } else {
            onFail(response.message());
        }
        //UI.removeWaitBox();
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        //UI.showToast("Network Error");
        //UI.removeWaitBox();
    }

    public abstract void onSuccess(T t);

    public abstract void onFail(String msg);

}
