package com.jiaolf.dream.设计模式.单例模式;

/**
 * Create by jiaolf on 2023/4/18
 * 懒汉式，线程不安全
 * <p>
 * 实现了延迟初始化。这种方式是最基本的实现方式，这种实现最大的问题就是不支持多线程
 */
public class Singleton2 {

    private static Singleton2 instance;

    private Singleton2() {
    }

    public static Singleton2 getInstance() {
        if (instance == null) {
            instance = new Singleton2();
        }
        return instance;
    }
}
