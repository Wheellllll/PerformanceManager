## 性能模块
> 这是一个简单的性能管理模块，可以自由添加指标、定时输出日志到文件以及按需压缩.

### 安装

#### Maven

```xml
<repositories>
    <repository>
        <id>tahiti-nexus-snapshots</id>
        <name>Tahiti NEXUS</name>
        <url>http://sse.tongji.edu.cn/tahiti/nexus/content/groups/public</url>
        <releases><enabled>false</enabled></releases>
        <snapshots><enabled>true</enabled></snapshots>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>wheellllll</groupId>
        <artifactId>PerformanceManager</artifactId>
        <version>2.0-SNAPSHOT</version>
    </dependency>
</dependencies>
```

#### 手工下载
从 https://github.com/Wheellllll/PerformanceManager/releases 下载最新的jar包添加到项目依赖里去

### 使用说明
本模块是`线程安全`的，您可以在多个线程中对同一个指标进行更新操作
本模块分为3个部分，分别完成3种不同的功能

- IntervalLogger 定时输出型的日志记录器
- RealtimeLogger 实时输出型的日志记录器
- ArchiveManager 定时压缩日志文件

### IntervalLogger
本记录器负责管理指标并定时输出指标到文件，首先创建一个实例
```java
IntervalLogger intervalLogger = new IntervalLogger();
```

#### 指定输出文件夹和输出文件名
输出日志的文件命名方式如下所示：
`${prefix} ${date}.${suffix}`

比如说：
```
prefix=server
date=yyyy-MM-dd HH:mm
suffix=log
```
则生成的日志文件名称会长成这个样子：
`server 2016-04-21 19:32.log`
`server 2016-04-21 19:34.log`
`server 2016-04-21 19:36.log`
请注意，如果你使用的是`Windows系统`，请不要在文件名中出现`":"`字符，否则你的电脑会爆炸

前缀，后缀和日期格式可以通过以下方法指定
```java
intervalLogger.setLogDir("./log");        //输出到当前工作目录下的log文件夹里
intervalLogger.setLogPrefix("server");      //设置日志的前缀为server
intervalLogger.setDateFormat("yyyy-MM-dd HH_mm_ss");  //日期格式类似2016-04-21 19_36_30
```
更多关于日期的格式化方法请参考 [https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html](https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html)

#### 指定输出的时间间隔
```java
intervalLogger.setInterval(1, TimeUnit.SECONDS);  //每隔1秒钟输出一次
intervalLogger.setInitialDelay(1);                //延时1秒后执行
```

#### 添加指标
```java
intervalLogger.addIndex("loginSuccess");
intervalLogger.addIndex("loginFail");
```

#### 删除指标
```java
intervalLogger.removeIndex("loginSuccess");
```

#### 更新指标
```java
intervalLogger.updateIndex("loginSuccess", 1);    //loginSuccess指标加1
intervalLogger.updateIndex("loginFail", 2);       //loginFail指标加2
```

#### 将指标数值清除（直接删除指标）
```java
pm.clear();
```

#### 自定义输出格式
```java
intervalLogger.setFormatPattern(
    "Login Success Number : ${loginSuccess}\nLogin Fail Number : ${loginFail}\n\n"
);
```
其中类似`${variable}`的样式就会在输出的时候被具体的指标数值代替，比如说上述例子，输出格式有可能会是这个样子：
```
Login Success Number : 1
Login Fail Number : 2

Login Success Number : 3
Login Fail Number : 4

Login Success Number : 5
Login Fail Number : 6
...
```

#### 启动
```java
pm.start();
```

#### 关闭
```java
pm.stop();
```

### RealtimeLogger
本记录器负责实时将相关信息输出到指定的文件，首先创建一个实例
```java
RealtimeLogger realtimeLogger = new RealtimeLogger();
```

#### 指定输出文件夹和输出文件名
输出日志的文件命名方式如下所示，注意，这里没有日期：
`${prefix}.${suffix}`

比如说：
```
prefix=server
suffix=log
```
则生成的日志文件名称会长成这个样子：
`server.log`

前缀和后缀可以通过以下方法指定
```java
realtimeLogger.setLogDir("./log");        //输出到当前工作目录下的log文件夹里
realtimeLogger.setLogPrefix("server");      //设置日志的前缀为server
```

#### 自定义输出格式
```java
realtimeLogger.setFormatPattern(
    "Username : ${username}\nTime : ${time}\nMessage : ${message}\n\n"
);

HashMap<String, String> record = new HashMap<>();
record.put("username", "Sweet");
record.put("time", "2016-04-21 20:19:40");
record.put("message", "foo");

realtimeLogger.log(record);  //将信息写到文件里去

record.put("username", "Black");
record.put("time", "2016-04-21 20:21:50");
record.put("message", "bar");

realtimeLogger.log(record);  //将信息写到文件里去
```

其中类似`${variable}`的样式就会在输出的时候被具体的指标数值代替，比如说上述例子，输出格式有可能会是这个样子：
```
Username : Sweet
time : 2016-04-21 20:19:40
message : foo

Username : Black
time : 2016-04-21 20:21:50
message : bar
```


### ArchiveManager
本部分是上述两种记录器的辅助模块，负责定时将它们输出的文件打包并保存，首先创建一个实例：
```java
ArchiveManager am = new ArchiveManager();
```

#### 指定输出文件夹和输出文件名
输出日志的文件命名方式如下所示，注意，这里不能指定后缀名，因为我们支持zip格式的压缩：
`${prefix} ${date}.zip`

比如说：
```
prefix=server
date=yyyy-MM-dd
```
则生成的压缩文件名称会长成这个样子：
`server 2016-04-21.zip`
`server 2016-04-22.zip`
`server 2016-04-23.zip`
请注意，如果你使用的是`Windows系统`，请不要在文件名中出现`":"`字符，否则你的电脑会爆炸

前缀，后缀和日期格式可以通过以下方法指定
```java
intervalLogger.setLogDir("./archive");        //输出到当前工作目录下的archive文件夹里
intervalLogger.setLogPrefix("server");        //设置日志的前缀为server
intervalLogger.setDateFormat("yyyy-MM-dd");  //日期格式类似2016-04-21
```
更多关于日期的格式化方法请参考 [https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html](https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html)

#### 指定输出的时间间隔
```java
am.setInterval(1, TimeUnit.DAYS);  //每隔1天压缩一次
am.setInitialDelay(1);             //延时1天后执行
```

#### 添加需要压缩的记录器
```
am.addLogger(intervalLogger);
am.addLogger(realtimeLogger);
```

#### 启动
```java
am.start();
```

#### 关闭
```java
am.stop();
```

###一个完整的例子
```java
//Initial Interval Logger
IntervalLogger logger1 = new IntervalLogger();
logger1.setLogDir("./log");
logger1.setLogPrefix("test");
logger1.setInterval(1, TimeUnit.SECONDS);
logger1.addIndex("loginSuccess");
logger1.addIndex("loginFail");
logger1.setFormatPattern("Login Success Number : ${loginSuccess}\nLogin Fail Number : ${loginFail}\n\n");

//Initial Realtime Logger
RealtimeLogger logger2 = new RealtimeLogger();
logger2.setLogDir("./llog");
logger2.setLogPrefix("test");
logger2.setFormatPattern("Username : ${username}\nTime : ${time}\nMessage : ${message}\n\n");

//Initial Archive Manager
ArchiveManager am = new ArchiveManager();
am.setArchiveDir("./archive");
am.setDatePattern("yyyy-MM-dd HH:mm");
am.addLogger(logger1);
am.addLogger(logger2);
am.setInterval(1, TimeUnit.DAYS);
logger1.start();
am.start();

//Test
for (int i = 0; i < 300; ++i) {
    logger1.updateIndex("loginSuccess", 1);
    logger1.updateIndex("loginFail", 2);
    HashMap<String, String> map = new HashMap<>();
    map.put("username", "Sweet");
    map.put("time", "2016-04-21");
    map.put("message", "Hello World - " + i);
    logger2.log(map);
    try {
        Thread.sleep(1000);
        System.out.println("" + i);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    logger1.stop();
    am.stop();
}
```
