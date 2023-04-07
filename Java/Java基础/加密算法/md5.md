### 什么是MD5
MD5加密全程是Message-Digest Algoorithm 5（信息-摘要算法），它对信息进行摘要采集，再通过一定的位运算，最终获取加密后的MD5字符串。
　　例如我们要加密一篇文章，那么我们会随机从每段话或者每行中获取一个字，把这些字统计出来后，再通过一定的运算获得一个固定长度的MD5加密后信息。因此，其很难被逆向破解。

### MD5有哪些特点
针对不同长度待加密的数据、字符串等等，其都可以返回一个固定长度的MD5加密字符串。（通常32位的16进制字符串）；
其加密过程几乎不可逆，除非维护一个庞大的Key-Value数据库来进行碰撞破解，否则几乎无法解开。
运算简便，且可实现方式多样，通过一定的处理方式也可以避免碰撞算法的破解。
对于一个固定的字符串。数字等等，MD5加密后的字符串是固定的，也就是说不管MD5加密多少次，都是同样的结果。

### 1. 使用JDK自带的API实现
加单实现如下
```java
@Test
void test1() {
    String pwd = "123456";
    try {
        MessageDigest md = MessageDigest.getInstance("MD5");// 生成一个MD5加密计算摘要
        md.update(pwd.getBytes());// 计算md5函数
        /**
         * digest()最后确定返回md5 hash值，返回值为8位字符串。
         * 因为md5 hash值是16位的hex值，实际上就是8位的字符
         * BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
         * 一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方）
         */
        String hashedPwd = new BigInteger(1, md.digest()).toString(16);// 16是表示转换为16进制数
        System.out.println(hashedPwd); 
    } catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
    }
}
//结果:
//e10adc3949ba59abbe56e057f20f883e
```
### 2. 使用Spring的DigestUtils工具类
```java
@Test
void testMD5() {
    String pwd = "123456";
    // 基于spring框架中的DigestUtils工具类进行密码加密
    String hashedPwd1 = DigestUtils.md5DigestAsHex((pwd).getBytes());
    System.out.println(hashedPwd1); 
}
//结果:
//e10adc3949ba59abbe56e057f20f883e
```
### 改进- 加盐
一般加密算法固定，很容易破解，安全系数低，有很多网站可以直接破解密文。为了提高安全性，可以采取加盐的方式。生成一组随机串，保存在数据库中，然后混杂在原来的密码中，再通过加密算法加密，存进数据库中
```java
@Test
void testMD5() {
    String pwd = "123456";
    String salt = UUID.randomUUID().toString();
    // 基于spring框架中的DigestUtils工具类进行密码加密
    String hashedPwd1 = DigestUtils.md5DigestAsHex((pwd + salt).getBytes());
    System.out.println(hashedPwd1);
}
//结果:
//ce504625e463008803c1b875a0bd87a3
```
### 改进-加次数
多加密几次也可增加破解的难度, 一般可用于交易码等
``` java
@Test
void testMD5() {
    String pwd = "123456";
    String salt = UUID.randomUUID().toString();
    // 基于spring框架中的DigestUtils工具类进行密码加密
    String hashedPwd1 = DigestUtils.md5DigestAsHex((pwd + salt).getBytes());
    hashedPwd1 = DigestUtils.md5DigestAsHex((hashedPwd1 + salt).getBytes()); // +1次
    hashedPwd1 = DigestUtils.md5DigestAsHex((hashedPwd1 + salt).getBytes()); // +2次
    // ... 可使用循环等
    System.out.println(hashedPwd1);
}
//结果:
//22cca33f84e7c72132dbff6fcfc60934
```
### 3. 使用Shiro的simpleHash进行加密
当然, 我们也可以加盐加次数等
```java
@Test
void testShiroMD5() {
    String pwd = "123456"; // 密码
    String salt = UUID.randomUUID().toString(); // 盐
    /**
         * 参数1: 加密方式
         * 参数2: 要加密的字符串
         * 参数3: 盐
         * 参数4: 加密次数
         */
    SimpleHash sh = new SimpleHash("MD5", pwd, salt, 5); // 定义simpleHash对象
    String hashedPwd = sh.toHex(); // 生成16进制密文
    System.out.println(hashedPwd); // 输出
}
//结果
//b37e9129e9a6c1cecc8d34c60315fd8d