### Java string和各种格式互转 string转int int转string

简单收集记录下

#### 其他类型转String
> String s = String.valueOf( value); // 其中 value 为任意一种数字类型。

#### 字符串型转换成各种数字类型：

```java
String s = "169";

byte b = Byte.parseByte( s );

short t = Short.parseShort( s );

int i = Integer.parseInt( s );

long l = Long.parseLong( s );

Float f = Float.parseFloat( s );

Double d = Double.parseDouble( s );
```