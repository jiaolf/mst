package com.jiaolf.dream.设计模式.责任链模式;

/**
 * Create by jiaolf on 2023/4/18
 */
public class Test {

    @org.junit.Test
    public void test1() {
//        Handler1 handler1 = new Handler1();
//        ConcreteHandler2 handler2 = new ConcreteHandler2();
//        ConcreteHandler3 handler3 = new ConcreteHandler3();
//
//        handler1.nextHandler = handler2;
//        handler2.nextHandler = handler3;
//        handler3.nextHandler = handler1;
//
//        handler1.handleRequest("ConcreteHandler3");

        AbstractHandler handler1 = new Handler1();
        AbstractHandler handler2 = new Handler2();
        AbstractHandler handler3 = new Handler3();

        handler1.nextHandler = handler2;
        handler2.nextHandler = handler3;

        AbstractRequest request1 = new Request1("Request1");
        AbstractRequest request2 = new Request2("Request2");
        AbstractRequest request3 = new Request3("Request3");

        // 处理3种不同的request
        handler1.handleRequest(request1);
//        handler1.handleRequest(request2);
//        handler1.handleRequest(request3);
    }
}
