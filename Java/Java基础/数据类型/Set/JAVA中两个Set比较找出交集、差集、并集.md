JAVA中两个Set比较找出交集、差集、并集
//两个Set比较找出交集、差集、并集
```java

public static void  setCompare() {
    Set<Integer> result = new HashSet<Integer>();
    Set<Integer> set1 = new HashSet<Integer>() {{
        add(1);
        add(3);
        add(4);
    }};
    System.out.println("set1 = " + set1.toString());

    Set<Integer> set2 = new HashSet<Integer>() {{
        add(1);
        add(2);
        add(3);
    }};
    System.out.println("set2 = " + set2.toString());
    result.clear();
    result.addAll(set1);
    result.retainAll(set2);
    System.out.println("交集：" + result);

    result.clear();
    result.addAll(set1);
    result.removeAll(set2);
    System.out.println("差集：" + result);

    result.clear();
    result.addAll(set1);
    result.addAll(set2);
    System.out.println("并集：" + result);
}