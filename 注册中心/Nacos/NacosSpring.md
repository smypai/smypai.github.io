1. 基础 JDK8+ Maven SpringMvc Servlet
2. POM添加依赖
```xml
<dependency>
    <groupId>com.alibaba.nacos</groupId>
    <artifactId>nacos-spring-context</artifactId>
    <version>${latest.version}</version>
</dependency>
```
3. 配置文件
```java
@Configuration
@EnableNacosConfig(globalProperties = @NacosProperties(serverAddr = "127.0.0.1:8848"))
@NacosPropertySource(dataId = "example", autoRefreshed = true)
public class NacosConfiguration {
}
```
4. 测试例子 通过 Nacos 的 @NacosValue 注解设置属性值。

```java
@Controller
@RequestMapping("config")
public class ConfigController {

    @NacosValue(value = "${useLocalCache:false}", autoRefreshed = true)
    private boolean useLocalCache;

    @RequestMapping(value = "/get", method = GET)
    @ResponseBody
    public boolean get() {
        return useLocalCache;
    }
}
```

5. 启动 Tomcat，调用 curl http://localhost:8080/config/get尝试获取配置信息。由于此时还未发布过配置，所以返回内容是 false。
6. 通过调用 Nacos Open API 向 Nacos Server 发布配置：dataId 为example，内容为useLocalCache=true
```shell
curl -X POST "http://127.0.0.1:8848/nacos/v1/cs/configs?dataId=example&group=DEFAULT_GROUP&content=useLocalCache=true"
```
7. 再次访问 http://localhost:8080/config/get，此时返回内容为true，说明程序中的useLocalCache值已经被动态更新了。


