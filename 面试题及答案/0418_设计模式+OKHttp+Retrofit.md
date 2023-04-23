## 设计模式

1. 常用的设计模式有哪些？

   单例模式、建造者模式、观察者模式、责任链模式、工厂模式、适配器模式、策略模式、代理模式、模板方法模式。

   其他的还有：桥接模式、原型模式、中介者模式、组合模式、享元模式。

   > 关于**生产者/消费者模式**。虽然不属于常见的设计模式之一，但也是编程中一种常用的高效的编程模式。**生产者**只需负责生产产品，然后将产品放入到缓冲队列`Queue`中，**消费者**只需负责从缓存队列`Queue`中取出产品使用即可。如：Android中的Handler机制就是典型的生产者消费者模式。

   - 单例模式——**意图：**保证一个类仅有一个实例，并提供一个访问它的全局访问点。**主要解决：**避免一个全局使用的类频繁地创建与销毁。

   - 建造者模式——是一步一步的构建一个复杂对象的创建型模式，它允许用户在不知道内部构建细节的情况下，可以更精细的控制对象的构造流程。该模式是为了将构建复杂对象的过程和它的部件解耦，使得构建过程和部件的表示隔离开来。

   - 责任链模式——**意图：**避免请求发送者与接收者耦合在一起，让多个对象都有可能接收请求，将这些对象连接成一条链，并且沿着这条链传递请求，直到有对象处理它为止。**主要解决：**职责链上的处理者负责处理请求，客户只需要将请求发送到职责链上即可，无须关心请求的处理细节和请求的传递，所以职责链将请求的发送者和请求的处理者解耦了。

   - 工厂模式——**意图：**定义一个创建对象的接口，让其子类决定实例化哪个类。**优点：** 1、一个调用者想创建一个对象，只要知道其名称就可以了。 2、扩展性高，如果想增加一个产品，只要扩展一个工厂类就可以。 3、屏蔽产品的具体实现，调用者只关心产品的接口。**缺点：**每次增加一个产品时，都需要增加一个具体类和对象实现工厂，使得系统中类的个数成倍增加，在一定程度上增加了系统的复杂度，同时也增加了系统具体类的依赖。这并不是什么好事。**使用场景**：在任何需要生成复杂对象的地方，都可以使用工厂方法模式。复杂对象适合使用工厂模式，用new就可以完成创建的对象无需使用工厂模式。

   - 适配器模式—— 适配器模式把一个类的接口变换成客户端所期待的另一种接口，从而使原本因接口不匹配而无法在一起工作的两个类能够在一起工作。

   - 观察者模式——**意图：**定义对象间的一种一对多的依赖关系，当一个对象的状态发生改变时，所有依赖于它的对象都得到通知并被自动更新。**主要解决：**一个对象状态改变给其他对象通知的问题，而且要考虑到易用和低耦合，保证高度的协作。

     ```
     发布订阅者模式和观察者模式的区别？
     答案：他们都属于观察者模式，只不过有不同的实现方法。发布订阅相比于观察者多了一个调度中心，发布者通过调度中心向订阅者发布消息。观察者模式中目标和观察者相互依赖，观察者订阅目标主题，当目标发生变化后，会通知对应观察者。
     ```

     | 观察者模式                                                   |                        发布/订阅模式                         |
     | ------------------------------------------------------------ | :----------------------------------------------------------: |
     | ![img](https://images2015.cnblogs.com/blog/555379/201603/555379-20160313183429007-1351424959.png) | ![img](https://images2015.cnblogs.com/blog/555379/201603/555379-20160313183439366-1623019133.png) |

     - 模板方法模式——定义一个操作中的算法的框架，而将一些步骤延迟到子类中，使得子类可以不改变一个算法的结构即可重新定义改算法的某些特定步骤。

   

2. 你在项目中用过哪些设计模式？

   - 单例模式：如网络请求OKHttp；图片加载框架；如之前项目中有包含社交模块，其中对好友的统一管理使用的单例模式，在整个应用的不同地方，都使用一个实例来对好友的信息读取、好友关系判断、添加或删除等等操作。

   - 观察者模式：如以前做过应用商店，里面有apk的文件下载，而下载任务和在UI上的显示可能是一对多的，比如下载详细页的按钮会变成进度条的样式显示进度，应用列表上会显示下载的百分比进度，通知栏或者下载管理页面中也有显示，这样就有一个DownloadListener接口，让观察者，也就是需要显示下载进度的类去implement这个接口。然后还有一个DownloadTask的类，所有的观察者也都会随着自己的生命周期而在这里注册和解除注册，当下载任务有相应的动作变化时，如开始、暂停、进度变化、下载完成、下载出错等情况发生时，就会通知到所有的观察者。

   - 模板方法模式：如在BaseActivity中的onCreate方法中实现一些方法来完成每个Activity都要执行的相似操作，其中有些方法是在子类中完成的。

     ```java
     public abstract class BaseActivity extends AppCompatActivity {
     
         @Override
         protected void onCreate(Bundle savedInstanceState) {
             super.onCreate(savedInstanceState);
             setContentView(initLayout()); //重点是这句
             initView();
             initData();
         }
     
         protected abstract int initLayout(); //加载xml布局
         protected abstract void initData(); //加载数据
         protected abstract void initView(); //加载界面
     }
     ```

     - 动态代理的使用场景：
     - 构造者模式的使用场景：
     - 外观模式：

     

3. 设计模式的六大原则？

   - 单一职责原则（就一个类而言，应该仅有一个引起它变化的原因）
   - 开闭原则（软件实体（类、模块、函数等）应该是可以扩展，但不可修改）
   - 依赖倒置原则（A. 高层模块不应该依赖底层模块，两者都应该依赖抽象。B. 抽象不应该依赖细节；细节应该依赖抽象）
   - 接口隔离原则（客户端不应该依赖它不需要的接口。或：类间的依赖关系应该建立在最小的接口上）
   - 里氏替换原则（子类型必须能够替换掉它们的父类型）
   - 迪米特原则（也称为最少知识原则。一个对象应该对其他对象有最少的了解）

### 设计模式的类型

| 序号 | 模式 & 描述                                                  | 包括                                                         |
| :--- | :----------------------------------------------------------- | :----------------------------------------------------------- |
| 1    | **创建型模式** 这些设计模式提供了一种在创建对象的同时隐藏创建逻辑的方式，而不是使用 new 运算符直接实例化对象。这使得程序在判断针对某个给定实例需要创建哪些对象时更加灵活。 | 工厂模式（Factory Pattern）抽象工厂模式（Abstract Factory Pattern）单例模式（Singleton Pattern）建造者模式（Builder Pattern）原型模式（Prototype Pattern） |
| 2    | **结构型模式** 这些设计模式关注类和对象的组合。继承的概念被用来组合接口和定义组合对象获得新功能的方式。 | 适配器模式（Adapter Pattern）桥接模式（Bridge Pattern）过滤器模式（Filter、Criteria Pattern）组合模式（Composite Pattern）装饰器模式（Decorator Pattern）外观模式（Facade Pattern）享元模式（Flyweight Pattern）代理模式（Proxy Pattern） |
| 3    | **行为型模式** 这些设计模式特别关注对象之间的通信。          | 责任链模式（Chain of Responsibility Pattern）命令模式（Command Pattern）解释器模式（Interpreter Pattern）迭代器模式（Iterator Pattern）中介者模式（Mediator Pattern）备忘录模式（Memento Pattern）观察者模式（Observer Pattern）状态模式（State Pattern）空对象模式（Null Object Pattern）策略模式（Strategy Pattern）模板模式（Template Pattern）访问者模式（Visitor Pattern） |



## OKHttp

参考：[OKhttp原理8连问](https://mp.weixin.qq.com/s?__biz=MzA5MzI3NjE2MA==&mid=2650267613&idx=1&sn=a4f1ae77700723e548cf9209bbab61b5&chksm=88632eb2bf14a7a44d752ef76ebdb55297c9478ca5bd485d13167f0ee985c5e7b172fd984996&scene=27)

1. **Okhttp中用到的设计模式？**

   1. 构建者模式：OkHttpClient与Request的构建都用到了构建者模式。
   2. 外观模式：OkHttp使用了外观模式,将整个系统的复杂性给隐藏起来，将子系统接口通过一个客户端OkHttpClient统一暴露出来。
   3. 责任链模式：OKHttp的核心就是责任链模式，通过5个默认拦截器构成的责任链完成请求的配置。
   4. 享元模式：享元模式的核心即池中复用，OKHttp复用TCP连接时用到了连接池，同时在异步请求中也用到了线程池。

   

2. **OKHttp的大体请求流程**

   1. 通过建造者模式构建OKHttpClient与 Request
   2. OKHttpClient通过newCall发起一个新的请求
   3. 通过分发器维护请求队列与线程池，完成请求调配
   4. 通过五大默认拦截器完成请求重试，缓存处理，建立连接等一系列操作
   5. 得到网络请求结果

3. **OKHttp分发器是怎样工作的？**

   分发器的主要作用是维护请求队列与线程池,比如我们有100个异步请求，肯定不能把它们同时请求，而是应该把它们排队分个类，分为正在请求中的列表和正在等待的列表，等请求完成后，即可从等待中的列表中取出等待的请求，从而完成所有的请求。

```java
private int maxRequests = 64; // 最大允许的并行请求数量
private int maxRequestsPerHost = 5; // 单个主机可以并行请求的最大数量

三个Deque队列：	
​			readyAsyncCalls   准备运行的异步队列
​			runningAsyncCalls 正在运行的异步队列
​			runningSyncCalls  正在运行的同步队列
    
   public synchronized ExecutorService executorService() {
    if (executorService == null) {
      executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
          new SynchronousQueue<Runnable>(), Util.threadFactory("OkHttp Dispatcher", false));
    }
    return executorService;
  }
```

4. OKHttp的拦截器是如何工作的？

   添加拦截器的代码在源码 RealCall.java文件中：

   ```java
   Response getResponseWithInterceptorChain() throws IOException {
       // Build a full stack of interceptors.
       List<Interceptor> interceptors = new ArrayList<>();
       interceptors.addAll(client.interceptors());
       interceptors.add(retryAndFollowUpInterceptor);	//重试和重定向拦截器
       interceptors.add(new BridgeInterceptor(client.cookieJar())); // 桥接拦截器
       interceptors.add(new CacheInterceptor(client.internalCache())); // 缓存拦截器
       interceptors.add(new ConnectInterceptor(client)); // 连接拦截器
       if (!forWebSocket) {
         interceptors.addAll(client.networkInterceptors()); 
       }
       interceptors.add(new CallServerInterceptor(forWebSocket)); // 请求拦截器
   
       Interceptor.Chain chain = new RealInterceptorChain(interceptors, null, null, null, 0,
           originalRequest, this, eventListener, client.connectTimeoutMillis(),
           client.readTimeoutMillis(), client.writeTimeoutMillis());
   
       return chain.proceed(originalRequest);
     }
   ```

   我们的网络请求就是这样经过责任链一级一级的递推下去，最终会执行到CallServerInterceptor的intercept方法，此方法会将网络响应的结果封装成一个Response对象并return。之后沿着责任链一级一级的回溯，最终就回到getResponseWithInterceptorChain方法的返回。

5. OKhttp空闲连接如何清除？（相关类：ConnectionPool.java)

   1. 在将连接加入连接池时就会启动定时任务。
   2. 有空闲连接的话，如果最长的空闲时间大于5分钟或空闲数大于5，就移除关闭这个最长空闲连接；如果空闲数不大于5且最长的空闲时间不大于5分钟，就返回到5分钟的剩余时间，然后等待这个时间再来清理。
   3. 没有空闲连接就等5分钟后再尝试清理。
   4. 没有连接不清理。

6.  简单说一下OKhttp

   1、Http2支持对于同一个主机的多个请求共享一个socket

   2、连接池可以减少请求延迟

   3、支持gzip压缩响应体

   4、通过缓存避免重复的请求

   

## Retrofit

Retrofit是一个基于AOP思想,对RestfulApi注解进行动态代理的网络框架

核心的三个类

- **[Retrofit]** 通过动态代理创建api接口的实例。在InvocationHandler的

invoke(Object proxy, Method method, @Nullable Object[] args)中，
参数中的method就是代理在接口上调用的方法。然后通过此method,构建出ServiceMethod

- **[ServiceMethod]** 通过反射获取到我们在方法上注解的网络请求的信息，创建okhttp3.Call

- **[OkHttpCall]** 调用OKhttp请求，对结果进行类型转换