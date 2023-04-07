### 字符串拼接
```直接用+号:
String a = "I"; 
String b = "love"; 
String c = "you";
String d = a+b+c;
# "I"+"love"+"you"得到的也是I love you
```
### 字符串比较
#### ==和equals都能比较字符串,返回的都是boolean类型
```
String a ="I";
String b = "love";
# a==b;返回false   a.equals(b)返回false  
# ==比较的是2个字符串是否指向同一地址,equals()比较的是实际字符串,比如大小写,长度等
```
#### 是否包含
1. contains();返回值为boolean类型
```
String a = "szrfrrgdhjd";
a.contains("g") 里面包含,所以返回true
```
2. indexOf();返回的是一个int类型,通常和substring()一起用
```
String a = "qwertyu";
a.indexOf("e");他返回的是int类型的2,就是说e在字符串a中的第2个位置
如果有多个e的话,始终返回的是第一个e的位置
```
#### 是否为空
1.  == null;
2.  isEmpty();返回的都是boolean
```String a = "sssss";
a.idEmpty();  a不是空,返回的是false
```
### 分割字符串
* split()分割字符串返回的是一个String数组
```
String a = "abcdeAfghijk";
String [] b = a.split("A");//以A作为分割点,将字符串a分割为2个字符串数组 分别为
b[0] = " abce ";
b[1] = "efghijk"
# 如果字符串包含好几个A呢
String a = "abcdeAfghAijk";
String [] b = a.split("A");  //以每个A作为分割点,得到的是
b[0] = "abde";
b[1] = "fgh";
b[2] = "ijk";
```

### 截取字符串中间段
* substring(int start,int end) 截取从start到end中间的字符串,也可以只传入一个int start 截取从start到最后
下面来一个实际操作:
```
String s = "{name=段炼, age=25, sex=男, id=12, hobby=吃饭。睡觉}";
    // 拿到id字段是处于字符串第几个位置
if (s.contains("id")) {
    int start = s.indexOf("id");
    //* "id"字段后面是"hobby"字段,拿到hobby字段的位置
    int end = s.indexOf("hobby");
    //* start + 3:从i开始+3个正好是id也就是12开始的地方,
    //* end-2:hobby-2正好是12结束的位置;一共减去了一个h和一个逗号
    String a = s.substring(start + 3, end-2);
    //得到id后转换成int类型
    int id = Integer.parseInt(a);
    得到的id就是12
}
```
### 字符串替换
* replace(oldChar, newChar)方法	参数1:要被替换的字符,参数2:替换进去的字符
该方法的作用是替换字符串中所有指定的字符，然后生成一个新的字符串。经过该方法调用以后，原来的字符串不发生改变。例如：
```
String s = "abcde8fghijk8lmn";
String a = s.replace('8', 'Q');
# a的值为"abcdeQfghijkQlmn"
```
* replaceAll(String regularExpression, String replacement),	替换所有包含的字符串
参数1:要替换的字符串,2,替换进去的字符串
```
String s = "QQQQabcWWWabcGGGGabc";
String a = s.replaceAll("abc", "PPP");
a的值为"QQQQPPPWWWPPPGGGGPPP"
```
* 如果只替换第一个abc用replaceFirst()
```
String s = "QQQQabcWWWabcGGGGabc";
String a = s.replaceFirst("abc", "PPP");
a的值为"QQQQPPPWWWabcGGGGabc"
```

### 去空格
* ltrim()、rtrim() 和 trim() 函数的区别 　　
* 返回不带前导空格 (ltrim)、后续空格 (rtrim) 或前导与后续空格 (trim) 的字符串

### 字符串长度
* string.length()