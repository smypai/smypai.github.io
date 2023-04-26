
### demo 类

[java](/Java/ORM/JDBCo.java)

### 错误问题

异常错误：Loading class `com.mysql.jdbc.Driver'. This is deprecated. The new driver class is `com.mysql.cj.jdbc.Driver'. The driver is automatically registered via the SPI and manual loading of the driver class is generally unnecessary.

这个问题 是在我整合项目过程中出现的，用了最新的mysql 连接驱动

以前版本的jdbc.properties

jdbc.driverClass= com.mysql.dbc.Driverjdbc.url      = jdbc:mysql://127.0.0.1:3306/db?useUnicode=true&characterEncoding=utf8&serverTimezone=GMTjdbc.username = rootjdbc.password = root123

现在按照最新官方提示支持将com.mysql.jdbc.Driver 改为 com.mysql.cj.jdbc.Driver

jdbc.driverClass= com.mysql.cj.jdbc.Driverjdbc.url      = jdbc:mysql://127.0.0.1:3306/db?useUnicode=true&characterEncoding=utf8&serverTimezone=GMTjdbc.username = rootjdbc.password = root123

```xml

<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
</dependency>

spring.datasource.url=jdbc:mysql://localhost:3306/spring?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=Cliu123#
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver


com.mysql.jdbc.Driver 是 mysql-connector-java 5中的， 
driverClassName=com.mysql.jdbc.Driver
url=jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8&useSSL=false
username=root
password=


com.mysql.cj.jdbc.Driver 是 mysql-connector-java 6中的
driverClassName=com.mysql.cj.jdbc.Driver
url=jdbc:mysql://localhost:3306/test?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=false
username=root
password=
```
