```java

public class Stream {
    Stream<Long> keyStream = adminMap.keySet().stream();
    Stream<Boolean> valueStream = adminMap.values().stream();
    Stream<Map.Entry<Long, Boolean>> entryStream = adminMap.entrySet().stream();

    //单一条件 筛选
    admins.stream().filter(s->s.getAdminId().equals(1)).collect(Collectors.toList());
    //多条件 筛选
    admins.stream().filter(s->s.getAdminId().equals(1) && s.getAdminStatus().equals(1)).collect(Collectors.toList());

    // java一行代码打印心形
    IntStream.range(-15, 15).map(y -> -y)
            .forEach(y -> IntStream.range(-30, 30)
            .forEach(x -> System.out.print(
            Math.pow(Math.pow((x * 0.05), 2) +
            Math.pow((y * 0.1), 2) - 1, 3) -
            Math.pow(x * 0.05, 2) *
            Math.pow(y * 0.1, 3) <= 0 ? "love".charAt(Math.abs((y - x) % 4)) : " " + (x == 29 ? "\n" : ""))));
    
    //1、stream计算list集合中某字段的总和
    BigDecimal amount = m.parallelStream().map(ActivityRecord::getAmount).reduce(BigDecimal.ZERO,BigDecimal::add);
    Long num = list.stream().collect(Collectors.summingLong(Person::getNum));
    //实现方式一
    DoubleSummaryStatistics statistics = list.stream().collect(Collectors.summarizingDouble(Employee::getSalary));
    //实现方式二
    DoubleSummaryStatistics statistics1 = list.stream().mapToDouble(Employee::getSalary) .summaryStatistics();
    
    //### java stream 去掉重复
    - https://blog.csdn.net/haiyoung/article/details/80934467
    - https://blog.csdn.net/lin252552/article/details/81128296

    public class Stream {
        Stream<Long> keyStream = adminMap.keySet().stream();
        Stream<Boolean> valueStream = adminMap.values().stream();
        Stream<Map.Entry<Long, Boolean>> entryStream = adminMap.entrySet().stream();
        //单一条件 筛选
    admins.stream().filter(s->s.getAdminId().equals(1)).collect(Collectors.toList());
        //多条件 筛选
    admins.stream().filter(s->s.getAdminId().equals(1) && s.getAdminStatus().equals(1)).collect(Collectors.toList());

    
    Function.identity()是什么？
    // 将Stream转换成容器或MapStream<String> stream = Stream.of("I", "love", "you", "too");Map<String, Integer> map = stream.collect(Collectors.toMap(Function.identity(), String::length));//[love=4, too=3, I=1, you=3]
    Function是一个接口，那么Function.identity()是什么意思呢？解释如下：
    Java 8允许在接口中加入具体方法。接口中的具体方法有两种，default方法和static方法，identity()就是Function接口的一个静态方法。
            Function.identity()返回一个输出跟输入一样的Lambda表达式对象，等价于形如t -> t形式的Lambda表达式。
    identity() 方法JDK源码如下：
    static  Function identity() {    return t -> t;}
    Function.identity()的应用
    下面的代码中，Task::getTitle需要一个task并产生一个仅有一个标题的key。task -> task是一个用来返回自己的lambda表达式，上例中返回一个task。
    private static Map<String, Task> taskMap(List<Task> tasks) {  return tasks.stream().collect(toMap(Task::getTitle, task -> task));}
    可以使用Function接口中的默认方法identity来让上面的代码代码变得更简洁明了、传递开发者意图时更加直接，下面是采用identity函数的代码。
            import static java.util.function.Function.identity; private static Map<String, Task> taskMap(List<Task> tasks) {  return tasks.stream().collect(toMap(Task::getTitle, identity()));








}
```