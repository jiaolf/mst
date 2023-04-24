## 一、app的启动流程

1. 用户点击App图标，Launcher进程通过Binder联系到SystemServer进程，发起startActivity【Launcher，Binder，SystemServer，startActivity】
2. SystemServer通过Socket联系到Zygote，fork出一个新的App进程【SystemServer，socket,Zygote,fork新进程】
3. 创建出一个新的App进程后，Zygote启动App进程的ActivityThread#main()方法【Zygote启动App进程进入main方法】
4. 在ActivityThread中，调用AMS进行ApplicationThread的绑定
5. AMS发送创建Application的消息给ApplicationThread，进而转交给ActivityThread中的H，也就是Handler，接着进行Application的创建工作，接着AMS已同样的方式创建Activity
6. 最后通过ActivityThread.handleLaunchActivity()->performLaunchActivity()->Instrumentation.callActivityOnCreate(),最终我们的程序执行到了onCreate方法。

![img](https://upload-images.jianshu.io/upload_images/9271486-89af7a139a1ab82a.png)

---



### 二、跨进程通信的方式【最好能结合代码实例】

4-23号的面试：通过ServiceConnection获取到了IBinder对象，但IBinder只是一个接口，你是如何调用服务呢？

```java
private ServiceConnection mWeatherServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
			mAIDLWeatherStub = IWeather.Stub.asInterface(service);
            // 这个mAIDLWeatherStub就是一个IWeather对象
            // IWeather是一个aidl接口文件，里面定义了天气相关的方法，这样我们就通过IBinder
            // 来完成和Service的远程调用了。（因为在远程Service中对IWeather接口做了相关实现）
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };
```

所以说，对于跨进程间通信，必须结合代码或者实际的应用场景来学习。这样才能真正的掌握。

**IPC的几种方式：**

- 使用Bundle

  ```
  Bundle实现了Parcelable接口，而Intent中携带的数据，就是保存在了Bundle对象中。Activity、Service、Receiver之间就可以通过相互调用传递Intent来实现跨进程通信。所以说，Bundle是一种最简单的跨进程通信的方式。
  ```

  

- 使用文件共享，以及SharedPreference等

- ContentProvider

- 使用Messenger【是对AIDL的封装，底层是Binder。绑定服务-单线程排队执行】

  ```java
  public Messenger(Handler target){
  	mTarget = target.getIMessenger();
  }
  
  public Messenger(IBinder target){
  	mTarget = IMessenger.Stub.asInterface(target);
  }
  ```

  

- 使用AIDL【底层是Binder。常用的，绑定服务-多线程任务】

  ```java
  比如:
  Weather.aidl（里面就一行代码parcelable Weather;)
  Weather.java（需要实现Parcelable接口)
  IWeatherManager.aidl(获取天气数据等方法)
  
  AIDL文件编译后，如IWeatherManager.aidl -> IWeatherManager.java 文件
      
  里面有一个实现了IWeatherManager接口、并继承android.os.Binder的抽象类Stub。以及Stub的实现类，也是代理类Proxy
      
  Stub类中的重要方法：
  asInterface(android.os.IBinder obj)
  asBinder
  onTransact
      
  Proxy类
  ```

  

- 使用Socket【socket不仅可以用于两台不同的主机上通信，也可以在同一主机上不同的进程间通信】



在Android上实现多进程的具体操作：(四大组件activity、service、Receiver、ContentProvider都一样)

```java
//方式一
<activity
	android:name="com.jlf.dream"
	android:process=":remote"/> //进程名称 com.jlf.dream:remote
        
//方式二
<activity
    andorid:name="com.jlf.dream"
    android:process="com.jlf.dream.remote"/> //进程名称 com.jlf.dream.remote
        
区别：
":remote" 进程名以“:”开头的进程属于当前应用的私有进程，其他应用组件不可以和它跑在同一个进程中。
"com.jlf.dream.remote" 不以“:”开头的属于全局进程，其他应用通过ShareUID方式可以和它跑在同一个进程中。    
    
每个应用都有唯一的UID，如果两个应用有相同的UID并且签名相同，才可以通过ShareUID跑在同一个进程中。
```

使用多进程会遇到的问题：

1. 静态成员和单例模式完全失效。（虽然看起来在同一个项目中，但他们相当于是两个独立的应用）
2. 线程同步机制完全失效（相当于两块内存，因此无论是锁对象还是锁类都无法保证同步）
3. SharedPreferences的可靠性下降（并发读写xml问题）
4. Application会多次创建

> 对于同一个应用的多进程，就相当于两个不同的应用采用了SharedUID模式

### **Binder的理解**

> 从IPC角度来说，Binder是Android中的一种跨进程通信方式；Binder还可以理解为虚拟的物理设备，它的设备驱动是/dev/binder；从Android Framework来讲，Binder是Service Manager连接各种Manager和对应的ManagerService的桥梁。从面向对象和CS模型来讲，Client通过Binder和远程的Server进行通讯。

Binder与其他IPC（这里的其他，主要是指linux上的IPC方式，因为Android上的Messenger或者AIDL，本质上还是Binder）比较：

- 效率高：除了内存共享外，其他IPC都需要进行两次数据拷贝，而因为Binder使用内存映射的关系，仅需要一次数据拷贝。
- 安全性好：接收方可以从数据包中获取发送发的进程Id和用户Id，方便验证发送方的身份，其他IPC想要实验只能够主动存入，但是这有可能在发送的过程中被修改。

#### Binder的通信过程？Binder的原理？

![img](https://upload-images.jianshu.io/upload_images/9271486-3ef2e63be902de04.jpeg?imageMogr2/auto-orient/strip|imageView2/2/w/1002/format/webp)

Binder的结构：
`Client`：服务的请求方。
`Server`：服务的提供方。
`Service Manager`：为`Server`提供`Binder`的注册服务，为`Client`提供`Binder`的查询服务，`Server`、`Client`和`Service Manage`r的通讯都是通过Binder。
`Binder驱动`：负责Binder通信机制的建立，提供一系列底层支持。



从上图中，Binder通信的过程是这样的：

1. Server在Service Manager中注册：Server进程在创建的时候，也会创建对应的Binder实体，如果要提供服务给Client，就必须为Binder实体注册一个名字。
2. Client通过Service Manager获取服务：Client知道服务中Binder实体的名字后，通过名字从Service Manager获取Binder实体的引用。
3. Client使用服务与Server进行通信：Client通过调用Binder实体与Server进行通信。

更详细一点？

Binder通信的实质是利用内存映射，将用户进程的内存地址和内核的内存地址映射为同一块物理地址，也就是说他们使用的同一块物理空间，每次创建Binder的时候大概分配128的空间。数据进行传输的时候，从这个内存空间分配一点，用完了再释放即可。
