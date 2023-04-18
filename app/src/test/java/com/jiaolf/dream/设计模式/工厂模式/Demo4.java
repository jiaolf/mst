package com.jiaolf.dream.设计模式.工厂模式;

import org.junit.Test;

/**
 * Create by jiaolf on 2023/4/18
 * 抽象工厂模式
 * 为创建一组相关或者相互依赖的对象提供一个接口，而不需要指定它们的具体类
 */
public class Demo4 { // 如Demo3，虽然Q3和Q7

    /**
     * 抽象车工厂
     */
    public abstract class CarFactory {
        // 生产轮胎
        public abstract ITire createTire();

        // 生产发动机
        public abstract IEngine createEngine();

        // 生产制动系统
        public abstract IBrake createBrake();
    }

    /**
     * 轮胎相关类
     */
    public interface ITire {
        void tire();
    }

    public class NormalTire implements ITire {
        @Override
        public void tire() {
            System.out.println("普通轮胎");
        }
    }

    public class SUVTire implements ITire {
        @Override
        public void tire() {
            System.out.println("越野轮胎");
        }
    }

    /**
     * 发动机相关类
     */
    public interface IEngine {
        void engine();
    }

    public class DomesticEngine implements IEngine {
        @Override
        public void engine() {
            System.out.println("国产发动机");
        }
    }

    public class ImportEngine implements IEngine {
        @Override
        public void engine() {
            System.out.println("进口发动机");
        }
    }

    /**
     * 制动系统相关
     */
    public interface IBrake {
        void brake();
    }

    public class NormalBrake implements IBrake {
        @Override
        public void brake() {
            System.out.println("普通制动");
        }
    }

    public class SeniorBrake implements IBrake {
        @Override
        public void brake() {
            System.out.println("高级制动");
        }
    }

    /**
     * Q3工厂类
     */
    public class Q3Factory extends CarFactory {
        @Override
        public ITire createTire() {
            return new NormalTire();
        }

        @Override
        public IEngine createEngine() {
            return new DomesticEngine();
        }

        @Override
        public IBrake createBrake() {
            return new NormalBrake();
        }
    }

    /**
     * Q7工厂类
     */
    public class Q7Factory extends CarFactory {
        @Override
        public ITire createTire() {
            return new SUVTire();
        }

        @Override
        public IEngine createEngine() {
            return new ImportEngine();
        }

        @Override
        public IBrake createBrake() {
            return new SeniorBrake();
        }
    }


    @Test
    public void test() {
        // Q3工厂
        CarFactory factoryQ3 = new Q3Factory();
        factoryQ3.createTire().tire();
        factoryQ3.createEngine().engine();
        factoryQ3.createBrake().brake();

        System.out.println("----------------");

        // Q7工厂
        CarFactory factoryQ7 = new Q7Factory();
        factoryQ7.createTire().tire();
        factoryQ7.createEngine().engine();
        factoryQ7.createBrake().brake();


    }


}
