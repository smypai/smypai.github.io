```java
String[] arrayA = new String[] { "1", "2", "3", "4"};
String[] arrayB = new String[] { "3", "4", "5", "6" };
List<String> listA = Arrays.asList(arrayA);
List<String> listB = Arrays.asList(arrayB);   

    //1、并集 union
    System.out.println(CollectionUtils.union(listA, listB));
    //输出: [1, 2, 3, 4, 5, 6]

    //2、交集 intersection
    System.out.println(CollectionUtils.intersection(listA, listB));
    //输出:[3, 4]

    //3、交集的补集（析取）disjunction
    System.out.println(CollectionUtils.disjunction(listA, listB));
    //输出:[1, 2, 5, 6]

    //4、差集（扣除）
    System.out.println(CollectionUtils.subtract(listA, listB));
    //输出:[1, 2]
    
     
 //1、交集
    List<String>  jiaoList = new ArrayList<>(listA);
    jiaoList.retainAll(listB);
    System.out.println(jiaoList);
    //输出:[3, 4]

    //2、差集
    List<String>  chaList = new ArrayList<>(listA);
    chaList.removeAll(listB);
    System.out.println(chaList);
    //输出:[1, 2]

    //3、并集 (先做差集再做添加所有）
    List<String>  bingList = new ArrayList<>(listA);
    bingList.removeAll(listB); // bingList为 [1, 2]
    bingList.addAll(listB);  //添加[3,4,5,6]
    System.out.println(bingList);
    //输出:[1, 2, 3, 4, 5, 6]
————————————————
版权声明：本文为CSDN博主「he_lei」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/he_lei/article/details/111469256                                                                                                                                                                                                                                                   
```