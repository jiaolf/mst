package com.jiaolf.dream.设计模式.责任链模式;

/**
 * Create by jiaolf on 2023/4/18
 */
public class Request3 extends AbstractRequest {
    public Request3(Object obj) {
        super(obj);
    }

    @Override
    public int getRequestLevel() {
        return 3;
    }
}
