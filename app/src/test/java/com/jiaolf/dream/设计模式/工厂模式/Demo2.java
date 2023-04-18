package com.jiaolf.dream.设计模式.工厂模式;

import org.junit.Test;

/**
 * Create by jiaolf on 2023/4/18
 */
public class Demo2 {
    // 抽象产品类
    public abstract class Product {
        public abstract void method();
    }

    // 具体产品类A
    public class ConcreteProductA extends Product {
        @Override
        public void method() {
            System.out.println("我是产品A");
        }
    }

    // 具体产品类B
    public class ConcreteProductB extends Product {
        @Override
        public void method() {
            System.out.println("我是产品B");
        }
    }

    // 抽象工厂类
    public abstract class Factory {
        public abstract <T extends Product> T createProduct(Class<T> clazz);
    }

    // 具体工厂类
    public class ConcreteFactory extends Factory {

        @Override
        public <T extends Product> T createProduct(Class<T> clz) {
            Product product = null;
            try {
                product = (Product) Class.forName(clz.getName()).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return (T) product;
        }
    }

    @Test
    public void test1() {
        Factory factory = new ConcreteFactory();

        Product p1 = factory.createProduct(ConcreteProductA.class);
        p1.method();

        Product p2 = factory.createProduct(ConcreteProductB.class);
        p2.method();

    }


}
