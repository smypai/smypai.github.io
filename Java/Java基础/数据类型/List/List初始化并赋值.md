```java
// 1.普通方式
List<String> list = new ArraysList<String>();
list.add("test1");
list.add("test2");

//2.匿名内部类
List<String> list = new ArraysList<String>(){
    add("test1");
    add("test2")
}

//3.Arrays.asList()作为初始化参数
List<String> list = new ArraysList<String>(
    Arrays.asList("test1","test2")
)

//4.直接使用Arrays.asList()
List<String> list = Arrays.asList("test","test1");

//5.Collections.nCopies返回一个不可变列表组成的n个拷贝的指定对象
ArrayList<String> list5 = new ArrayList<String>(Collections.nCopies(3, "大少"));

//6.guava的ImmutableList
List<String> list6 = ImmutableList.of("大少", "二少", "三少");
List<String> list7 = ImmutableList.<String>builder()
                .add("大少")
                .add("二少")
                .add("三少")
                .build();


```
原文链接：https://blog.csdn.net/lovesunren/article/details/105525270

