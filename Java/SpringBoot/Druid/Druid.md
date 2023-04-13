### Druid简介
- Java程序很大一部分要操作数据库，为了提高性能操作数据库的时候，又不得不使用数据库连接池。
- Druid 是阿里巴巴开源平台上一个数据库连接池实现，结合了 C3P0、DBCP 等 DB 池的优点，同时加入了日志监控。
- Druid 可以很好的监控 DB 池连接和 SQL 的执行情况，天生就是针对监控而生的 DB 连接池。
- Druid已经在阿里巴巴部署了超过600个应用，经过一年多生产环境大规模部署的严苛考验。
- Spring Boot 2.0 以上默认使用 Hikari 数据源，可以说 Hikari 与 Driud 都是当前 Java Web 上最优秀的数据源，我们来重点介绍 Spring Boot 如何集成 Druid 数据源，如何实现数据库监控。
- Github地址：https://github.com/alibaba/druid/

### com.alibaba.druid.pool.DruidDataSource 基本配置参数
配置	 |	说明
--|--
name|		配置这个属性的意义在于没如果存在多个数据源，监控的时候可以通过名字来区分开来。如果没有配置，将会生成一个名字，格式是"DataSource-"+System.identityHashCode(this)
jdbcUrl|		连接数据库的url，不同数据库不一样
username|		连接数据库的用户名
password|		连接数据库的密码。如果你不希望密码直接写在配置文件中
driverClassName|		这一项可配可不配，如果不配置druid会根据url自动识别dbType，然后选择相应的driverClassName(建议配置下)
initialSize|		初始化时建立物理连接的个数，初始化发生在显示调用init方法，或者第一次getConnection时
maxActive|		最大连接池数量
maxIdle|		已经不再使用，配置了也没效果
minIdle|		最小连接池数量
maxWait|		获取连接时最大等待时间，单位毫秒，配置了maxWait之后，缺省启用公平锁，并发效率会有所下降，如果需要可以通过配置useUnfairLock属性为true使用非公平锁
poolPreparedStatements|		是否缓存preparedStatement，也就是PsCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql下建议关闭
maxOpenPreparedStatements|		要启用PSCache，必须配置大于0，当大于0时，poolPreparedStatements自动触发修改为true。在Druid中，不会存在Oracle下PSCache占用内存过多的问题，可以把这个数值配置打一下，比如说100
validationQuery|		用来检测连接是否有效的sql，要求是一个查询语句。如果validationQuery为null，testOnBorrow、testOnReturn 、testWhileIdle都不会起作用
testOnBorrow|		申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能
testOnReturn|		归还连接时执行它validationQuery检测连接是否有效，做了这个配置会降低性能
testWhileIdle|		建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunMills，执行validationQuery检测连接是否有效
timeBetweenEvictionRunMillis|		有两个含义:Destory线程会检测连接的间隔时间 testWhileIdle的判断依据，详细看testWhileIdele属性的说明
numTestsPerEvictionRun|		不再使用，一个DruidDataSource只支持一个EvicationRun
minEvictableIdleTimeMillis|		
connectionInitSqls|		物理连接初始化的时候执行sql
exceptionSorter|		当数据库抛出一些不可恢复的异常时，抛弃连接
filters|		属性类型是字符串，通过别名的方式配置扩展插件，常用的插件有:监控统计用的filter：stat日志用的filter;log4j防御注入的filter:wall
proxyFilters|		类型是List<com.alibaba.druid,filter.Filter>，如果同时配置filter和proxyFilters，是组合关系，并非


配置数据源
1、添加上 Druid 数据源依赖
```xml
		<!--spring-->
		<!-- https://mvnrepository.com/artifact/com.alibaba/druid -->
		<dependency>
			<groupId>com.alibaba</groupId>
			<artifactId>druid</artifactId>
			<version>1.1.21</version>
		</dependency>
        
        <!--spring boot-->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.1.10</version>
        </dependency>

```
2、切换数据源；之前已经说过 Spring Boot 2.0 以上默认使用 com.zaxxer.hikari.HikariDataSource 数据源，但可以 通过 spring.datasource.type 指定数据源。

```properties
spring:
  datasource:
    username: root
    password: 123456
    url: jdbc:mysql://192.168.10.132:3306/jdbc
    driver-class-name: com.mysql.jdbc.Driver
    type: com.alibaba.druid.pool.DruidDataSource
#自动往数据库建表
#    schema:
#      - classpath:department.sql
    initialSize: 5
    minIdle: 5
    maxActive: 20
    maxWait: 60000
    timeBetweenEvictionRunsMillis: 60000
    minEvictableIdleTimeMillis: 300000
    validationQuery: SELECT 1 FROM DUAL
    testWhileIdle: true
    testOnBorrow: false
    testOnReturn: false
    poolPreparedStatements: true
#   配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙
    filters: stat,wall,log4j
    maxPoolPreparedStatementPerConnectionSize: 20
    useGlobalDataSourceStat: true
    connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500
```

```properties
spring:
  datasource:
    # 使用阿里的Druid连接池
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    # 填写你数据库的url、登录名、密码和数据库名
    url: jdbc:mysql://127.0.0.1:3306/test?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai
    username: root
    password: root
  druid:
    # 连接池的配置信息
    # 初始化大小，最小，最大
    initial-size: 5
    min-idle: 5
    maxActive: 20
    # 配置获取连接等待超时的时间
    maxWait: 60000
    # 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
    timeBetweenEvictionRunsMillis: 60000
    # 配置一个连接在池中最小生存的时间，单位是毫秒
    minEvictableIdleTimeMillis: 300000
    validationQuery: SELECT 1
    testWhileIdle: true
    testOnBorrow: false
    testOnReturn: false
    # 打开PSCache，并且指定每个连接上PSCache的大小
    poolPreparedStatements: true
    maxPoolPreparedStatementPerConnectionSize: 20
    # 配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙
    filters: stat,wall,slf4j
    # 通过connectProperties属性来打开mergeSql功能；慢SQL记录
    connectionProperties: druid.stat.mergeSql\=true;druid.stat.slowSqlMillis\=5000
    # 配置DruidStatFilter
    web-stat-filter:
      enabled: true
      url-pattern: "/*"
      exclusions: "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*"
    # 配置DruidStatViewServlet
    stat-view-servlet:
      url-pattern: "/druid/*"
      # IP白名单(没有配置或者为空，则允许所有访问)
      allow: 127.0.0.1,192.168.8.109
      # IP黑名单 (存在共同时，deny优先于allow)
      deny: 192.168.1.188
      #  禁用HTML页面上的“Reset All”功能
      reset-enable: false
      # 登录名
      login-username: admin
      # 登录密码
      login-password: 123456

```