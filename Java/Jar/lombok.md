### Lombok 的 @EqualsAndHashCode(callSuper = false) 的使用

> @EqualsAndHashCode(callSuper = true)

该注解用于子类对象之间进行比较的时候

不加该注解的影响：子类对象属性值一致，但其继承的父类对象属性值不一致，在比较的时候会出现比较结果不对的情况。


## lombok
### 背景

知道有这么个东西，是因为项目中用到了@Slf4j注解。

lombok库提供了一些注解来简化java代码

官网：http://projectlombok.org/

查看lombok所有api：https://projectlombok.org/api/overview-summary.html

### 几个常用的 lombok 注解：
- @Data：注解在类上；提供类所有属性的 getting 和 setting 方法，此外还提供了equals、canEqual、hashCode、toString 方法
- @Setter：注解在属性上；为属性提供 setting 方法
- @Getter：注解在属性上；为属性提供 getting 方法
- @SneakyThrows：无需在签名处显式抛出异常
- @Log4j：注解在类上；为类提供一个 属性名为log 的 log4j 日志对像
- @Slf4j: 同上
- @NoArgsConstructor：注解在类上；为类提供一个无参的构造方法
- @AllArgsConstructor：注解在类上；为类提供一个全参的构造方法

### 实现原理：
Lombok不是通过字节码改写来实现的。
它主要是用编译器内支持的annotation processing，直接操纵抽象语法树（AST），根据需要添加新节点。
（讲真的，不太懂，java基础不是太好，这一段来自https://www.jianshu.com/p/d0a68a9b46ae）

### 使用方法 在pom文件添加
```xml
<dependency>
<groupId>org.projectlombok</groupId>
<artifactId>lombok</artifactId>
</dependency>
```

代码中使用（举两个例子，更多的可以看官网api）

```java
@Slf4j使用
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class LogExample {
}
以上将编译成
public class LogExample {
private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LogExample.class);
}
@Getter使用
private @Getter int foo;
以上将编译成
public int getFoo() {
return this.foo;
}
```

- 在idea中解决代码中识别错误的问题
- 在idea中添加插件lombok（file->setting->plugins）
- 如果直接安装失败，可以先下载安装包，然后导入，下载地址：https://github.com/mplushnikov/lombok-intellij-plugin/releases
- 如果报以上错误，参考https://blog.csdn.net/github_38410229/article/details/79475745，下载对应ide版本的包。

```java
@Data注解
展开
在实体类的编写过程中，常常需要应用大量的get、set方法，需要写大量的重复代码，即有的工具有自动生成功能，当时也会使实体类中产生大量冗余代码，使得代码变，springboot为我们提供了相应注解可以解决这类问题----@Data
 接下来简明扼要的介绍一下@Data注解的功能与使用方法
 ## 注解功能
  1、@Data可以为类提供读写功能，从而不用写get、set方法。
  2、他还会为类提供 equals()、hashCode()、toString() 方法。
  ## 使用方法--一下仅提供idea的使用方法
  1、下安装lombok插件
 ![settings/plugins在插件库中搜索安装即可。](https://img-blog.csdnimg.cn/20190501112839630.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Jlbnh0MDUwOA==,size_16,color_FFFFFF,t_70)
 2、重启idea
 3、在maven库中添加依赖
   <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <version>1.16.10</version>
   </dependency>
 4、在实体类上添加@Data注解即可生效


Lombok项目是一个Java库，它会自动插入编辑器和构建工具中，Lombok提供了一组有用的注释，用来消除Java类中的大量样板代码。
Lombok能以简单的注解形式来简化java代码，只要添加相应的注解，可以在JavaBean中省略构造器、getter/setter、equals、hashcode、toString等方法。
相关注解
Setter ：注解在类或字段，注解在类时为所有字段生成setter方法，注解在字段上时只为该字段生成setter方法。
Getter ：使用方法同上，区别在于生成的是getter方法。
ToString ：注解在类，添加toString方法。
EqualsAndHashCode： 注解在类，生成hashCode和equals方法。
NoArgsConstructor： 注解在类，生成无参的构造方法。
RequiredArgsConstructor： 注解在类，为类中需要特殊处理的字段生成构造方法，比如final和被NonNull注解的字段。
AllArgsConstructor： 注解在类，生成包含类中所有字段的构造方法。
Data： 注解在类，生成setter/getter、equals、canEqual、hashCode、toString方法，如为final属性，则不会为该属性生成setter方法。
Slf4j： 注解在类，生成log变量，严格意义来说是常量
安装插件
在IDEA中，菜单栏File->settings->Plugins，中间搜索框中搜索lombok插件，在最右方有该插件的详细介绍以及安装按钮，安装完成后点击确定，重启IDEA即可。

```