### 1.List转String数组

* 方法一:
```
//先准备一个List
List<String> testList=new ArrayList<>();
testList.add("a");
testList.add("b");
testList.add("c");
//List转String
String[] strs1=testList.toArray(new String[testList.size()]);
for(String s:strs1){
System.out.println(s);
}
```
* 方法二：
```
//先准备一个List
List<String> testList=new ArrayList<>();
testList.add("a");
testList.add("b");
testList.add("c");
//List转String
String[] strs2 = new String[testList.size()];
for(int i=0;i<testList.size();i++){
strs2[i]=testList.get(i);
}
for(String s:strs2){
System.out.println(s);
}
```

### 二：String数据转List

* 方法一：
```
//准备一个String数组
String[] strs = {"aa","bb","cc"};
//String数组转List
List<String> strsToList1= Arrays.asList(strs);
for(String s:strsToList1){
System.out.println(s);
}
```


* 方法二：
```
//准备一个String数组
String[] strs = {"aa","bb","cc"};
//String数组转List
List<String> strsToList2=new ArrayList<>();
Collections.addAll(strsToList2,strs);
for(String s:strsToList2){
System.out.println(s);
}
```

* 方法三：
```
//准备一个String数组
String[] strs = {"aa","bb","cc"};
//String数组转List
List<String> strsToList3=new ArrayList<>();
for(String s:strs){
strsToList3.add(s);
}
for(String s:strsToList3){
System.out.println(s);
}
```
* java List转String
```
System.out.println(StringUtils.join(list, ","));
System.out.println(StringUtils.join(list, ""));

```

数组转List
String[] staffs = new String[]{"Tom", "Bob", "Jane"};
List staffsList = Arrays.asList(staffs);
需要注意的是， Arrays.asList() 返回一个受指定数组决定的固定大小的列表。所以不能做 add 、 remove 等操作，否则会报错。

List staffsList = Arrays.asList(staffs);
staffsList.add("Mary"); // UnsupportedOperationException
staffsList.remove(0); // UnsupportedOperationException


如果想再做增删操作呢？将数组中的元素一个一个添加到列表，这样列表的长度就不固定了，可以进行增删操作。

List staffsList = new ArrayList<String>();
for(String temp: staffs){
  staffsList.add(temp);
}
staffsList.add("Mary"); // ok
staffsList.remove(0); // ok


数组转Set
String[] staffs = new String[]{"Tom", "Bob", "Jane"};
Set<String> staffsSet = new HashSet<>(Arrays.asList(staffs));
staffsSet.add("Mary"); // ok
staffsSet.remove("Tom"); // ok

List转数组
String[] staffs = new String[]{"Tom", "Bob", "Jane"};
List staffsList = Arrays.asList(staffs);
Object[] result = staffsList.toArray();


List转Set
String[] staffs = new String[]{"Tom", "Bob", "Jane"};
List staffsList = Arrays.asList(staffs);
Set result = new HashSet(staffsList);


Set转数组
String[] staffs = new String[]{"Tom", "Bob", "Jane"};
Set<String> staffsSet = new HashSet<>(Arrays.asList(staffs));
Object[] result = staffsSet.toArray();

Set转List
String[] staffs = new String[]{"Tom", "Bob", "Jane"};
Set<String> staffsSet = new HashSet<>(Arrays.asList(staffs));
List<String> result = new ArrayList<>(staffsSet);
