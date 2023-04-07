```java
// 实例
class list {

    // 创建集合
    List<String> list = Lists.newArrayList("a", "b", "c", "d");

    //1、正常遍历
    list.forEach(item->System.out.println(item));
    //2、条件遍历
    list.forEach(item->
    {
        if ("b".equals(item)) {
            System.out.println(item);
        }

}
// 底层实现
 public interface Iterable<T> {

        Iterator<T> iterator();

        default void forEach(Consumer<? super T> action) {
            Objects.requireNonNull(action);
            for (T t : this) {
                action.accept(t);
            }
        }

        default Spliterator<T> spliterator() {
            return Spliterators.spliteratorUnknownSize(iterator(), 0);
}
```