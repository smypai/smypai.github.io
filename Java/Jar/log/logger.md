```xml
springboot默认的日志格式，挺好看
%d{yyyy-MM-dd HH:mm:ss.SSS} %5p ${PID:-} [%15.15t] %-40.40logger{39} : %m%n
参考 https://blog.csdn.net/LiPanGeng/article/details/52104481
logback默认的日志格式
%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n
lishuoboy推荐日志格式
%d{MM-dd HH:mm:ss.SSS} [%5level] %4line %40.40logger{39}.%-30.30method : %m%n


%d{yyyy-MM-dd HH:mm:ss.SSS}：显示日期，年-月-日 时-分-秒.毫秒（2021-01-01 01:01:01.001）
%5p：显示日志级别ERROR，WARN，INFO，DEBUG，TRACE；%5若字符长度小于5，则右边用空格填充，%-5若字符长度小于5，则左边用空格填充
%t：显示产生该日志的线程名
%5：若字符长度小于5，则右边用空格填充
%-5若字符长度小于5，则左边用空格填充
%.15：若字符长度超过15，截去多余字符，
%15.15若字符长度小于5，则右边用空格填充；若字符长度超过15，截去多余字符
%m：显示输出消息
%n：换行符

```