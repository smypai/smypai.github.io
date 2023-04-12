# springboot整合redis
## 1、引入依赖包
<!-- 引入redis依赖 -->
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```
## 2、添加配置文件
```properties
spring:
  redis:
    database: 0
    host: 127.0.0.1
    port: 6379
    password: 123456
```
## 3、注入RedisTemplate，通过redisTemplate对redis进行操作
```java
@Autowired
private RedisTemplate redisTemplate;
redisTemplate.opsForValue().set(key,value);
redisTemplate.opsForValue().get(key);
redisTemplate.delete(key);
```
## 4、使用RedisTemplate操作时，默认会采用jdkSerializable序列化机制，使得插入的值在redis客户端看来会有乱码，若想解决这一问题，需要手动指定序列化方式。
```java
@Bean
public RedisTemplate<Object,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
    RedisTemplate<Object,Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory);
    RedisSerializer serializer = new StringRedisSerializer();
    Jackson2JsonRedisSerializer jsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
    redisTemplate.setKeySerializer(serializer);
    redisTemplate.setValueSerializer(jsonRedisSerializer);
    return redisTemplate;
}
```
