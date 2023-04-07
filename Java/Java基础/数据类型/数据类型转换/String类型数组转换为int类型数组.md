原数组：

String[] arr ={"1","2","4","6"};
1
转换方法：

一、使用循环进行转换

public int[] StringToInt(String[] arr){
int[] array = new int[arr.length];
for (int i = 0; i < arr.length; i++) {
array[i] = Integer.parseInt(arr[i]);
}
return array;

二、使用lambda表达式

方式一：

public int[] StringToInt(String[] arr){
int[] array = Arrays.asList(arr).stream().mapToInt(Integer::parseInt).toArray();
return array;

方式二：

public int[] StringToInt(String[] arr){
    int[] array = Arrays.stream(arr).mapToInt(Integer::parseInt).toArray();
    return array;
}