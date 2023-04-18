package com.jiaolf.dream.设计模式.工厂模式;

import org.junit.Test;

/**
 * Create by jiaolf on 2023/4/18
 */
public class Demo1 {
    // 抽象产品类
    public abstract class Product{
        public abstract void method();
    }

    // 具体产品类A
    public class ConcreteProductA extends Product{
        @Override
        public void method() {
            System.out.println("我是产品A");
        }
    }

    // 具体产品类B
    public class ConcreteProductB extends Product{
        @Override
        public void method() {
            System.out.println("我是产品B");
        }
    }

    // 抽象工厂类
    public abstract class Factory{
        public abstract Product createProduct();
    }

    // 具体工厂类
    public class ConcreteFactory extends Factory{

        @Override
        public Product createProduct() {
            // 想生产A就返回A，想生产B就返回B
//            return new ConcreteProductA();
            return new ConcreteProductB();
        }
    }

    @Test
    public void test1(){
        Factory factory = new ConcreteFactory();
        Product product = factory.createProduct();
        product.method();
    }



}
