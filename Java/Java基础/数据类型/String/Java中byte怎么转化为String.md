
## string转化为byte[]数组
```java
String str = "abcd";
byte[] bs = str.getBytes();
```

## byte[]数组转化为string字符串
```java
/*String str1 = "abcd";
byte[] bs1 = str1.getBytes();*/
byte[] bs1 = {97,98,100};
String s = new String(bs1);
```
## 设置格式
```java
byte[] srtbyte = {97,98,98};
String res = new String(srtbyte,"UTF-8");	

