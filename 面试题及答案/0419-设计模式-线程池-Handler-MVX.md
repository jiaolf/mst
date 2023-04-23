1. #### 常见的设计模式有哪些？

   回答：单例、代理、建造者、责任链...  

   问：你说一下什么是代理模式？

   你说一下，什么是MVC，MVP，MVVM？各自的优缺点？

   回答：简单的说，MVC就是Controller层操作Model层的数据并返回给View层展示。在Android上，Controller层就相当于Activity和Fragment，View层就相当于xml布局和View类，Model层就是对数据的处理、存储，及网络请求等这些。但在Android上来说呢，Activity不光是充当了Controller的角色，有时候还会做一些View和model层做的事情，久而久之，就容易使Controller层变得太繁重和臃肿。而MVP，相比MVC来说，实现了Model层和View层的完全解耦，P层充当了Model和View的桥梁。在Android上，使用MVP的设计模式，可以让原来Activity或Fragment中的大部分的逻辑代码分离到Presenter中，而Activity或Fragment作为了View的角色。MVP虽然很好做到了解耦，但是随着业务的复杂度越来越高，View和Model之间会有大量的交互操作，各种接口和类也会大量增加，

   问：说一下MVP和MVVM的区别是什么？

   > 既要对某个知识点理解，又要能清晰流畅的表达出来。

---

MVC的特点：**Model层和View层存在耦合**

MVP的特点：相比MVC，MVP中的View和Model是完全解耦的，P充当了View和Model的中间人角色，但是当View和Model之间存在大量数据交互逻辑的时候，视图和Presenter的交互会过于频繁，使得他们的联系过于紧密。

MVVM的特点：可以作为是MVP的改进版，但在MVVM中，原本有P负责的view和model直接的数据交互操作交给了Binder或者叫DataBinding的东西，而这块通常都会有对应的框架层面的实现，对于开发者而言，实现ViewModel的部分就相对变少了。

---

## 线程池

线程池的两种创建方式：

1. 使用jdk中juc包（java.util.concurrent）中的Executors工具类，来创建4中不同类型的线程池。
2. 使用ThreadPoolExecutor自己去new一个线程池。

线程池构造方法中的7个参数：

```java
    public ThreadPoolExecutor(int corePoolSize, // 核心线程数大小
                              int maximumPoolSize, // 最大线程数大小
                              long keepAliveTime, // 闲置线程的超时时间
                              TimeUnit unit, // 时间单位
                              BlockingQueue<Runnable> workQueue, // 阻塞队列
                              ThreadFactory threadFactory, // 线程工厂
                              RejectedExecutionHandler handler) // 拒绝策略
```

前4个参数都比较简单，也容易记，这里重点说一下后三个参数。

- BlockingQueue<Runnable> 在jdk提供的4种线程池中，除了CachedThreadPool用的是SynchronousQueue（同步阻塞队列）之外，其他用的都是LinkedBlockingQueue

- ThreadFactory ， 我们看一下OKhttp中Dispatcher分发器里面的线程池中的线程工厂：

  ```java
  public static ThreadFactory threadFactory(final String name, final boolean daemon) {
      return new ThreadFactory() {
        @Override public Thread newThread(Runnable runnable) {
          Thread result = new Thread(runnable, name);
          result.setDaemon(daemon);
          return result;
        }
      };
    }
  ```

  其实可以看出来，在创建线程的时候，给线程起了一个名字，同时设置了是否为**守护线程**的标志。

- 拒绝策略。

  ```
  AbortPolicy - [丢弃任务，并抛出拒绝执行] RejectedExecutionException 异常信息。
  线程池默认的拒绝策略。 必须处理好抛出的异常，否则会打断当前的执行流程，影响后续的任务执行。
  
  DiscardPolicy - 直接丢弃，其他啥都没有
  
  CallerRunsPolicy - 当触发拒绝策略，只要线程池没有关闭的话，则使用调用线程直接运行任务。
  一般并发比较小，性能要求不高，不允许失败。 但是，由于调用者自己运行任务，如果任务提交速度过快，可能导致程序阻塞，性能效率上必然的损失较大
  
  DiscardOldestPolicy - 当触发拒绝策略，只要线程池没有关闭的话，丢弃阻塞队列 workQueue
  中最老的一个任务，并将新任务加入
  ```

