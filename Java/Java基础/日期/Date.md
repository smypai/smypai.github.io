Java提供了两套处理日期和时间的API



1、旧的API，放在java.util 这个包下的：比较常用的有Date和Calendar等
2、新的API是java 8新引入的，放在java.time 这个包下的：LocalDateTime，ZonedDateTime，DateTimeFormatter和Instant等

为什么会有两套日期时间API，这个是有历史原因的，旧的API是jdk刚开始就提供的，随着版本的升级，逐渐发现原先的api不满足需要，暴露了一些问题，所以在java 8 这个版本中，重新引入新API。

这两套API都要了解，为什么呢？

因为java 8 发布时间是2014年，很多之前的系统还是沿用旧的API，所以这两套API都要了解，同时还要掌握两套API相互转化的技术。



## 1、Date
### 1.1 Date类说明
Date类负责时间的表示，在计算机中，时间的表示是一个较大的概念，现有的系统基本都是利用从1970.1.1 00:00:00 到当前时间的毫秒数进行计时，这个时间称为epoch（时间戳）
```java
package java.util;

public class Date implements java.io.Serializable, Cloneable, Comparable<Date>{
      private transient long fastTime;
}
```
java.util.Date是java提供表示日期和时间的类，类里有个long 类型的变量fastTime，它是用来存储以毫秒表示的时间戳。


### 1.2 date常用的用法
```java
import java.util.Date;
    //获取当前时间
    Date date = new Date();
    System.out.println("获取当前时间:"+date);
    //获取时间戳
    System.out.println("获取时间戳:"+date.getTime());

    // date时间是否大于afterDate 等于也为false
    Date afterDate = new Date(date.getTime()-3600*24*1000);
    System.out.println("after:"+date.after(afterDate));
    System.out.println("after:"+date.after(date));

    // date时间是否小于afterDate 等于也为false
    Date beforeDate = new Date(date.getTime()+3600*24*1000);
    System.out.println("before:"+date.before(beforeDate));
    System.out.println("before:"+date.before(date));

    //两个日期比较
    System.out.println("compareTo:"+date.compareTo(date));
    System.out.println("compareTo:"+date.compareTo(afterDate));
    System.out.println("compareTo:"+date.compareTo(beforeDate));

    //转为字符串
    System.out.println("转为字符串:"+date.toString());
    //转为GMT时区 toGMTString() java8 中已废弃
    System.out.println("转为GMT时区:"+date.toGMTString());
    //转为本地时区 toLocaleString() java8 已废弃
    System.out.println("转为本地时区:"+date.toLocaleString());
```

### 1.3 自定义时间格式-SimpleDateFormat

date的toString方法转成字符串，不是我们想要的时间格式，如果要自定义时间格式，就要使用SimpleDateFormat
```java
//获取当前时间
    Date date = new Date();
    System.out.println("获取当前时间:"+date);
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    System.out.println(simpleDateFormat.format(date));
    SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
    System.out.println(simpleDateFormat1.format(date));

//SimpleDateFormat也可以方便的将字符串转成Date

//获取当前时间
    String str = "2021-07-13 23:48:23";
    try {
      Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str);
      System.out.println(date);
    } catch (ParseException e) {
      e.printStackTrace();
    }
```
```java


日期和时间格式化参数说明

yyyy：年
MM：月
dd：日
hh：1~12小时制(1-12)
HH：24小时制(0-23)
mm：分
ss：秒
S：毫秒
E：星期几
D：一年中的第几天
F：一月中的第几个星期(会把这个月总共过的天数除以7)
w：一年中的第几个星期
W：一月中的第几星期(会根据实际情况来算)
a：上下午标识
k：和HH差不多，表示一天24小时制(1-24)。
K：和hh差不多，表示一天12小时制(0-11)。
z：表示时区


1.4 SimpleDateFormat线程不安全原因

1.4.1SimpleDateFormat线程为什么是线程不安全的呢？

来看看SimpleDateFormat的源码，先看format方法：



// Called from Format after creating a FieldDelegate
    private StringBuffer format(Date date, StringBuffer toAppendTo,
                                FieldDelegate delegate) {
        // Convert input date to time field list
        calendar.setTime(date);
...
    }


问题就出在成员变量calendar，如果在使用SimpleDateFormat时，用static定义，那SimpleDateFormat变成了共享变量。那SimpleDateFormat中的calendar就可以被多个线程访问到。



SimpleDateFormat的parse方法也是线程不安全的：



 public Date parse(String text, ParsePosition pos)
    {
     ...
         Date parsedDate;
        try {
            parsedDate = calb.establish(calendar).getTime();
            // If the year value is ambiguous,
            // then the two-digit year == the default start year
            if (ambiguousYear[0]) {
                if (parsedDate.before(defaultCenturyStart)) {
                    parsedDate = calb.addYear(100).establish(calendar).getTime();
                }
            }
        }
        // An IllegalArgumentException will be thrown by Calendar.getTime()
        // if any fields are out of range, e.g., MONTH == 17.
        catch (IllegalArgumentException e) {
            pos.errorIndex = start;
            pos.index = oldStart;
            return null;
        }


        return parsedDate;  
 }


由源码可知，最后是调用**parsedDate = calb.establish(calendar).getTime();**获取返回值。方法的参数是calendar，calendar可以被多个线程访问到，存在线程不安全问题。



我们再来看看**calb.establish(calendar)**的源码



图片



calb.establish(calendar)方法先后调用了cal.clear()和cal.set(),先清理值，再设值。但是这两个操作并不是原子性的，也没有线程安全机制来保证，导致多线程并发时，可能会引起cal的值出现问题了。



1.4.2 验证SimpleDateFormat线程不安全



public class SimpleDateFormatDemoTest {

  private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        //1、创建线程池
        ExecutorService pool = Executors.newFixedThreadPool(5);
        //2、为线程池分配任务
        ThreadPoolTest threadPoolTest = new ThreadPoolTest();
        for (int i = 0; i < 10; i++) {
            pool.submit(threadPoolTest);
        }
        //3、关闭线程池
        pool.shutdown();
    }

    static class  ThreadPoolTest implements Runnable{

        @Override
        public void run() {
        String dateString = simpleDateFormat.format(new Date());
        try {
          Date parseDate = simpleDateFormat.parse(dateString);
          String dateString2 = simpleDateFormat.format(parseDate);
          System.out.println(Thread.currentThread().getName()+" 线程是否安全: "+dateString.equals(dateString2));
        } catch (Exception e) {
          System.out.println(Thread.currentThread().getName()+" 格式化失败 ");
        }
        }
    }
}
图片



出现了两次false，说明线程是不安全的。而且还抛异常，这个就严重了。



2、LocalDateTime

2.1 LocalDateTime类说明

表示当前日期时间，相当于：yyyy-MM-ddTHH:mm:ss



2.2 LocalDateTime常用的用法

2.2.1获取当前日期和时间

LocalDate d = LocalDate.now(); // 当前日期
LocalTime t = LocalTime.now(); // 当前时间
LocalDateTime dt = LocalDateTime.now(); // 当前日期和时间
System.out.println(d); // 严格按照ISO 8601格式打印
System.out.println(t); // 严格按照ISO 8601格式打印
System.out.println(dt); // 严格按照ISO 8601格式打印


由运行结果可行，本地日期时间通过now()获取到的总是以当前默认时区返回的



2.2.2获取指定日期和时间

LocalDate d2 = LocalDate.of(2021, 07, 14); // 2021-07-14, 注意07=07月
LocalTime t2 = LocalTime.of(13, 14, 20); // 13:14:20
LocalDateTime dt2 = LocalDateTime.of(2021, 07, 14, 13, 14, 20);
LocalDateTime dt3 = LocalDateTime.of(d2, t2);
System.out.println("指定日期时间："+dt2);
System.out.println("指定日期时间："+dt3);
图片



2.2.3 日期时间的加减法及修改

LocalDateTime currentTime = LocalDateTime.now(); // 当前日期和时间
System.out.println("------------------时间的加减法及修改-----------------------");
//3.LocalDateTime的加减法包含了LocalDate和LocalTime的所有加减,上面说过,这里就只做简单介绍
System.out.println("3.当前时间：" + currentTime);
System.out.println("3.当前时间加5年：" + currentTime.plusYears(5));
System.out.println("3.当前时间加2个月：" + currentTime.plusMonths(2));
System.out.println("3.当前时间减2天：" + currentTime.minusDays(2));
System.out.println("3.当前时间减5个小时：" + currentTime.minusHours(5));
System.out.println("3.当前时间加5分钟：" + currentTime.plusMinutes(5));
System.out.println("3.当前时间加20秒：" + currentTime.plusSeconds(20));
//还可以灵活运用比如：向后加一年，向前减一天，向后加2个小时，向前减5分钟，可以进行连写
System.out.println("3.同时修改(向后加一年，向前减一天，向后加2个小时，向前减5分钟)：" + currentTime.plusYears(1).minusDays(1).plusHours(2).minusMinutes(5));
System.out.println("3.修改年为2025年：" + currentTime.withYear(2025));
System.out.println("3.修改月为12月：" + currentTime.withMonth(12));
System.out.println("3.修改日为27日：" + currentTime.withDayOfMonth(27));
System.out.println("3.修改小时为12：" + currentTime.withHour(12));
System.out.println("3.修改分钟为12：" + currentTime.withMinute(12));
System.out.println("3.修改秒为12：" + currentTime.withSecond(12));





2.3 线程安全

网上大家都在说Java 8提供的LocalDateTime是线程安全的，但是它是如何实现的呢，今天让我们来挖一挖：



public final class LocalDateTime
        implements Temporal, TemporalAdjuster, ChronoLocalDateTime<LocalDate>, Serializable {
        ... 
        }


由上面的源码可知，LocalDateTime是不可变类。我们都知道一个Java并发编程规则：不可变对象永远是线程安全的。



对比下Date的源码 ,Date是可变类，所以是线程不安全的。



public class Date
    implements java.io.Serializable, Cloneable, Comparable<Date>
{
...
}


3、LocalDateTime和Date相互转化

3.1 Date转LocalDateTime

System.out.println("------------------方法一：分步写-----------------------");
//实例化一个时间对象
Date date = new Date();
//返回表示时间轴上同一点的瞬间作为日期对象
Instant instant = date.toInstant();
//获取系统默认时区
ZoneId zoneId = ZoneId.systemDefault();
//根据时区获取带时区的日期和时间
ZonedDateTime zonedDateTime = instant.atZone(zoneId);
//转化为LocalDateTime
LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();
System.out.println("方法一：原Date = " + date);
System.out.println("方法一：转化后的LocalDateTime = " + localDateTime);

System.out.println("------------------方法二：一步到位（推荐使用）-----------------------");
//实例化一个时间对象
Date todayDate = new Date();
//Instant.ofEpochMilli(long l)使用1970-01-01T00:00:00Z的纪元中的毫秒来获取Instant的实例
LocalDateTime ldt = Instant.ofEpochMilli(todayDate.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
System.out.println("方法二：原Date = " + todayDate);
System.out.println("方法二：转化后的LocalDateTime = " + ldt);
图片



3.2 LocalDateTime转Date

System.out.println("------------------方法一：分步写-----------------------");
//获取LocalDateTime对象，当前时间
LocalDateTime localDateTime = LocalDateTime.now();
//获取系统默认时区
ZoneId zoneId = ZoneId.systemDefault();
//根据时区获取带时区的日期和时间
ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
//返回表示时间轴上同一点的瞬间作为日期对象
Instant instant = zonedDateTime.toInstant();
//转化为Date
Date date = Date.from(instant);
System.out.println("方法一：原LocalDateTime = " + localDateTime);
System.out.println("方法一：转化后的Date = " + date);


System.out.println("------------------方法二：一步到位（推荐使用）-----------------------");
//实例化一个LocalDateTime对象
LocalDateTime now = LocalDateTime.now();
//转化为date
Date dateResult = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
System.out.println("方法二：原LocalDateTime = " + now);
System.out.println("方法二：转化后的Date = " + dateResult);



四、DateTimeFormatter

4.1DateTimeFormatter类说明

DateTimeFormatter的作用是进行格式化显示，且DateTimeFormatter是不可变类且是线程安全的。



public final class DateTimeFormatter {
...
}


说到时间的格式化显示，就要说老朋友SimpleDateFormat了，之前格式化Date就要用上。但是我们知道SimpleDateFormat是线程不安全的，文前有介绍。



4.2 DateTimeFormatter常用的用法

ZonedDateTime zonedDateTime = ZonedDateTime.now();
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm ZZZZ");
System.out.println(formatter.format(zonedDateTime));

DateTimeFormatter usFormatter = DateTimeFormatter.ofPattern("E, MMMM/dd/yyyy HH:mm", Locale.US);
System.out.println(usFormatter.format(zonedDateTime));

DateTimeFormatter chinaFormatter = DateTimeFormatter.ofPattern("yyyy MMM dd EE HH:mm", Locale.CHINA);
System.out.println(chinaFormatter.format(zonedDateTime));

