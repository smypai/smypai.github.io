```
spring.jpa.database-platform=org.hibernate.dialect.MySQL5InnoDBDialect
spring.jpa.show-sql=true
##spring.jpa.hibernate.ddl-auto=create

yml
spring
jpa:
#这个参数是在建表的时候，将默认的存储引擎切换为 InnoDB 用的
database-platform: org.hibernate.dialect.MySQL5InnoDBDialect
#配置在日志中打印出执行的 SQL 语句信息。
show-sql: true
hibernate:
#配置指明在程序启动的时候要删除并且创建实体类对应的表
ddl-auto: create
参数：
spring.jpa.hibernate.ddl-auto=create
##validate           加载hibernate时，验证创建数据库表结构
##create             每次加载hibernate，重新创建数据库表结构，这就是导致数据库表数据丢失的原因。
##create-drop        加载hibernate时创建，退出是删除表结构
##update             加载hibernate自动更新数据库结构
##validate           启动时验证表的结构，不会创建表
##none               启动时不做任何操作

##控制台打印sql
spring.jpa.show-sql=true
```