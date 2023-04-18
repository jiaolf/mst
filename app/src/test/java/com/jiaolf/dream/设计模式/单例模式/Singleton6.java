package com.jiaolf.dream.设计模式.单例模式;

/**
 * Create by jiaolf on 2023/4/18
 *
 * 这是实现单例模式的最佳方法。它更简洁，自动支持序列化机制，绝对防止多次实例化。
 */
public enum Singleton6 {
    INSTANCE;
    public void doSomething(){
        //...
    }
}
