# xandroid

1.xandroid为一个完整的基于MVP模式的安卓企业级快发框架，该框架集成Android开发的常用主流框架。

2.该框架主要集成xUtils3、gson、dagger2、rxjava2、lambda、rxpermissions、retrofit2、ZXing、ZBar、imagepicker、picasso、arcgis、leakcanary

    xUtils3：xUtils 包含了很多实用的android工具。
             
	     xUtils 最初源于Afinal框架，进行了大量重构，使得xUtils支持大文件上传，更全面的http请求协议支持(10种谓词)，拥有更加灵活的ORM，更多的事件注解支持且不受混淆影响...
             
	     xUitls最低兼容android 2.2 (api level 8)

             本框架主要使用xUtils3的ORM模块。

    gson：Gson 是google解析Json的一个开源框架,同类的框架fastJson,JackJson等等

             本框架主要使用gson与retrofit2来解析远程接口数据。

    dagger2：dagger2是解决Android或java中依赖注入的一个类库（DI类库）

             在本框架中M和P层的调用都是通过dagger2来实现。

    rxjava2：rxjava2一个知名的响应式编程库，rxjava2主要特点是简化异步操作、链式操作。

             本框架中主要使用rxjava2+lambda+retrofit2实现网络操作，同时基于rxjava2对Arcgis进行封装。

    lambda：安卓环境支持java8的lambda表达式。

    rxpermissions：一个基于rxjava2的android运行时权限申请框架。

    retrofit2：它是Square公司开发的现在非常流行的网络框架。主要特点：
    
	      性能好，处理快，使用简单，Retrofit 是安卓上最流行的HTTP Client库之一 

	      使用REST API设计风格 

              支持 NIO(new i/o) 

              默认使用OKHttp处理网络请求，可以看成是OKHttp的增强。 

              默认使用Gson解析

    ZXing和ZBar：是两个Android二维码生成扫描类库。

    imagepicker：图片选择操作第三方类库

    picasso：Android图片加载库

    arcgis：集成ArcGIS for Android 包括二维gis和三维场景

    leakcanary：Android 内存泄漏分析第三方类库


