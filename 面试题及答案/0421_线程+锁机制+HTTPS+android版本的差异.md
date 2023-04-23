一、sleep、wait/notify/notifyAll

- sleep是Thread的静态方法，wait是object的方法。

- sleep不释放锁，wait释放锁。
- 

二、Synchronized，监视器，Lock，ReentrantLock，条件和信号



三、阻塞队列，jdk提供的四种线程池中的阻塞队列分别是什么？

阻塞队列，首先它是一个队列的数据结构，有两种方式会引起线程的阻塞。一种是放入数据时，如果队列已经满了，则会引起线程的阻塞。另一种是取数据时，如果队列是空的，也会引起线程的阻塞。

常见的阻塞队列有：

```java

SynchronousQueue sq; //同步阻塞队列。同步队列没有任何内部容量，甚至没有1的容量。
BlockingQueue bq1 = new LinkedBlockingQueue(); //链表结构的阻塞队列。容量为Integer.MaxValue
BlockingQueue bq2 = new ArrayBlockingQueue(10); //这个容量，就是说允许排队的数量
BlockingQueue bq3 = new PriorityBlockingQueue(); //优先级队列
BlockingQueue bq4 = new DelayQueue(); //
BlockingQueue bq5 = new LinkedTransferQueue(); //
BlockingQueue bq6 = new LinkedBlockingDeque(); // 双端阻塞队列
```

