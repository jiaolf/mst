package com.jiaolf.dream.设计模式.单例模式;

/**
 * Create by jiaolf on 2023/4/18
 * 静态内部类的方式
 *
 * 第一次加载此类时不会初始化，只有在第一次调用getInstance方法时才会导致
 * 实例对象的初始化。因此，第一次调用getInstance方法会导致虚拟机加载SingletonHolder
 * 类，这种方式不仅能够保证线程安全，也能够保证实例对象的唯一性，同时也延迟了单例的实例化，
 * 所以这是推荐使用的单例实现方式。
 *
 * 这种方式是 Singleton 类被装载了，instance 不一定被初始化。因为 SingletonHolder
 * 类没有被主动使用，只有通过显式调用 getInstance 方法时，才会显式装载 SingletonHolder
 * 类，从而实例化 instance。
 * 想象一下，如果实例化 instance 很消耗资源，所以想让它延迟加载，另外一方面，又不希望在
 * Singleton 类加载时就实例化，因为不能确保 Singleton 类还可能在其他的地方被主动使用
 * 从而被加载，那么这个时候实例化 instance 显然是不合适的。
 */
public class Singleton5 {
    private static class SingletonHolder{
        private static final Singleton5 INSTANCE = new Singleton5();
    }

    private Singleton5(){}

    public static Singleton5 getInstance(){
        return SingletonHolder.INSTANCE;
    }
}
