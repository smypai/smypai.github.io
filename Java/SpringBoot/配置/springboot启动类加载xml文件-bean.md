```java
众所周知，springboot为了简化配置，极大多数的配置都简化到application.properties中了，不过对于某些入门难，属性配置复杂的框架，仍然需要xml配置，如何正确加载，请看下面代码：
/**
启动类,程序入口
*/
@EnableWebMvc
@SpringBootApplication
@ImportResource("classpath:spring-config-center.xml")      //导入xml配置
public class Application extends WebMvcConfigurerAdapter {
static {
System.setProperty("log4j.configurationFile", "config/log4j2.xml");
}
public static void main(String[] args) throws Exception {
SpringApplication.run(Application.class, args);
}
}


@ImportResource   加载xml文件，多个文件导入的时候使用（{"","",""}）形式

```