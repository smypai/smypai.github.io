## 1 添加redis的起步依赖：
<!-- 配置使用redis启动器 -->
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```
## 2 在application.properties中配置redis的连接信息：
```properties
#Redis
spring.redis.host=127.0.0.1
spring.redis.port=6379
spring.redis.password=root
#最大连接数
spring.redis.jedis.pool.max-active=30
#最大空闲数
spring.redis.jedis.pool.max-idle=20
#最小空闲连接数
spring.redis.jedis.pool.min-idle=10
#连接时的最大等待毫秒数
spring.redis.jedis.pool.max-wait=3000
```

## 3 注入RedisTemplate测试redis操作
```java
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class RedisTest {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void test() throws JsonProcessingException {
        // 从redis缓存中获得指定的数据
        String userListData = redisTemplate.boundValueOps("user.findAll").get();
        //如果redis中没有数据的话
        if (null == userListData) {
            //查询数据库获得数据
            List<User> users = userDao.findAll();
            //转换成json格式字符串
            ObjectMapper om = new ObjectMapper();
            userListData = om.writeValueAsString(users);
            //将数据存储到redis中，下次在查询直接从redis中获得数据，不用在查询数据库
            redisTemplate.boundValueOps("user.findAll").set(userListData);
            System.out.println("===============从数据库获得数据===============");
        } else {
            System.out.println("===============从redis缓存中获得数据===============");
        }
        System.out.println(userListData);
    }
}
```
### Operations：
 - redisTemplate有两个方法经常用到,
 - 一个是opsForXXX一个是boundXXXOps,XXX是value的类型,前者获取到一个Opercation,但是没有指定操作的key,可以在一个连接(事务)内操作多个key以及对应的value;
 - 后者会获取到一个指定了key的operation,在一个连接内只操作这个key对应的value
```java
　　private RedisTemplate redisTemplate;
　　ValueOperations valueOperations = redisTemplate.opsForValue();
　　　　ValueOperations ：简单字符串类型数据操作
　　　　SetOperations ：set类型数据操作
　　　　ZSetOperations ：zset类型数据操作
　　　　HashOperations ：map类型的数据操作
　　　　ListOperations ：list类型的数据操作
　　　　opsForGeo：geo类型的数据操作，应用于地理位置计算
　　　　opsForHyperLogLog：应用于独立信息统计
　　　　opsForCluster：应用于集群

　　BoundValueOperations boundValueOps = redisTemplate.boundValueOps("key");
　　　　boundValueOps
　　　　boundSetOps
　　　　boundZSetOps
　　　　boundHashOps
　　　　boundListOps
　　　　boundGeoOps
```



