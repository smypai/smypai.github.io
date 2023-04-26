```java
1、java中list集合中contains()的用法为：
public boolean list.contains(Object o)
意思为：当前列表若包含某元素，返回结果为true, 若不包含该元素，返回结果为false。
        
2、contains()方法实现的具体细节为：
当list调用contains（）方法并传递一个元素时，会执行遍历，逐个对比item是否等于该元素，当遍历结束后，如果还没有一个元素等于该元素的值，则返回false, 否则返回true
        
3、java中list集合中contains()用法的代码示例为：
import java.util.ArrayList;
public class ArrayListDemo {
public static void main(String[] args) {
    // 创建1个空列表
    ArrayList arrlist = new ArrayList();
    // 添加测试数据
    arrlist.add(20);
    arrlist.add(25);
    arrlist.add(10);
    arrlist.add(15);
    // 列表是否包含元素 10
    boolean res = arrlist.contains(10);
    if (res == true) {
        System.out.println(“包含”);
    } else {
        System.out.println(“不包含”);
}}}
```