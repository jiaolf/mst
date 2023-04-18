package com.jiaolf.dream.设计模式.责任链模式;

/**
 * Create by jiaolf on 2023/4/18
 * 具体处理者1
 */
public class Handler1 extends AbstractHandler {
    @Override
    protected int getHandleLevel() {
        return 1;
    }

    @Override
    protected void handle(AbstractRequest request) {
        System.out.println("第一个处理者收到了:" + request + " 进行了相应的处理任务");
    }
}
