## jetpack相关的组件库

1）ViewModel （）		
2）LiveData （）		
3）LifeCycle （构建生命周期感知型组件）		
4）DataBinding （使用声明性格式将布局中的界面组件绑定到应用中的数据源）		
5）Dagger2、Hilt（依赖注入的框架  @Module @Component @Provides @Inject @Singleton）
6）WorkManager （后台作业的框架）
7）Room（创建、存储和管理由 SQLite 数据库支持的持久性数据）
8）compose
9）constraintlayout
10）recyclerview
11）viewpager2
12）navigation

## Android KTX

Android KTX 是包含在 Android Jetpack 及其他 Android 库中的一组 Kotlin 扩展程序。
1）Core KTX		implementation "androidx.core:core-ktx:1.9.0"
			Core KTX 模块为属于 Android 框架的通用库提供扩展程序

2）Collection KTX	implementation "androidx.collection:collection-ktx:1.2.0"
			Collection 扩展程序包含在 Android 的节省内存的集合库中使用的实用函数，
			包括 ArrayMap、LongSparseArray、LruCache 等等。

3）Fragment KTX		implementation "androidx.fragment:fragment-ktx:1.5.5"

4）Lifecycle KTX	implementation "androidx.lifecycle:lifecycle-runtime-ktx:2.5.1"
			Lifecycle KTX 为每个 Lifecycle 对象定义一个 LifecycleScope。在此范围
			内启动的协程会在 Lifecycle 被销毁时取消

5）LiveData KTX

6）SQLite KTX

7）Room KTX

8）ViewModel KTX

十五、kotlin相关的库或技术
1）Koin 依赖注入库，但不同于Dagger2以注解的方式，这个更简单好用
2）Anko 是一个使Android应用开发更快、更容易Kotlin库 https://github.com/Kotlin/anko
3）kotlinx.coroutines implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
4）kotlinx-datetime 
5）kotlinx.serialization 
6）Kotlin Flow
7）coil	一个kotlin的图片加载库，对应java的Glide
8）

其他：了解kotlin相关的插件

## compose

