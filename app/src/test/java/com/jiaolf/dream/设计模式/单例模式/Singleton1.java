package com.jiaolf.dream.设计模式.单例模式;

/**
 * Create by jiaolf on 2023/4/18
 * 饿汉模式
 * <p>
 * 优点：没有加锁，执行效率会提高。
 * 缺点：类加载时就初始化，浪费内存。
 * <p>
 * 它基于 classloader 机制避免了多线程的同步问题，不过，instance 在类装载时就实例化，
 * 虽然导致类装载的原因有很多种，在单例模式中大多数都是调用 getInstance 方法， 但是也
 * 不能确定有其他的方式（或者其他的静态方法）导致类装载，这时候初始化 instance 显然没
 * 有达到 lazy loading 的效果。
 */
public class Singleton1 {
    private static final Singleton1 instance = new Singleton1();

    private Singleton1() {
    }

    public static Singleton1 getInstance() {
        return instance;
    }
}
