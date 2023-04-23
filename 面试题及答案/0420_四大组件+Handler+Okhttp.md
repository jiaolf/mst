1. Activity，Service，广播，ContentProvider

   问题：什么是Activity？ Activity的生命周期？Activity的启动模式？onSaveInstanceState的调用时机是什么？

   - Activity 提供窗口供应用在其中绘制界面。此窗口通常会填满屏幕，但也可能比屏幕小，并浮动在其他窗口上面。通常，一个 Activity 实现应用中的一个屏幕。

   - 对于生命周期中的onPause方法：当 Activity 失去焦点并进入“已暂停”状态时，系统就会调用 `onPause()`从技术上来说，这意味着您的 Activity 仍然部分可见。**您不应使用 `onPause()` 来保存应用或用户数据**，您应组合使用 [`ViewModel`](https://developer.android.google.cn/reference/androidx/lifecycle/ViewModel?hl=zh-cn)、[`onSaveInstanceState()`](https://developer.android.google.cn/reference/android/app/Activity?hl=zh-cn#onSaveInstanceState(android.os.Bundle)) 和/或本地存储来保留用户的瞬时界面状态。

   - 四种启动模式。android12以后又多了一种启动模式singleInstancePerTask

   - **taskAffinity** 亲和性相同的 Activity 组件 , 放在同一个任务栈中 ;

     应用的亲和性属性默认就是包名 , 如果不设置 , 默认是在同一个任务栈中的 ;

   - onSaveInstanceState的调用时机：按下home键，按下电源键，横竖屏切换时，启动其他Activity时

   问题：什么是Service？服务有哪几种？什么是IntentService？StartService和BindService的区别？

   - 服务是一种可在后台执行长时间运行操作而不提供界面的应用组件。比如可以在一个服务去执行下载任务、或者播放音乐、执行文件 I/O等。

   - 服务有三种：

     1. **前台服务**。前台服务执行一些用户能注意到的操作，前台服务必须显示[通知](https://developer.android.google.cn/guide/topics/ui/notifiers/notifications?hl=zh-cn)，比如音乐播放器。
     2. **后台服务**。后台服务执行用户不会直接注意到的操作。
     3. **绑定服务**。当应用组件通过调用 `bindService()` 绑定到服务时，服务即处于*绑定*状态。绑定服务会提供客户端-服务器接口，以便组件与服务进行交互、发送请求、接收结果，甚至是利用进程间通信 (IPC) 跨进程执行这些操作。仅当与另一个应用组件绑定时，绑定服务才会运行。多个组件可同时绑定到该服务，但全部取消绑定后，该服务即会被销毁。

   - startService和bindStrvice的区别：

     ```
     startService：onCreate->onStart->runing->onDestroy
     bindService: onCreate->onBind->onUnbind->onDestroy。
     
     启动服务
     该服务在其他组件调用 startService() 时创建，然后无限期运行，且必须通过调用 stopSelf() 来自行停止运行。此外，其他组件也可通过调用 stopService() 来停止此服务。服务停止后，系统会将其销毁。
     
     绑定服务
     该服务在其他组件（客户端）调用 bindService() 时创建。然后，客户端通过 IBinder 接口与服务进行通信。客户端可通过调用 unbindService() 关闭连接。多个客户端可以绑定到相同服务，而且当所有绑定全部取消后，系统即会销毁该服务。（服务不必自行停止运行。）
     
     绑定服务需要一个ServiceConnection的实现，当连接成功后，客户端通过IBinder与绑定服务通信。
     
     IBinder的3中实现方式：
     1. 扩展Binder类（在与客户端相同的进程中运行，也就是说不需要跨进程时）
     2. 使用Messenger （Messenger 会在单个线程中创建包含所有客户端请求的队列，以便服务一次接收一个请求）
     3. 使用AIDL（如果您想让服务同时处理多个请求，可以直接使用 AIDL）
     ```

     ​	

     - IntentService：

       *IntentService是Service的子类，内部封装了Looper、ServiceHandler、和HandlerThread*，IntentService可以更方便的让我们在Service中执行一些耗时操作，当任务执行完毕后会自动停止服务 `stopSelf()`。

       **HandlerThread**,就是在启动这个线程的时候，会创建一个消息的循环。也就是会执行Looper.prepare和Looper.loop方法。new一个Handler的时候，构造方法需要传一个Looper，所以要先运行HandlerThread，然后再通过HandlerThread.getLooper()来获取Looper对象。这样我们就可以用这个Handler发送和处理消息了。

       **service和Activity一样，都是运行在主线程中**。所以，在Service的onStartCommand方法中同样不能去执行耗时操作，IntentService就是通过主线程给子线程发送消息的方式，把任务安排到子线程中去执行。

     

   问题：什么是广播？广播有哪些类型？静态注册和动态注册的区别？

   - 广播可以让应用与应用、应用与系统之间相互收发消息，这与[发布-订阅](https://en.wikipedia.org/wiki/Publish–subscribe_pattern)设计模式相似。

   - 广播有3种不同的发送方式：

     1.  `sendOrderedBroadcast(Intent, String)` 方法一次向一个接收器发送广播。当接收器逐个顺序执行时，接收器可以向下传递结果，也可以完全中止广播
     2. `sendBroadcast(Intent)` 方法会按随机的顺序向所有接收器发送广播。
     3. `LocalBroadcastManager.sendBroadcast` 方法会将广播发送给与发送器位于同一应用中的接收器。如果您不需要跨应用发送广播，请使用本地广播。

   - 静态注册和动态注册的区别：

      1. 如果您在清单中声明广播接收器，系统会在广播发出后启动您的应用（如果应用尚未运行）

      2. 但是，一旦从 `onReceive()` 返回代码，BroadcastReceiver 就不再活跃。接收器的宿主进程变得与在其中运行的其他应用组件一样重要。如果该进程仅托管清单声明的接收器（这对于用户从未与之互动或最近没有与之互动的应用很常见），则从 `onReceive()` 返回时，系统会将其进程视为低优先级进程，并可能会将其终止，以便将资源提供给其他更重要的进程使用。

      3. `onReceive()` 完成后，系统可以随时终止进程来回收内存

         ---

         其他答案：

         1.动态的比静态的安全
         2.静态在app启动的时候就初始化了 动态使用代码初始化
         3.生存期，静态广播的生存期可以比动态广播的长很多。静态的在App未启动时就监听广播，动态则不可以
         4.优先级：同时注册时，动态广播优先于静态广播。

         注意点：

         Android8.0以后的[广播限制](https://developer.android.google.cn/about/versions/oreo/background?hl=zh-cn#broadcasts)：除了有限的例外情况，应用无法使用清单注册隐式广播。（8.0后静态注册受限制）

         

   问题：什么是ContentProvider？

   **ContentProvider的使用场景？**进行跨进程通信，实现进程间的数据交互和共享。通过Context 中 getContentResolver() 获得实例，通过 Uri匹配进行数据的增删改查。

   

   **ContentProvider**：管理数据，提供数据的增删改查操作，数据源可以是数据库、文件、XML、网络等，ContentProvider为这些数据的访问提供了统一的接口，可以用来做进程间数据共享。

   **ContentResolver**：ContentResolver可以为不同URI操作不同的ContentProvider中的数据，外部进程可以通过ContentResolver与ContentProvider进行交互。

   **ContentObserver**：观察ContentProvider中的数据变化，并将变化通知给外界。

   

2. Handler如何说（1小时）

   - 第一步：首先要说下，Handler有两个主要用途:(1)调度消息和可运行程序，以便在将来的某个时间点执行;(2)让一个动作或者说任务在其他线程上执行。最常用的就是在子线程中把更新UI的操作调度到主线程去执行。

   - 第二步：Android的消息机制相关的比较重要的类，除了Handler，还有Looper和MessageQueue。

     ​	Looper主要负责消息的循环，从MessageQueue中取消息，并交给Handler处理。

     ​	MessageQueue是消息队列，遵循先进先出的原则。

     ​    Handler是消息的发送者和处理者。

     

     - Looper.prepare()做了些什么？（new一个Looper对象，并set到当前线程的ThreadLocal中。）
     - Looper.loop()做了什么？（消息轮询，不停地去看消息队列中有没有新消息，有的话就取出并处理；没有则阻塞）

   - 其他：

     ```
     对于一个消息，handler是怎么从子线程发送，又在主线程中接收的？（handler即是消息的发送者，又是消息的接收者，但却把消息从一个线程发送到了另一个线程）
     1.这个Handler是在主线程中new的，Handler的Looper是MainLooper。那么我们只要把子线程中创建的消息放入到MainLooper的MessageQueue中就行了。（发送消息有好几个方法，但最终都会到sendMessageAtTime)
     2.消息在子线程由Handler发送后，最终会进入主线程对应的MessageQueue中
     3.主线程的Looper.loop()方法会取出Message，并调用msg.target.dispatchMessage方法，
     而在dispatchMessage方法中，又调用了handleMessage方法，而我们在主线程中new的这个Handler
     又会重写HandleMessage方法，最终就拿到了message对象。
     ```

3. OKhttp怎么说（1小时）【深刻的理解OKhttp https://zhuanlan.zhihu.com/p/150708364?utm_id=0】

   - 首先，为什么是OKhttp？之前网络请求框架很多呢，像volley，android-async-http, afinal等，为什么现在只剩下了OKhttp？OKhttp有哪些是其特有的？【其他网络框架都是基于HttpClient和HttpURLConnection的封装，而OKhttp重写了网络应用层，相比那两种基础框架，做了很多的优化】【支持 HTTP/2 的多路复用，连接池的复用机制（同一主机的多个请求可以共享一个socket）；数据压缩，通过缓存避免重复的请求，失败重连等。】

   - 核心的3大块：

     ​			（1）RealCall  【首先将 Request 封装为一个 RealCall, 这个RealCall 实现了 Call 接口，Call接口定义的就是一次网络**请求**和其对应的**响应**的抽象】

     ​				在 RealCall 类中一个请求的处理步骤主要是分为三步：

     ​						第一步：client.dispatcher().executed(new AsyncCall(responseCallback)); 添加到分发器的队列中

     ​						第二步：getResponseWithInterceptorChain(), 发起拦截链，同时得到响应后返回

     ​						第三步：client.dispatcher().finished(this); 结束这个请求。

     ​			（2）Dispatcher分发器，主要作用是维护请求队列与线程池。**这个线程池核心线程数是0**，这就意味着没有网络请求的时候，线程池几乎不占用资源；**闲置超时时间是60秒**。在执行enqueue()时，会对最大并发数有一个限制，同一个主机不能超过5个，全部最多是64个，如果不超过限制则加入运行队列并执行；否则就加入到等待队列中。等到真正执行的时候，就执行到了RealCall中封装的AsyncCall的excute方法, 这个方法通过**拦截器链**获取相应，并把结果回调给调用者。 

     ​			（3）拦截器链。（介绍一下5大拦截器，然后说一下有什么好处，方便开发者添加自己的拦截器，如token失效拦截器，日志拦截器、缓存拦截器等）【拦截器链是用了责任链设计模式，这个模式是...可以让多个对象处理同一个请求，这些节点对象就像一条链一样，请求可以沿着这条链依次传递。避免了请求的发送者和接收者直接的耦合关系。像Android中的事件分发机制就是使用了责任链模式】

     				- RetryAndFollowUpInterceptor 
     				- BridgeInterceptor 作为客户端和网络请求的桥梁[Bridge]，请求前拦截来构建请求，响应后拦截来构建响应

        - CacheInterceptor 主要是负责读取缓存和更新缓存，根据缓存策略来决定是**返回缓存**还是**请求网络**
        - ConnectIntercetor 打开一个连接到目标服务器。连接池的复用等功能都被封装在这个相关的类里了，**之所以采用复用的原因是 客户端和服务器建立 socket 连接需要经历 TCP 的三次握手和四次挥手，是一种比较消耗资源的动作。Http 中有一种 keepAlive connections 的机制，在和客户端通信结束以后可以保持连接指定的时间。OkHttp3 支持 5 个并发 socket 连接，默认的 keepAlive 时间为 5 分钟。这种复用的模式就是 设计模式中的享元模式**。【像我们使用Handler发送Message时，会通过Mesage.obtain方法从消息池去获取而不是去new一个】
        - CallServerInterceptor 这是整个拦截链的最后一个拦截器，负责和服务器发送请求和从服务器读取响应

---

## Glide

   - Glide的三级缓存是什么？（内存缓存+磁盘缓存。内存缓存又分为LRUCache和软引用。这两个缓存模块的作用各不相同，内存缓存的主要作用是防止应用重复将图片数据读取到内存当中，而硬盘缓存的主要作用是防止应用重复从网络或其他地方重复下载和读取数据。）

   三级缓存的流程：

> 当我们的APP中想要加载某张图片时，先去LruCache中寻找图片，如果LruCache中有，则直接取出来使用，并将该图片放入WeakReference中，如果LruCache中没有，则去WeakReference中寻找，如果WeakReference中有，则从WeakReference中取出图片使用，如果WeakReference中也没有图片，则从磁盘缓存/网络中加载图片。

什么是LRUCache？

> LruCache是Android提供的一个缓存类，内部采用了一个LinkedHashMap以强引用的方式存储缓存对象，提供了get和put方法来完成缓存的获取和添加操作。当缓存满时，LruCache会移除较早使用的缓存对象，然后再添加新的缓存对象。



**Glide的几个基本概念：**

Model：表示数据来源，因为Glide加载的图片资源类型有很多，可以是string、resourceID、url、file等。不论是是什么
资源类型，Glide都会把它封装成对应的Model模型。

Data：数据。从数据源中获取了Model之后，需要把它加工成数据，一般来讲都是inputstream。

Resource：把inputstream解码成bitmap后，解码之后的Data我们称之为Resource。

TransformedResource（转换）：Resource需要进行裁剪等操作，然后变为TransformedResource。

TranscodedResource（转码）：Glide除了加载静态图片外，还能加载动态图片GIF，这里就需要TranscodedResource。

Target：Glide把我们需要展示的东西封装成target然后放到对应的view上。

```java
Glide.with(getApplicationContext()) // 指定Context
.load(url)// 指定图片的URL
.placeholder(R.mipmap.ic_launcher)// 指定图片未成功加载前显示的图片
.error(R.mipmap.ic_launcher)// 指定图片加载失败显示的图片
.override(300, 300)//指定图片的尺寸
.fitCenter()//指定图片缩放类型为
.centerCrop()// 指定图片缩放类型为
.skipMemoryCache(true)// 跳过内存缓存
.diskCacheStrategy(DiskCacheStrategy.NONE)//跳过磁盘缓存
.diskCacheStrategy(DiskCacheStrategy.SOURCE)//仅仅只缓存原来的全分辨率的图像
.diskCacheStrategy(DiskCacheStrategy.RESULT)//仅仅缓存最终的图像
.diskCacheStrategy(DiskCacheStrategy.ALL)//缓存所有版本的图像
.priority(Priority.HIGH)	  //指定优先级.Glide将会用他们作为一个准则，
							//并尽可能的处理这些请求，
							// 但是它不能保证所有的图片都会按照所要求的顺序加载。优先级排序:
							//IMMEDIATE > HIGH > NORMAL >　LOW
.into(imageView);//指定显示图片的Imageview
```

- with传入的context决定了Glide的生命周期，如果传入的是activity，那么当activity被销毁后，Glide就也会被销毁。



### RecyclerView的缓存机制

RecyclerView是通过内部类`Recycler`管理的缓存

Recycler缓存ViewHolder对象有4个等级，优先级从高到底依次为：

- mAttachedScrap: ArrayList<ViewHolder>类型（屏幕中可见范围的ViewHolder）
- mCachedViews: ArrayList<ViewHolder>类型（滑动时即将与RecyclerView分离的ViewHolder，默认最多存放2个）
- mViewCachedExtension：ViewCacheExtension类型（开发者自行实现的缓存）
- mRecyclerPool: RecyclerViewPool类型（缓存池，本质上是一个SparseArray，其中的key是ViewType（int类型），value存放的是ArrayList<ViewHolder>,默认每个ArrayList中最多存放5个ViewHolder

总结一下上述流程：通过mAttachedScrap、mCachedViews及mViewCacheExtension获取的ViewHolder不需要重新创建布局及绑定数据；通过缓存池mRecyclerPool获取的ViewHolder不需要重新创建布局，但是需要重新绑定数据；如果上述缓存中都没有获取到目标ViewHolder，那么就会回调Adapter#onCreateViewHolder创建布局，以及回调Adapter#onBindViewHolder来绑定数据。



**先看总结，结合源码，再变成自己的话，想想该怎么说。**

---

