## 性能模块
> 这是一个简单的性能管理模块，可以自由添加指标、定时输出日志到文件以及按需压缩.

### 警告
本模块经过2.0版本后经过重构，如果想使用旧版的api请保持版本到1.3（文档在[https://github.com/Wheellllll/PerformanceManager/blob/master/README-1.0.md](https://github.com/Wheellllll/PerformanceManager/blob/master/README-1.0.md)），不要升级到2.0以上

### ChangeLog
#### v2.1.4
- 功能扩展，加入对文件大小和总文件大小的限制
- `ArchiveManager`模块移至专门的仓库，请移步[ArchiveManager](https://github.com/Wheellllll/ArchiveManager)

#### v2.1.3
- `ArchiveManager`添加新的api`addFolder`

#### v2.1.2
- 消除控制台输出
- 修复压缩时会把上一次结果压缩进来的bug

#### v2.1.1
- 修复空压缩的错误

#### v2.1.0
- 修复IntervalLogger不能输出的bug
- 增量压缩，不再删除历史记录

#### v2.0.1
- 添加字符串输出功能

#### v2.0
- 重构
- 添加实时记录器
- 添加压缩功能

#### v1.3
- 修复log输出的文件夹问题

#### v1.2
- 加锁，成为线程安全的

#### v1.1
- 修复`windows`系统的`":"`bug

#### v1.0
- 诞生

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
        <version>2.1.4-SNAPSHOT</version>
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
- `server 2016-04-21 19:32.log`
- `server 2016-04-21 19:34.log`
- `server 2016-04-21 19:36.log`

请注意，如果你使用的是`Windows系统`，请不要在文件名中出现`":"`字符，否则你的电脑会爆炸

前缀，后缀和日期格式可以通过以下方法指定
```java
intervalLogger.setLogDir("./log");        //输出到当前工作目录下的log文件夹里
intervalLogger.setLogPrefix("server");    //设置日志的前缀为server
intervalLogger.setLogSuffix("log");       //设置日志的后缀为log
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
intervalLogger.clear();
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
intervalLogger.start();
```

#### 关闭
```java
intervalLogger.stop();
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
realtimeLogger.setLogDir("./log");          //输出到当前工作目录下的log文件夹里
realtimeLogger.setLogPrefix("server");      //设置日志的前缀为server
realtimeLogger.setLogSuffix("log");         //设置日志的后缀为log
```

#### 普通输出
```java
String message = "Hello World\n";
realtimeLogger.log(message); //写String到文件里去
```


#### 文艺输出(自定义格式输出)
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
####使用logback接口
本模块既可以使用该模块自己实现的日志功能，也可以实现Logback的日志功能；使用Logback的日志功能的示例如下：

```java
org.slf4j.Logger logger1 = LoggerFactory.getLogger(XXX.class);//org.slf4j.Logger 的使用方法可以参看logback的官方文档
IntervalLogger logViaLogback1 = new IntervalLogger(logger1);
....
//其他照旧

org.slf4j.Logger logger2 = LoggerFactory.getLogger(XXX.class);
RealtimeLogger logViaLogback2 = new RealtimeLogger(logger2)
....
//其他照旧
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
### 文件大小限制及总文件大小限制
通过方法```setMaxFileSize```和```setMaxTotalSize```设置最大值：
```java
Logger logger = new IntervalLogger();
logger.setMaxFileSize(500, Logger.SizeUnit.KB); //第一个参数是数值，第二个参数是单位
logger.setMaxTotalSize(200, Logger.SizeUnit.MB); //第一个参数是数值，第二个参数是单位
```
提供四种单位：B，KB， MB， GB


#### 注意
请合理设置最大值，例如当设置了总大小限制后，单个文件的最大值不应该超过总最大值。该构件会根据当前设置的值判断是否需要修改另一个值以保证合乎逻辑。如果你的设置不太正确，结果可能会与预期有差别。

当写入单个文件的内容大于最大值时，会舍弃末尾多出的内容；当一个目录下的文件大小达到最大值时，会根据设置删除最早的或者最新的文件，可以通过```setTruncateLatest```方法进行设置：
```java
logger.setTruncateLatest(true);
```
当参数为true时，会删除最新的内容，当参数为false时，会删除最早的内容，默认情况下truncateLatest的值为false，即删除最早的内容。

### 一个完整的例子
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
}
```
