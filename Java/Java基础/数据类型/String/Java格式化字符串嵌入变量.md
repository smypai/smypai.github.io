### 方法一：replace
```java
String str = "我是{name}，今年{age}岁了！";
String result = str.replace("{name}","小明").replace("{age}",18);
System.out.println(result);     
//->我是小明，今年18岁了！
```
### 方法二：format
```java
String str = "我是%s，今年%d岁了！";
String result = String.format(str,"小明",18);
System.out.println(result);
//->我是小明，今年18岁了！
```