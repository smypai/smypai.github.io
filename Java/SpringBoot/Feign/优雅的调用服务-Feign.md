## Springboot-微服务-微服务组件之服务管理-优雅的调用服务-Feign
Feign可以把Rest的请求进行隐藏，伪装成类似SpringMVC的Controller一样。你不用再自己拼接url，拼接参数等等操作，一切都交给Feign去做。


### 引入feign相关依赖
```xml
<!--引入feign-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
    <version>2.0.1.RELEASE</version>
</dependency>
```

### 在Springboot启动类上，开启@EnableFeignClients 注解
```java


package com.caicai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
//@EnableDiscoveryClient//开启eureka服务端的调用
//@SpringBootApplication
//@EnableCircuitBreaker //添加Hystrix
//@EnableHystrix
@EnableFeignClients //添加feign启动注解
@SpringCloudApplication // 一个标准的eureka标准的客户端都需要配置以上三个注解，所以Spring 提供一个新的注解SpringCloudApplication包含的以上三个注解
public class ConsumerServiceApplication {
    //Spring boot 调用的接口都是rest风格的接口，这里我们使用，Spring 自带的resTemplate 去调用，它包含了：http,post 等等

//    @Bean
//    @LoadBalanced//开启负载均衡注解
//    public RestTemplate restTemplate(){
//
//        return new RestTemplate();
//    }
    public static void main(String[] args) {
        SpringApplication.run(ConsumerServiceApplication.class);
    }
}
```


#### 编写一个Fegin接口
为什么要编写这个接口？ 因为fegin 要靠这个接口，知道接口请求路径，服务地址，以及参数

```
package com.caicai.Consumer.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("user-service")
public interface UserClient {

    @GetMapping("user/{id}")
    String queryById(@PathVariable("id") Long id);
}
```

#### 注入Fegin接口相关类,调用接口。
```
    @Autowired
    UserClient userClient;
//    @HystrixCommand
    @GetMapping("{id}")
    public String queryById(@PathVariable("id") Long id)
    {


        return userClient.queryById(id);

    }
```
#### 问题
调用服务，失败！
`Request processing failed; nested exception is feign.RetryableException: Read timed out executing GET
java.net.SocketTimeoutException: Read timed out`

#### 解析
当访问微服务的响应时间超过5秒，就会出现 :

`Read timed out executing with root cause`

后面查阅很多资料，发现是ribbon的配置问问题

#### 详细说明

> ribbon.ConnectTimeout：请求连接的超时时间 默认1S

> ribbon.ReadTimeout：请求处理的超时时间 默认1S

> ribbon.OkToRetryOnAllOperations：对所有操作请求都进行重试

> ribbon.MaxAutoRetriesNextServer：切换实例的重试次数

> ribbon.MaxAutoRetries：对当前实例的重试次数

解决这个问题，我们只需在yml配置文件中添加ribbon的ConnectTimeout、ReadTimeout ，这两个时间长一点，即可解决sockt 超时问题**

```properties
ribbon:
  ReadTimeout: 60000
  ConnectTimeout: 60000
```




#### 补充
Fegin自带的熔断机制,需要配置的

Fegin熔断默认是关闭，需要我们手工去开启，并且我们需要写一个实现类

在application.yml 中添加 #开启fegin 的熔断
```properties
fegin:
  hystrix:
    enable : true
```


#### Fegin 接口
在fegin 的接口类上，配置fallback方法  fallback 方法必须要实现 fegin 接口
```java


package com.caicai.Consumer.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient("user-service")
@FeignClient(value = "user-service",fallback = UserClientfallback.class)//这里的实现类，就是熔断方法
public interface UserClient {

    @GetMapping("user/{id}")
    String queryById(@PathVariable("id") Long id);
}

```

#### 熔断方法/也是实现类

```java
package com.caicai.Consumer.client;

import com.caicai.Consumer.po.User;
import org.springframework.stereotype.Component;

@Component //注入到Sprig 容器中
public class UserClientfallback implements UserClient {
    @Override
    public String queryById(Long id) {
        return "服务器繁忙，请稍后再试！！";
    }
}
```


#### Feign .请求压缩(了解)
Spring Cloud Feign 支持对请求和响应进行GZIP压缩，以减少通信过程中的性能损耗。通过下面的参数即可开启请求与响应的压缩功能：
```yaml
feign:
  compression:
    request:
      enabled: true # 开启请求压缩
    response:
      enabled: true # 开启响应压缩
```

同时，我们也可以对请求的数据类型，以及触发压缩的大小下限进行设置：

```yaml
feign:
  compression:
    request:
      enabled: true # 开启请求压缩
      mime-types: text/html,application/xml,application/json # 设置压缩的数据类型
      min-request-size: 2048 # 设置触发压缩的大小下限
```
注：上面的数据类型、压缩大小下限均为默认值。

#### Feign .日志级别(了解)
前面讲过，通过logging.level.xx=debug来设置日志级别。然而这个对Fegin客户端而言不会产生效果。因为@FeignClient注解修改的客户端在被代理时，都会创建一个新的Fegin.Logger实例。我们需要额外指定这个日志的级别才可以。

- 1）设置com.caicai包下的日志级别都为debug
```yaml
logging:
  level:
    com.caicai: debug
```
- 2）编写配置类，定义日志级别

```java

@Configuration
public class FeignConfig {
    @Bean
    Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }
}
```


- 这里指定的Level级别是FULL，Feign支持4种级别：
    - NONE：不记录任何日志信息，这是默认值。
    - BASIC：仅记录请求的方法，URL以及响应状态码和执行时间
    - HEADERS：在BASIC的基础上，额外记录了请求和响应的头信息
    - FULL：记录所有请求和响应的明细，包括头信息、请求体、元数据。