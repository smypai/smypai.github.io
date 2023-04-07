### Stream流转换集合类型

```java
//List<String> 转换 list<long>
public void delete1(){
    List<String> ids=new ArrayList<>();
    ids.add("1");
    ids.add("2");
    ids.add("3");
    ids.add("4");
    List<Long> longs=ids.stream().map(s->Long.parseLong(s.trim())).collect(Collectors.toList());
    System.out.println(longs);
}

// 转换代码样式（这个是String转Long）
ids.stream().map(s->Long.parseLong(s.trim())).collect(Collectors.toList())

```