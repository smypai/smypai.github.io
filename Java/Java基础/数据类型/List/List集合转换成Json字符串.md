
* 进行转换我们使用alibaba的json jar：com.alibaba.fastjson.jar

### 1.导入依赖或者直接导入jar
```xml
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.3</version>
</dependency>

```

### 2.代码实现讲解
* （1）list 转 json
```
List<User> userList = new ArrayList<>();
String json = JSON.toJSONString(userList);
```
* （2）json 转 list
```
String json="";
List<User> list = JSON.parseArray(json,User.class);
```

