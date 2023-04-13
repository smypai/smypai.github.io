### 1. 引入feign相关依赖
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
    <version>2.0.1.RELEASE</version>
</dependency>
```

### 2. 在Springboot启动类上，开启@EnableFeignClients 注解
```java
@EnableFeignClients //添加feign启动注解
@SpringCloudApplication // 一个标准的eureka标准的客户端都需要配置以上三个注解，所以Spring 提供一个新的注解SpringCloudApplication包含的以上三个注解
public class ConsumerServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerServiceApplication.class);
    }
}
```

### 3. 编写一个Fegin接口
```java
@FeignClient(
    url = "http://127.0.0.1:8080",
    name = "服务名称",
    fallback = UserHihystric.class) //熔断回执类
public interface UserClient {

    @GetMapping(value = "user/{id}")
    @ResoposeBody
    String queryById(@PathVariable("id") Long id);
    
    @RequestMapping(value = "user/", method = ReuestMethid.GET)
    @ResoposeBody
    String queryById(@RequestParam("id") Long id);
    
    @PostMapping(value = "/user")
    @ResoposeBody
    String queryById(@RequestBody Long id);
    
    @RequestMapping(value = "/user", method = ReuestMethid.POST)
    @ResoposeBody
    String queryById(@RequestBody UserDto user); 
}
```

### 4. 实现接口，（服务异常时执行，熔断）
```java
@Component
public class UserHihystric implements UserClient {
    @Override
    public String getSuccessInfo(){
        return "getSuccessInfo serve is bad.";
    }
    ………………………… 
}
```

### 5. 使用请求接口
```java
@RestController
@RequestMapping("/user")
public class UserController {
 
    @Autowired
    private UserClient userClient;
 
    @GetMapping("/getSuccessInfo")
    public Object getSuccessInfo(){
        return userClient.getSuccessInfo();
    }
    ………………………… 
}
```



