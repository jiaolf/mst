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

-  使用Bundle
- 使用文件共享，ContentProvider
- 使用Messenger【绑定服务-单线程排队执行】
- 使用AIDL【常用的，绑定服务-多线程任务】
- 使用Socket



### **Binder的理解**

> 从IPC角度来说，Binder是Android中的一种跨进程通信方式；Binder还可以理解为虚拟的物理设备，它的设备驱动是/dev/binder；从Android Framework来讲，Binder是Service Manager连接各种Manager和对应的ManagerService的桥梁。从面向对象和CS模型来讲，Client通过Binder和远程的Server进行通讯。

Binder与其他IPC比较：

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
