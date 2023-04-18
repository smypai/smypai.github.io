```java


StringAssert.Contains
判断字符串中是否包含另一个字符串。

string str = "April";
StringAssert.Contains("pr", str);    //true
StringAssert.StartsWith
判断字符串是否以另一个字符串开头。

string str = "April";
StringAssert.StartsWith("Ap", str);          //true
StringAssert.EndsWith
判断字符串是否以另一个字符串结尾。

string str = "April";
StringAssert.EndsWith("il", str);           //true
StringAssert.Equals
 判断字符串是否相等。

StringAssert.Equals("April", "april");    //false
StringAssert.AreEqualIgnoringCase
忽略大小写的情况下，判断字符串是否相等。

StringAssert.AreEqualIgnoringCase("April","april");     //true
 
```