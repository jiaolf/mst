package com.jiaolf.dream.设计模式.责任链模式;

import android.util.Log;

/**
 * Create by jiaolf on 2023/4/18
 * 抽象处理者
 */
public abstract class AbstractHandler {

    protected AbstractHandler nextHandler; // 下一个节点的处理者

    /**
     * 处理请求
     */
    public void handleRequest(AbstractRequest request){
        //判断当前处理者的等级是否与对象的处理等级一致
        if (getHandleLevel() == request.getRequestLevel()) {
            //一致则处理对象
            handle(request);
        }else{
            //否则将请求对像转发给一下个节点的请求对象
            if (nextHandler != null) {
                nextHandler.handleRequest(request);
            }else{
                //当所以对象都不能处理时...
                System.out.println("所有处理者都无法处理了...");
            }
        }
    }

    protected abstract int getHandleLevel();

    protected abstract void handle(AbstractRequest request);
}
