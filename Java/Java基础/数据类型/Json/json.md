### fastjson

```java
JSON.toJSONString("json string");
JSONObject.parseObject("json string");

JSONObject name  = data.getJSONObject("name");
System.out.println(name.get("result"));
System.out.println(name.getString("age"));
System.out.println(name.getInteger("age"));
```


### Java 中 JSON 的使用

#### 类库选择

Java中并没有内置JSON的解析，因此使用JSON需要借助第三方类库。

下面是几个常用的 JSON 解析类库：
- Gson: 谷歌开发的 JSON 库，功能十分全面。
- FastJson: 阿里巴巴开发的 JSON 库，性能十分优秀。
- Jackson: 社区十分活跃且更新速度很快。

1. FastJson 环境配置 在 Maven 构建的项目中，在 pom.xml 文件中加入以下依赖即可。

```xml

<dependency>
<groupId>com.alibaba</groupId>
<artifactId>fastjson</artifactId>
<version>1.2.47</version>
</dependency>
```

2. 编码  从 Java 变量到 JSON 格式的编码过程如下：

```java
public void testJson() {
JSONObject object = new JSONObject();
    //string
    object.put("string","string");
    //int

    object.put("int",2);
    //boolean

    object.put("boolean",true);
    //array

    List<Integer> integers = Arrays.asList(1,2,3);
    object.put("list",integers);
    //null

    object.put("null",null);
    System.out.println(object);
}
```

在上例中，首先建立一个 JSON 对象，然后依次添加字符串、整数、布尔值以及数组，最后将其打印为字符串。
输出结果如下：
> {"boolean":true,"string":"string","list":[1,2,3],"int":2}

3. 解码 从 JSON 对象到 Java 变量的解码过程如下：

```java
public void testJson2() {
    JSONObject object = JSONObject
    .parseObject("{\"boolean\":true,\"string\":\"string\",\"list\":[1,2,3],\"int\":2}");
    //string
    
    String s = object.getString("string");
    System.out.println(s);
    //int
    
    int i = object.getIntValue("int");
    System.out.println(i);
    //boolean
    
    boolean b = object.getBooleanValue("boolean");
    System.out.println(b);
    //list
    
    List<Integer> integers = JSON.parseArray(object.getJSONArray("list").toJSONString(),Integer.class);
    integers.forEach(System.out::println);
    //null
    
    System.out.println(object.getString("null"));
}
```
在上例中，首先从 JSON 格式的字符串中构造一个 JSON 对象，之后依次读取字符串、整数、布尔值以及数组，最后分别打印，打印结果如下：
```
string
2
true
1
2
3
null
```
--------------------------------------------------------------------------------

4. JSON 对象与字符串的相互转化

方法作用JSON.parseObject()从字符串解析 JSON 对象JSON.parseArray()从字符串解析 JSON 数组JSON.toJSONString(obj/array)将 JSON 对象或 JSON 数组转化为字符串

//从字符串解析JSON对象
> JSONObject obj = JSON.parseObject("{\"runoob\":\"菜鸟教程\"}");

//从字符串解析JSON数组
> JSONArray arr = JSON.parseArray("[\"菜鸟教程\",\"RUNOOB\"]\n");

//将JSON对象转化为字符串
> String objStr = JSON.toJSONString(obj);

//将JSON数组转化为字符串
> String arrStr = JSON.toJSONString(arr);

 