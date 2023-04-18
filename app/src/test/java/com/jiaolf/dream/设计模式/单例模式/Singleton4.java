package com.jiaolf.dream.设计模式.单例模式;

/**
 * Create by jiaolf on 2023/4/18
 * 双重校验锁
 * 这种方式采用双锁机制，安全且在多线程情况下能保持高性能
 */
public class Singleton4 {
    // volatile: 1)线程之间的可见性 2)禁止指令重排序
    private volatile static Singleton4 instance;

    private Singleton4() {
    }

    public static Singleton4 getInstance() {
        if (instance == null) {
            synchronized (Singleton4.class) {
                if (instance == null) {
                    instance = new Singleton4();
                }
            }
        }
        return instance;
    }
}
