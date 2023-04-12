Spring Boot Logging

- Spring boot可以使用Logback、Log4J2、java util logging。
- spring boot默认使用Logback进行日志记录。 
- spring-boot-starter-logging
- 当日志文件达到10MB时，将进行轮换。

### 自定义日志配置
在 Spring Boot 的配置文件 application.porperties/yml 中，可以对日志的一些默认配置进行修改，但这种方式只能修改个别的日志配置，想要修改更多的配置或者使用更高级的功能，则需要通过日志实现框架自己的配置文件进行配置。

日志框架|	配置文件
--|--
Logback|	logback-spring.xml、logback-spring.groovy、logback.xml、logback.groov
Log4j|	2og4j2-spring.xml、log4j2.xml
JUL (Java Util Logging)|	logging.properties

### 配置文件 参数说明 src\main\resources\application.properties
- logging.level.* : 它用作包名称的前缀，用于设置日志级别。
- logging.file : 它配置了一个日志文件名来记录文件中的信息。我们也可以用绝对路径配置文件名。
- logging.path : 它只配置了日志文件的路径。Spring boot创建了一个名为spring.log的日志文件。
- logging.pattern.console : 它定义了控制台中的日志模式。
- logging.pattern.file: 它定义了文件中的日志模式。
- logging.pattern.level: 它定义了渲染日志级别的格式。默认是%5p。
- logging.exception-conversion-word : 它定义了记录异常时的转换词。
- PID : 它定义了当前的进程ID。

### 配置文件 logback.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
 
<!--
    scan:当此属性设置为true时，配置文件如果发生改变，将会被重新加载，默认值为true。
    scanPeriod:设置监测配置文件是否有修改的时间间隔，如果没有给出时间单位，默认单位是毫秒。当scan为true时，此属性生效。默认的时间间隔为1分钟。
    debug:当此属性设置为true时，将打印出logback内部日志信息，实时查看logback运行状态。默认值为false。-->
<configuration scan="true" scanPeriod="60 seconds" debug="false">
    <!--定义日志文件的存储地址 勿在 LogBack 的配置中使用相对路径 -->
    <property name="LOG_HOME" value="D:/logback" />
    <!-- 控制台输出 -->
     <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender"> 
        <encoder> 
            <!-- %.-1level 只显示信息级别的首字母,%-5level 左对齐显示信息级别全称 --> 
            <Pattern>[%date{yyyy-MM-dd HH:mm:ss}] [%-5level] --%mdc{client} %msg%n</Pattern> 
        </encoder> 
    </appender> 
    <!-- 每天生成日志文件,文件大小超过50则新生成一个文件，同时将旧文件按${LOG_HOME}/logs/aa.%d{yyyy-MM-dd}.%i.log.zip格式压缩，文件保存30天 -->
    <appender name="AA" class="ch.qos.logback.core.rolling.RollingFileAppender"> 
        <file>${LOG_HOME}/aa.log</file> <!-- 日志名称 -->
        <rollingPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy"> 
            <fileNamePattern>${LOG_HOME}/logs/aa.%d{yyyy-MM-dd}.%i.log.zip</fileNamePattern> 
            <maxFileSize>50MB</maxFileSize>  <!-- 日志文件过大会使的编辑器打开非常慢，因此设置日志最大50MB -->
            <maxHistory>30</maxHistory>  <!-- 保存30天 -->
            <totalSizeCap>10GB</totalSizeCap>  <!-- 总日志大小 -->
        </rollingPolicy> 
        <!-- encoder负责两件事，一是把日志信息转换成字节数组，二是把字节数组写入到输出流。 -->
        <encoder> 
            <pattern>[%date{yyyy-MM-dd HH:mm:ss}] [%-5level] [%logger:%line] --%mdc{client} %msg%n</pattern> 
        </encoder> 
        <!-- 过滤器，可以过滤掉不符合条件的日志，INFO及以上的日志被处理，其它的拒绝 -->
        <filter class="ch.qos.logback.classic.filter.LevelFilter"> 
            <level>INFO</level> 
            <onMatch>ACCEPT</onMatch> 
            <onMismatch>DENY</onMismatch> 
        </filter> 
    </appender>
 
 
    <appender name="BB" class="ch.qos.logback.core.rolling.RollingFileAppender"> 
        <file>${LOG_HOME}/bb.log</file> 
        <rollingPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy"> 
            <fileNamePattern>${LOG_HOME}/logs/bb.%d{yyyy-MM-dd}.%i.log.zip</fileNamePattern> 
            <maxFileSize>50MB</maxFileSize> 
            <maxHistory>30</maxHistory> 
            <totalSizeCap>10GB</totalSizeCap> 
        </rollingPolicy> 
        <encoder> 
            <pattern>[%date{yyyy-MM-dd HH:mm:ss}] [%-5level] [%logger:%line] --%mdc{client} %msg%n</pattern> 
        </encoder> 
        <filter class="ch.qos.logback.classic.filter.LevelFilter"> 
            <level>INFO</level> 
            <onMatch>ACCEPT</onMatch> 
            <onMismatch>DENY</onMismatch> 
        </filter> 
    </appender>
     
     
    <appender name="update" class="ch.qos.logback.core.rolling.RollingFileAppender"> 
        <file>${LOG_HOME}/update.log</file> 
        <rollingPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy"> 
            <fileNamePattern>${LOG_HOME}/logs/update.%d{yyyy-MM-dd}.%i.log.zip</fileNamePattern> 
            <maxFileSize>50KB</maxFileSize> 
            <maxHistory>7</maxHistory> 
            <totalSizeCap>10GB</totalSizeCap> 
        </rollingPolicy> 
        <encoder> 
            <pattern>[%date{yyyy-MM-dd HH:mm:ss}] [%-5level] [%logger:%line] --%mdc{client} %msg%n</pattern> 
        </encoder> 
        <filter class="ch.qos.logback.classic.filter.LevelFilter"> 
            <level>INFO</level> 
            <onMatch>ACCEPT</onMatch> 
            <onMismatch>DENY</onMismatch> 
        </filter> 
    </appender>
    <!-- java项目中com.example.demo包下通过LoggerFactory.getLogger(Demo.class)获取的日志全部由AA appender处理 -->
    <logger name="com.example.demo" additivity="false">
        <appender-ref ref="AA" />
    </logger>
    <!-- java项目中com.example.demo包下通过LoggerFactory.getLogger(Demo2.class)获取的日志全部由BB appender处理 -->
     <logger name="com.example.demo.bb">
        <appender-ref ref="BB" />
    </logger>
    <!-- java项目中任意类中通过LoggerFactory.getLogger("update")获取的日志全部由update appender处理 ,
        例如想把所有的更新操作的日志放在一起，便于查看
    -->
    <logger name="update" >
        <appender-ref ref="update" />
    </logger>
    <!-- 根日志,所有logger默认继承自root，打印信息向上级传递，所以以上logger所打印的日志默认被 STDOUT appender再处理一遍，
        也就是会被打印到控制台，可以再通过设置logger的additivity="false"，使得不再向上传递
    -->
    <root level="DEBUG">
        <appender-ref ref="STDOUT" />
    </root>
</configuration>
```



### logging.level | 设置日志级别
- TRACE 追踪，指明程序运行轨迹。
- DEBUG 调试，实际应用中一般将其作为最低级别，而 trace 则很少使用。
- INFO 输出重要的信息，使用较多。
- WARN 警告，使用较多。
- ERROR 错误信息，使用较多。
- FATAL
- OFF

### 输出格式
序号	|输出格式	|说明
--|--|--
1	|%d{yyyy-MM-dd HH:mm:ss, SSS}	|日志生产时间,输出到毫秒的时间
2	|%-5level	                    |输出日志级别，-5 表示左对齐并且固定输出 5 个字符，如果不足在右边补 0
3	|%logger 或 %c	|logger 的名称
4	|%thread 或 %t	|输出当前线程名称
5	|%p	|日志输出格式
6	|%message 或 %msg 或 %m	|日志内容，即 logger.info(“message”)
7	|%n	|换行符
8	|%class 或 %C	|输出 Java 类名
9	|%file 或 %F	|输出文件名
10	|%L	|输出错误行号
11	|%method 或 %M	|输出方法名
12	|%l	|输出语句所在的行数, 包括类名、方法名、文件名、行数
13	|hostName	|   本地机器名
14	|hostAddress	|本地 ip 地址



### 配置控制台输出格式 logging.pattern.console
logging.pattern.console= %d{yyyy-MMM-dd HH:mm:ss.SSS} %-5level [%thread] %logger{15} - %msg%n  

### 配置日志文件输出格式 logging.pattern.file 
logging.pattern.file= %d{yyyy-MMM-dd HH:mm:ss.SSS} %-5level [%thread] %logger{15} - %msg%n


### 使用自定义日志：Log4j2
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-logging</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-log4j2</artifactId>
</dependency>
```
![图1](https://smypai.github.io/photo/img_6.png)
