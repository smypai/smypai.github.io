* 一：添加依赖：maven依赖中添加了spring-boot-starter-logging
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-logging</artifactId>
</dependency>
```

* 二：默认配置：默认情况下Spring Boot将日志输出到控制台，不会写到日志文件。  
如果要编写除控制台输出之外的日志文件，则需在application.properties中设置logging.file或logging.path属性

* 三：logback-spring.xml详解  
Spring Boot官方推荐优先使用带有-spring的文件名作为你的日志配置（如使用logback-spring.xml，而不是logback.xml），命名为logback-spring.xml的日志配置文件，将xml放至 src/main/resource下面。
Logger, Appenders and Layouts（记录器、附加器、布局）：Logback基于三个主要类：Logger，Appender和Layout。 这三种类型的组件协同工作，使开发人员能够根据消息类型和级别记录消息，并在运行时控制这些消息的格式以及报告的位置。首先给出一个基本的xml配置如下：

```xml
<configuration>
 
  <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
    <!-- encoders are assigned the type
         ch.qos.logback.classic.encoder.PatternLayoutEncoder by default -->
    <encoder>
      <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
    </encoder>
  </appender>
 
  <logger name="chapters.configuration" level="INFO"/>
 
  <!-- Strictly speaking, the level attribute is not necessary since -->
  <!-- the level of the root level is set to DEBUG by default.       -->
  <root level="DEBUG">          
    <appender-ref ref="STDOUT" />
  </root>  
  
</configuration>
```
* 3.1：<configuration> 根节点<configuration>有 5 个子节点  
logback.xml配置文件的基本结构可以描述为<configuration>元素，
  - 包含零个或多个<appender>元素，
  - 后跟零个或多个<logger>元素，
  - 后跟最多一个<root>元素(也可以没有)。

* root 节点 是必选节点，用来指定最基础的日志输出级别  <br /> 
  * 只有一个 level 属性，用于设置打印级别，可选如下：TRACE,DEBUG,INFO,WARN,ERROR,ALL,OFF。<br /> 
  * root 节点可以包含 0 个或多个元素，将appender添加进来。

* contextName 节点 设置上下文名称 <br /> 
  * 默认为default，可通过%contextName来打印上下文名称，一般不使用此属性。

* property 节点 用于定义变量，方便使用。<br /> 
  * 有两个属性：name,value。定义变量后，可以使用${}来使用变量。

* appender 节点  用来格式化日志输出的节点，这个最重要。有两个属性：
  * name:该本 appender 命名
  * class:指定输出策略，通常有两种：控制台输出，文件输出

* logger 节点 构成
  * 此节点用来设置一个包或具体的某一个类的日志打印级别、以及指定<appender>，有以下三个属性：
  * name: 必须。用来指定受此 loger 约束的某个包或者某个具体的类
  * level：可选。设置打印级别。默认为 root 的级别。
  * addtivity: 可选。是否向上级 loger(也就是 root 节点)传递打印信息。默认为 true。