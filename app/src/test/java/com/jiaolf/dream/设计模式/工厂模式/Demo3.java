package com.jiaolf.dream.设计模式.工厂模式;

import org.junit.Test;

/**
 * Create by jiaolf on 2023/4/18
 */
public class Demo3 {
    public abstract class AudiFactory {
        /**
         * 某车型的工厂方法
         *
         * @param clazz 具体型号的SUV型号类型
         * @param <T>
         * @return
         */
        public abstract <T extends AudiCar> T createAudiCar(Class<T> clazz);
    }

    public class AudiCarFactory extends AudiFactory {
        @Override
        public <T extends AudiCar> T createAudiCar(Class<T> clazz) {
            AudiCar car = null;
            try {
                car = (AudiCar) Class.forName(clazz.getName()).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return (T) car;
        }
    }

    public abstract class AudiCar {
        public abstract void drive();

        public abstract void selfNavigation();
    }

    public class AudiQ3 extends AudiCar{
        @Override
        public void drive() {
            System.out.println("奥迪Q3启动了~");
        }

        @Override
        public void selfNavigation() {
            System.out.println("奥迪Q3自动巡航了~");
        }
    }

    public class AudiQ5 extends AudiCar{
        @Override
        public void drive() {
            System.out.println("奥迪Q5启动了~");
        }

        @Override
        public void selfNavigation() {
            System.out.println("奥迪Q5自动巡航了~");
        }
    }

    public class AudiQ7 extends AudiCar{
        @Override
        public void drive() {
            System.out.println("奥迪Q7启动了~");
        }

        @Override
        public void selfNavigation() {
            System.out.println("奥迪Q7自动巡航了~");
        }
    }

    @Test
    public void test(){
        AudiFactory factory = new AudiCarFactory();

        // 奥迪Q3生产并启动
        AudiQ3 q3 = factory.createAudiCar(AudiQ3.class);
        q3.drive();
        q3.selfNavigation();

        //奥迪Q5生产并启动
        AudiQ5 q5 = factory.createAudiCar(AudiQ5.class);
        q5.drive();
        q5.selfNavigation();

        // 奥迪Q7生产并启动
        AudiQ7 q7 = factory.createAudiCar(AudiQ7.class);
        q7.drive();
        q7.selfNavigation();

    }
}
