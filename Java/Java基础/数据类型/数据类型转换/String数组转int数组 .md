### string数组类型转换为int数组.
* 方法一：ConvertAll的用法
```
public static int StrToInt(string str)
{
    return int.Parse(str);
}
string[] arrs = new string[] { "100", "300", "200" };
int[] arri = Array.ConvertAll(arrs, new Converter<string, int>(StrToInt));
```

* 方法二：使用数组循环分别转换。
```
string[] str1 = new string[] {"100","300","200"};
int[] intTemp = new int[str1.Length];
for (int i = 0; i <str1.Length; i++)
{
int.TryParse(str1[i]，out intTemp[i]);//int.TryParse函数返回Bool型。不会抛出异常
}
```


* 方法三：
```
string[] str1 = new string[] {"100","300","200"};

int[] intTemp = new int[str1.Length];
for (int i = 0; i <str1.Length; i++)
{
intTemp[i] = int.Parse(str1[i]);
}
```


* 方法四：

```
Integer[] aftIdArray = (Integer[])ConvertUtils.convert(aftIdStringArray, Integer.class);

