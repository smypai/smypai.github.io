1. 基本类型
可以看到， 这种情况下会有编译错误，那如果右边改为引用类型呢？

此时可以看到，仍然有编译错误，那么再试试特殊的 null 呢？

好像也不行，由此可以得出，基本类型不能用于 instanceof 判断.
```java
if(data instanceof Map) return JSONObject.toJSONString(data);
```

2. null
对于 null，我们可以看到它只能用来判断是否是引用类型，并且返回的总是 false，表明它不是任意一个引用类型的对象实例.

它用来判断是否是基本类型或 null 的对象时，也会有编译错误，不可用.
```java
if(data instanceof null) return JSONObject.toJSONString(data);
```


3. 引用类型
我们创建如下关系的类和接口：
                
对于如下代码：
```java
public class TestInstanceof {
    public static void main(String[] args) {
        Dog dog = new Dog();
        Animal animal = new Animal();
        Animal cat = new Cat();
        
        System.out.println(dog instanceof Dog);     // true
        System.out.println(dog instanceof Bark);    // true
        
        System.out.println(animal instanceof Bark); // true
        System.out.println(animal instanceof Dog);  // false
        
        System.out.println(cat instanceof Animal);  // true
        System.out.println(cat instanceof Cat);     // true
        
        System.out.println(dog instanceof Cat);     // 编译错误
        System.out.println(dog instanceof Integer); // 编译错误
        System.out.println(dog instanceof int);     // 编译错误
        System.out.println(dog instanceof null);    // 编译错误
    }
}
```
基本类型完全不能用于 instanceof 判断
null 只能放在 instanceof 关键字的左边
4. 数组类型
对引用类型示例稍加修改，可以得到数组类型用来判断时的情况：
```java
public class TestInstanceof {
    public static void main(String[] args) {
        Dog[] dog = new Dog[3];
        Animal animal = new Animal();
        System.out.println(dog instanceof Dog[]);    // true
        System.out.println(dog instanceof Bark[]);   // true
    }
}
```
特别地，基本类型的数组也是可以用来判断的：
```java
public class TestInstanceof {
    public static void main(String[] args) {
        int[] ary = new int[3];
        System.out.println(ary instanceof int[]);    // true
    }
}
```
4. 应用场景
instanceof 关键字一般用于强制转换，在强转之前用它来判断是否可以强制转换：
```java
public B convert(A a) {
    if (a instanceof B) {
        return (B) a;
    }
    return null;
}
```
5. 实现原理
用类似 Java 的伪代码来描述该原理如下所示：
```java
boolean rs;
if (obj == null) {
  rs= false;
} else {
  try {
      T temp = (T) obj; 
      rs= true;
  } catch (ClassCastException e) {
      rs = false;
  }
}
return rs;
```
obj==null，返回 false
如果 obj 强制转换为 T 时发生编译错误，返回 false
运行时，如果 T != null，并且 (T) obj 不引发 ClassCastException，返回 true
如果 obj != null 并且 (T) obj 不引发 ClassCastException，返回 true ，否则值为 false
总结：

obj==null，返回 false
(T) obj 不引发 ClassCastException，返回 true，否则返回 false
