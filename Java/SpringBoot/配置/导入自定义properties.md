```java
Spring boot导入自定义properties配置文件方式及原理
1.创建properties文件
self.define.username=root
self.define.password=root
self.define.nickname=小王
2.自定义properties文件可以通过注解@PropertySource(“classpath:.properties”)引入
//通过注解自定义引入properties 文件
@PropertySource("classpath:my.properties")
@RestController
@RequestMapping("myproperties")
public class Myproperties {
    //通过@Value注解，指定赋值给变量
    @Value("${self.define.username}")
    private String username;
    @Value("${self.define.password}")
    private String password;
    @Value("${self.define.nickname}")
    private String nickname;

    @RequestMapping("/getUserName")
    public String getUserName(){
        System.out.println("姓名"+username);
        return username;
    }

}
3.使用注解@ConfigurationProperties(prefix=“xxx”) 表示属性文件前缀，就可以，这个javabean就可以获取到相应的属性值了。示例如下
//javabean对象
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Data
@PropertySource("classpath:my.properties")
@ConfigurationProperties(prefix="self.define")
public class DefineUser {
    private String username;
    private String password;
    private String nickname;

//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getNickname() {
//        return nickname;
//    }
//
//    public void setNickname(String nickname) {
//        this.nickname = nickname;
//    }
}
4.不通过注解的形式，引入自定义的properties文件，
4.1须继承SpringApplicationRunListener 接口，重写environmentPrepared方法
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;

import java.io.IOException;
import java.util.Properties;

public class MySpringApplicationRunListener implements SpringApplicationRunListener {
    private final SpringApplication application;
    private final String[] args;
     //有参构造函数
    public MySpringApplicationRunListener(SpringApplication application, String[] args) {
        this.application = application;
        this.args = args;
    }
    @Override
    public void starting() {
        System.out.println("执行自定义的properties文件");
    }
    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        Properties properties = new Properties();
        try {
            //读取properties文件
            properties.load(this.getClass().getClassLoader()
                    .getResourceAsStream("my.properties"));
            //读取my 文件
            PropertiesPropertySource propertySource = new PropertiesPropertySource("my", properties);
            //将资源文件加入到springboot项目
            MutablePropertySources propertySources = environment.getPropertySources();
            //添加到项目中
            propertySources.addLast(propertySource);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
    }
    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
    }
    @Override
    public void started(ConfigurableApplicationContext context) {
    }
    @Override
    public void running(ConfigurableApplicationContext context) {
    }
    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {

    }
}
4.2 须在项目中建spring.factories文件，将实现接口SpringApplicationRunListener 配置如下

Run Listeners
org.springframework.boot.SpringApplicationRunListener=
com.example.springboot_vue.controller.MySpringApplicationRunListener

```