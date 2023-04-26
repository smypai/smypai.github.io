### BeanUtils
BeanUtils是Apache Commons组件的成员之一，主要用于简化JavaBean封装数据的操作。

简化反射封装参数的步骤，给对象封装参数。

好处： BeanUtils给对象封装参数的时候会进行类型自动转换。

#### JavaBean
//JavaBean就是一个类，但该类需要满足以下三个条件：

1. 类//必须使用public修饰。
   
2. 提供无参数的构造器。
   
3. 提供getter和setter方法访问属性。

#### JavaBean的两个重要概念
字段：就是成员变量，字段名就是成员变量名。

属性：属性名通过setter/getter方法去掉set/get前缀，首字母小写获得。

比如：setName()--> Name--> name
一般情况下，字段名和属性名是一致的。

##### 常用的操作有以下三个：
1. 对JavaBean的属性进行赋值和取值。
   getProperty()
   setProperty()
   
2. 将一个JavaBean所有属性赋值给另一个JavaBean对象中。
   copyProperties
   
3. 将一个Map集合的数据封装到一个JavaBean对象中。
   populate

BeanUtils的应用场景
1. 快速将一个JavaBean各个属性的值，赋值给具有相同结构的另一个JavaBean中。
   
2. 快速收集表单中的所有数据到JavaBean中。

BeanUtils相关JAR包
BeanUtils是第三方组织写的工具类，要使用它，得先下载对应的工具JAR包。

1. 下载地址：http://commons.apache.org/
   
2. 相关的jar包
   
   commons‐beanutils‐1.9.3.jar // 工具核心包
   
   commons‐logging‐1.2.jar // 日志记录包
   
   commons‐collections‐3.2.2.jar // 增强的集合包

BeanUtils的基本使用
导入相关JAR包

#### BeanUtils常用方法
1. public static void setProperty(Object bean, String name, Object value)给指定对象bean的指定name属性赋值为指定值value。//如果指定的属性不存在，则什么也不发生。
2. public static String getProperty(Object bean, String name)获取指定对象bean指定name属性的值。//如果指定的属性不存在，则会抛出异常。注意：当属性的类型是数组类型时，获取到的值数组中的第一个值。
3. public static void copyProperties(Object dest, Object orig)    将对象orig的属性值赋值给对象dest对象对应的属性注意：只有属性名名相同且类型一致的才会赋值成功。
4. public static void populate(Object bean, Map<String, ? extends Object>properties)将一个Map集合中的数据封装到指定对象bean中注意：对象bean的属性名和Map集合中键要相同。

#### 对JavaBean的属性进行赋值和取值

注意：

1. Beanutils给对象设置属性的时候是依赖了setXXX方法。
2. BeanUtils获取对象的属性时依赖的是getXXX方法。

```java
public class BeanUtilDemo {
    public static void main(String[] args) throws Exception {
        //得倒字节码对象        
        Class clazz = Class.forName("com.beanutils.User");
        //利用Class对象创建对象        
        User s = (User) clazz.newInstance();
        //给对象封装数据        
        BeanUtils.setProperty(s, "id", "001"); //给对象设置属性          
        // 参数一： 对象，  参数二：属性名, 参数三： 属性的值。        
        BeanUtils.setProperty(s, "name", "小芸");
        //参数一： 对象，  参数二：属性名, 参数三： 属性的值。        
        BeanUtils.setProperty(s, "age", "18");
        System.out.println("id:" + BeanUtils.getProperty(s, "id"));
        //参数一： 对象，  参数二： 获取的属性的属性名                
        System.out.println(s);
    }
}

class User {
    private int id;
    private int age;
    private String name;
    private double sal;

    public User(String name) {
        this.name = name;
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" + "name='" + name + '\'' + ", age=" + age + '}';
    }

    public void sleep(int i) {
        System.out.println(name + "满满的睡了" + i + "小时。。。");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSal() {
        return sal;
    }

    public void setSal(double sal) {
        this.sal = sal;
    }
}
```

将一个JavaBean对象的属性赋值给另一个JavaBean对象
只要两个JavaBean对象的属性名称名称相同就会赋值，不同的不赋值
将一个Map集合的数据封装到一个JavaBean对象中

```java
public class BeanUtilsDemo {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        //创建Map集合
        Map<String, String> map = new HashMap<String, String>();
        System.out.println("请输入您的身份证:");
        String id = scanner.next();
        map.put("id", id);
        System.out.println("请输入您的姓名:");
        String name = scanner.next();
        map.put("name", name);
        System.out.println("请输入您的年龄:");
        String age = scanner.next();
        map.put("age", age);
        //给对象封装数据 ， 数据全部都在Map中。
        User s = new User();
        //把map中的数据封装到对象上。
        BeanUtils.populate(s, map);
        System.out.println("用户信息是：" + s);
    }
}
```