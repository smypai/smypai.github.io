## @ConfigurationProperties注解原理与实战
一、@ConfigurationProperties 基本使用  
 在 SpringBoot 中，当想需要获取到配置文件数据时，除了可以用 Spring 自带的 @Value 注解外，  
 SpringBoot 还提供了一种更加方便的方式：@ConfigurationProperties。只要在 Bean 上添加上了这个注解，指定好配置文件的前缀，那么对应的配置文件数据就会自动填充到 Bean 中。

- 比如在application.properties文件中有如下配置文件

```yaml
config.username=jay.zhou
config.password=3333
```

- 那么按照如下注解配置，SpringBoot项目中使用@ConfigurationProperties的Bean，它的username与password就会被自动注入值了。就像下面展示的那样

```java
 
@Component
@ConfigurationProperties(prefix = "config")
public class TestBean{
 
    private String username;
    
    private String password;
}


@Data
@Component
@ConfigurationProperties(prefix = "openapi")
public class SuiteConfMap {
    private List<SuiteConf> configs;
    public SuiteDevConf get(String id) {
        ……
    }
    @Data
    public static class SuiteConf {
        private String id;
        private String Secret;
        private String token;
        private String encodingAesKey;
    }
}

```
