### SpringMVC给请求路径加上统一前缀

```
为了方便后台权限管理,项目准备采取给请求加上指定访问路径的方式来完成,
一开始想到的是通过硬编码的方式手动在每个路径上加上前缀, 后面发现这种方式太不智能了,万一要修改那还不得改死,
后面又想到通过定义静态常量通过引用这个变量来完成, 但总感觉这种方式也不是很好, 就是不够高大上, 接着又想到了注解
Spring既然支持EL表达式, 那能不能通过EL表达式的方式去读取配置文件里面的属性来达到引用的目的呢?
抱着这种想法去试了一下, 哈哈还真的可以呢,也很简单
代码台下
1.在mvc的配置文件里加入要引入的文件
<!--引入配置文件 属性上可通过 @Value跟EL表达式获取对应的值-->    <context:property-placeholder ignore-unresolvable="true" location="classpath:spring.conf/applicationContext.properties" />

1.1 配置文件内容为
#项目文件存放根路径FILE_ROOT_PATH=/apples/ELECTRONICDJ  #MacOS系统下音频转换ffmpeg路径JAVE_FFMPEG_PATH=/Applications/ffmpeg-macos64/bin/ffmpeg  #后台请求统一前缀admin.url.perfix = /admin
2.在Controller通过EL表达式引入该变量并测式访问测式

4.成功进入后台断点

5.需要注意的是, 如果你没获取到值, 很有可能是你的配置文件引入错地方了, 是引入到 spring-mvc.xml的配置文件中
```