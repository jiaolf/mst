package com.jiaolf.dream.设计模式.责任链模式;

/**
 * Create by jiaolf on 2023/4/18
 */
public abstract class AbstractRequest {
    private Object obj; // 处理对象

    public AbstractRequest(Object obj) {
        this.obj = obj;
    }

    // 获取请求的对象
    public Object getContent() {
        return obj;
    }

    // 获取请求的级别
    public abstract int getRequestLevel();
}
