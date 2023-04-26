### isEmpty 和 isBlank 的用法区别，

也许你两个都不知道，也许你除了isEmpty/isNotEmpty/isNotBlank/isBlank外，并不知道还有isAnyEmpty/isNoneEmpty/isAnyBlank/isNoneBlank的存在, come on ,让我们一起来探索org.apache.commons.lang3.StringUtils;这个工具类
#### isEmpty系列
- StringUtils.isEmpty（）是否为空. 可以看到 " " 空格是会绕过这种空判断,因为是一个空格,并不是严格的空值,会导致 isEmpty(" ")=false
- StringUtils.isEmpty(null) = true
- StringUtils.isEmpty("") = true
- StringUtils.isEmpty(" ") = false
- StringUtils.isEmpty(“bob”) = false
- StringUtils.isEmpty(" bob ") = false

```java 
public static boolean isEmpty(final CharSequence cs) {
  return cs == null || cs.length() == 0;
}

// StringUtils.isNotEmpty（）
// 相当于不为空 , = !isEmpty()
public static boolean isNotEmpty(final CharSequence cs) {
return !isEmpty(cs);
}

// StringUtils.isAnyEmpty（）
// 是否有一个为空,只有一个为空,就为true.
StringUtils.isAnyEmpty(null) = true
StringUtils.isAnyEmpty(null, “foo”) = true
StringUtils.isAnyEmpty("", “bar”) = true
StringUtils.isAnyEmpty(“bob”, “”) = true
StringUtils.isAnyEmpty(" bob ", null) = true
StringUtils.isAnyEmpty(" ", “bar”) = false
StringUtils.isAnyEmpty(“foo”, “bar”) = false

public static boolean isAnyEmpty(final CharSequence... css) {
    if (ArrayUtils.isEmpty(css)) {
        return true;
    }
    for (final CharSequence cs : css){
        if (isEmpty(cs)) {
            return true;
        }
    }
    return false;
}

// StringUtils.isNoneEmpty（）
// 相当于!isAnyEmpty(css) , 必须所有的值都不为空才返回true
/
* <p>Checks if none of the CharSequences are empty ("") or null.</p>
*
* <pre>
* StringUtils.isNoneEmpty(null)             = false
* StringUtils.isNoneEmpty(null, "foo")      = false
* StringUtils.isNoneEmpty("", "bar")        = false
* StringUtils.isNoneEmpty("bob", "")        = false
* StringUtils.isNoneEmpty("  bob  ", null)  = false
* StringUtils.isNoneEmpty(" ", "bar")       = true
* StringUtils.isNoneEmpty("foo", "bar")     = true
* </pre>
*
* @param css  the CharSequences to check, may be null or empty
* @return {@code true} if none of the CharSequences are empty or null
* @since 3.2
  */
  public static boolean isNoneEmpty(final CharSequence... css) {
```

### isBank系列

```java
// StringUtils.isBlank（）
// 是否为真空值(空格或者空值)
StringUtils.isBlank(null) = true
StringUtils.isBlank("") = true
StringUtils.isBlank(" ") = true
StringUtils.isBlank(“bob”) = false
StringUtils.isBlank(" bob ") = false
/
* <p>Checks if a CharSequence is whitespace, empty ("") or null.</p>
* @param cs  the CharSequence to check, may be null
* @return {@code true} if the CharSequence is null, empty or whitespace
* @since 2.0
* @since 3.0 Changed signature from isBlank(String) to isBlank(CharSequence)
  */
  public static boolean isBlank(final CharSequence cs) {
    int strLen;
    if (cs == null || (strLen = cs.length()) == 0) {
        return true;
    }
    for (int i = 0; i < strLen; i++) {
        if (Character.isWhitespace(cs.charAt(i)) == false) {
            return false;
        }
    }
    return true;
  }

// StringUtils.isNotBlank（）
// 是否真的不为空,不是空格或者空值 ,相当于!isBlank();
public static boolean isNotBlank(final CharSequence cs) {
return !isBlank(cs);
}

// StringUtils.isAnyBlank（）
// 是否包含任何真空值(包含空格或空值)
StringUtils.isAnyBlank(null) = true
StringUtils.isAnyBlank(null, “foo”) = true
StringUtils.isAnyBlank(null, null) = true
StringUtils.isAnyBlank("", “bar”) = true
StringUtils.isAnyBlank(“bob”, “”) = true
StringUtils.isAnyBlank(" bob ", null) = true
StringUtils.isAnyBlank(" ", “bar”) = true
StringUtils.isAnyBlank(“foo”, “bar”) = false
/
* <p>Checks if any one of the CharSequences are blank ("") or null and not whitespace only..</p>
* @param css  the CharSequences to check, may be null or empty
* @return {@code true} if any of the CharSequences are blank or null or whitespace only
* @since 3.2
  */
  public static boolean isAnyBlank(final CharSequence... css) {
      if (ArrayUtils.isEmpty(css)) {
        return true;
      }
      for (final CharSequence cs : css){
          if (isBlank(cs)) {
              return true;
          }
      }
      return false;
  }

// StringUtils.isNoneBlank（）
// 是否全部都不包含空值或空格
StringUtils.isNoneBlank(null) = false
StringUtils.isNoneBlank(null, “foo”) = false
StringUtils.isNoneBlank(null, null) = false
StringUtils.isNoneBlank("", “bar”) = false
StringUtils.isNoneBlank(“bob”, “”) = false
StringUtils.isNoneBlank(" bob ", null) = false
StringUtils.isNoneBlank(" ", “bar”) = false
StringUtils.isNoneBlank(“foo”, “bar”) = true
/
* <p>Checks if none of the CharSequences are blank ("") or null and whitespace only..</p>
* @param css  the CharSequences to check, may be null or empty
* @return {@code true} if none of the CharSequences are blank or null or whitespace only
* @since 3.2
*/
public static boolean isNoneBlank(final CharSequence... css) {
return !isAnyBlank(css);
}
```

StringUtils的其他方法
![16](/photo/img_16.png)
![17](/photo/img_16.png)

可以参考官方的文档,里面有详细的描述,有些方法还是很好用的.

https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/StringUtils.html







