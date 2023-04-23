package com.jiaolf.dream.mvp;

/**
 * Create by jiaolf on 2023/4/19
 *
 * Model层包含内容：
 *
 * ①实体类bean
 * ②接口，表示Model层所要执行的业务逻辑,如login, getXXXInfo等
 * ③接口实现类，具体实现业务逻辑，包含的一些主要方法。
 * 如数据获取，如从网络、缓存、数据库...
 */
public class GoodsModel {

    public void getGoodsInfo(DataCallback callback){
        // 比如这里通过Retrofit获取了网络数据
        callback.onSuccess("小米手机");
    }
}
