### 1.List转String
``` java
String test = String.join(此处是连接List中元素的符号(如","),yourList);
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