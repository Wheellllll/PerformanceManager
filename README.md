## 性能模块
> 这是一个简单的性能管理模块，可以自由添加指标和每分钟输出日志

### 安装
从 https://github.com/Wheellllll/PerformanceManager/releases 下载最新的jar包添加到项目依赖里去

### 使用说明

#### 获取实例
```java
PerformanceManager pm = new PerformanceManager();
```

#### 指定输出地址和输出文件名
```java
LogUtils.setLogPrefix("Server");  //设置输出的文件名
LogUtils.setLogPath("./log");   //设置输出文件的路径
```

#### 指定输出的时间间隔
```java
pm.setTimeUnit(TimeUnit.SECONDS);   //时间单位为秒
pm.setInitialDelay(1);              //延时1秒后执行
pm.setPeriod(60);                    //循环周期为60秒即1分钟
```

#### 添加指标
```java
pm.addIndex("loginSuccess");
pm.addIndex("loginFail");
```

#### 删除指标
```java
pm.removeIndex("loginSuccess");
```

#### 更新指标
```java
pm.updateIndex("loginSuccess", 1);    //loginSuccess指标加1
pm.updateIndex("loginFail", 2);       //loginFail指标加2
```

#### 启动
```java
pm.start();
...
...
...
```


#### 将指标数值清除为0
```java
pm.clear();
```

#### 关闭
```java
pm.stop();
```
