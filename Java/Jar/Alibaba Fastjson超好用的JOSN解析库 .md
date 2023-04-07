### Alibaba Fastjson——超好用的JOSN解析库
```xml
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.47</version>
</dependency>
```
* 1.Map转JSON
```java
Map<String, Object> map = new HashMap<String, Object>();
map.put("a", "a");
map.put("b", "123");
JSONObject json = new JSONObject(map);
```


* 2.map转string
```java
Map<String, Object> map = new HashMap<>();
map.put("a", "b");
String s = JSONObject.toJSONString(map);
```

* 3.JSON转String
```java
JSONObject json = new JSONObject();
json.put("c", "v");
json.put("z", "123n);
json.toJSONString();
```
* 4.JSON转Map
```java
JSONObject json = new JSONObject();
json.put("ccc", "321");
json.put("bbb", "123");
Map<String, Object> map = (Map<String, Object>)json;
```
* 5.String转JSON
```java
String str = "{\"username\":\"dsad\",\"qwewqe\":\"123\"}";
JSONObject json = JSONObject.parseObject(str);
```

### 2  google
```xml
<!-- https://mvnrepository.com/artifact/com.google.code.gson/gson -->
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.3.1</version>
</dependency>
```

//Map转换成JSON
```java
Map<String,String> map = new HashMap<String,String>();
map.put("a","aaa");
map.put("b","bbb");
map.put("c","ccc");
String json=JSON.toJSONString(map);
System.out.println(json);//输出{"a":"aaa","b":"bbb","c":"ccc"}
```
//JSON转换成Map
```java
Map map1 = JSON.parseObject(json);
System.out.println(map1.get("a"));
for (Object mapData : map.entrySet()) {
Map.Entry<String,String> entry = (Map.Entry<String,String>)mapData;
System.out.println(entry.getKey()+"--->"+entry.getValue());
}
/*输出
b--->bbb
c--->ccc
a--->aaa
*/
```
map中含有对象Map -> JSON
```java
//Map -> JSON
Map<String,Bar> map = new HashMap<String, Bar>();
map.put("a",new Bar());
map.put("b",new Bar());
map.put("c",new Bar());
String json = JSON.toJSONString(map,true);
System.out.println(json);
/*
输出{
"a":{
"barAge":383687382,
"barDate":1494945882018,
"barName":"name_1689176802"
},
"b":{
"barAge":-100528778,
"barDate":1494945882018,
"barName":"name_-878176366"
},
"c":{
"barAge":-344075192,
"barDate":1494945882018,
"barName":"name_-1710740534"
}
}
*/
```
//JSON -> Map
```java
Map<String,Bar> map1 = (Map<String,Bar>)JSON.parse(json);
for (String key : map1.keySet()) {
System.out.println(key+":"+map1.get(key));
}
/*输出
b:{"barAge":-100528778,"barDate":1494945882018,"barName":"name_-878176366"}
c:{"barAge":-344075192,"barDate":1494945882018,"barName":"name_-1710740534"}
a:{"barAge":383687382,"barDate":1494945882018,"barName":"name_1689176802"}
*/
```

-------------------===-------------------------附--MAP的ASCII排序-----------------------===------------------------
```java
StringBuilder sb = new StringBuilder();
List<String> keys = new ArrayList<String>(map.keySet());
Collections.sort(keys);//排序。
for(String k : keys){
String v = params.get(k);
if(StringUtils.isNotEmpty(v)){
sb.append(v);
}
}
//return MD5.toMD5(sb+key, "UTF-8");这个就不用看了~~~
```