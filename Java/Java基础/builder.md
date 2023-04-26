### java中的.builder()方法实现详解

```java
Result<String> result3 = new Result.Builder<>().code(404).message("failure").build();
Result<String> result4 = Result.newBuilder().code(404).message("failure").build();
```
使用一个构造器;

一个空的构造器，然后使用setter方法进行设置。

使用这些方法时会有冗长的构造函数或者setter方法，有不同参数默认值的构造函数需要多次定义，因此我们可以使用builder来简化代码的简介性。

2.那么什么是Builder呢？

它其实是一种设计模式，叫做建造者模式，它的含义是将一个复杂的对象的构建与它的表示分离，同样的构建过程可以创建不同的表示：