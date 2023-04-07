### 1.POM
* https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/ObjectUtils.html
```xml
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-lang3</artifactId>
     <version>3.9</version>
</dependency>
```
### 2.常用API介绍
* 方法名	方法介绍
* isEmpty(Object object)	检查Object是否为空或null
* isNotEmpty(Object object)	检查Object是否不为空且不为null
* allNotNull(Object… values)	检查所有元素是否都不为空,返回一个boolean
* anyNotNull(Object… values)	检查所有元素是否至少有一个不为空,返回一个
* clone(T obj)	拷贝一个对象并返回，克隆的对象是final类型
* cloneIfPossible(T obj)	拷贝一个对象并返回，克隆的对象是final类型，无法克隆则返回被克隆对象
* compare(T c1, T c2)	比较两个对象,返回一个int值
* defaultIfNull(T object, T defaultValue)	如果对象为空返回一个默认值
* firstNonNull(T… values)	返回元素中第一个不为空的值
* notEqual(Object object1, Object object2)	判断两个对象不相等，返回一个boolean
* CONST(final T v)	将参数转换为常量
