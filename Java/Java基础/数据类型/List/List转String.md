### 1.List转String
``` java
String test = String.join(此处是连接List中元素的符号(如","),yourList);
String s = StringUtils.join(list,",");

```

### 用StringUtils 来玩一下：

```java
import com.sun.deploy.util.StringUtils;
//或者
import org.apache.commons.lang3.StringUtils;
 
    public  static  void bb()
    {
        List<String> list = new ArrayList<String>();
        list.add("a1");
        list.add("a2");
        String test = StringUtils.join(list,",");
        System.out.println(test);
    }
```
### 2.List转String[]
```java
String []test = (String[]) yourList.toArray(new String[yourList.size()]);
```

### 初始化 List 

```java
List<String> list = Arrays.asList("1", "2", "3");
List<String> numbers = new ArrayList<>(Arrays.asList("1", "2", "3"));

List<String> list = Collections.nCopies(3, "1");

List<String> dogs = new ArrayList<>(Collections.nCopies(3, "dog"));

List<String> list = Lists.newArrayList("1", "2", "3");

List<String> list = new ArrayList<String>() {{
     add("1");
     add("2");
     add("3");
}};
List<String> list = ImmutableList.of("1", "2", "3");

List<String> list = Stream.of("1", "2", "3").collect(Collectors.toList());

List<String> list = List.of{"1", "2", "3"};

```
