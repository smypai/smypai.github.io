### springboot集成mybatisplus小例子
```java
集成mybatisplus后，简单的CRUD就不用写了，如果没有特别的sql，就可以不用mapper的xml文件的。
目录


pom.xml文件
<?xml version="1.0" encoding="UTF-8"?>
 <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
     <modelVersion>4.0.0</modelVersion>
     <parent>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-starter-parent</artifactId>
         <version>1.5.21.RELEASE</version>
         <relativePath/> <!-- lookup parent from repository -->
     </parent>
     <groupId>com.xiaostudy</groupId>
     <artifactId>demo1</artifactId>
     <version>0.0.1-SNAPSHOT</version>
     <name>demo1</name>
     <description>Demo project for Spring Boot</description>
 
     <properties>
         <java.version>1.8</java.version>
     </properties>
 
     <dependencies>
         <!-- spring-boot -->
         <dependency>
             <groupId>org.springframework.boot</groupId>
             <artifactId>spring-boot-starter-web</artifactId>
         </dependency>
         <dependency>
             <groupId>org.springframework.boot</groupId>
             <artifactId>spring-boot-starter</artifactId>
         </dependency>
         <dependency>
             <groupId>org.springframework.boot</groupId>
             <artifactId>spring-boot-starter-test</artifactId>
             <scope>test</scope>
         </dependency>
 
         <!--mybatis-plus自动的维护了mybatis以及mybatis-spring的依赖，
         在springboot中这三者不能同时的出现，避免版本的冲突，表示：跳进过这个坑-->
         <dependency>
             <groupId>com.baomidou</groupId>
             <artifactId>mybatis-plus-boot-starter</artifactId>
             <version>3.1.1</version>
         </dependency>
 
         <!-- 引入Druid依赖，阿里巴巴所提供的数据源 -->
         <dependency>
             <groupId>com.alibaba</groupId>
             <artifactId>druid</artifactId>
             <version>1.0.29</version>
         </dependency>
 
         <!-- 提供mysql驱动 -->
         <dependency>
             <groupId>mysql</groupId>
             <artifactId>mysql-connector-java</artifactId>
             <version>5.1.38</version>
         </dependency>
 
     </dependencies>
 
     <build>
         <plugins>
             <plugin>
                 <groupId>org.springframework.boot</groupId>
                 <artifactId>spring-boot-maven-plugin</artifactId>
             </plugin>
         </plugins>
     </build>
 
 </project>
实体类User
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

@TableName("t_user")
public class User implements Serializable {
    private String id;
    private String userName;
    private String userPassword;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                '}';
    }
}


Dao[也叫Mapper]
package com.xiaostudy.demo1.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaostudy.demo1.entity.User;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper接口
 * </p>
 *
 * @author xiaostudy
 * @since 2019-09-15
 */
@Repository
public interface UserDao extends BaseMapper<User> {
}

Service
package com.xiaostudy.demo1.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaostudy.demo1.entity.User;

public interface UserService extends IService<User> {
}
Service实现类
package com.xiaostudy.demo1.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaostudy.demo1.entity.User;
import com.xiaostudy.demo1.mapper.UserDao;
import com.xiaostudy.demo1.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {
}

Controller
package com.xiaostudy.demo1.controller;

import com.xiaostudy.demo1.entity.User;
import com.xiaostudy.demo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/getAll")
    public String getAll() {
        List<User> list = userService.list();
        System.out.println("list:" + list);
        return list.toString();
    }

    @RequestMapping("/insert")
    public String insert() {
        User user = new User();
        user.setUserName("aaa");
        user.setUserPassword("bbb");
        boolean save = userService.save(user);
        return getAll();
    }
}

application-jdbc.yml
#数据源
spring:
security:
 basic:
   enabled: false
datasource:
 url: jdbc:mysql://127.0.0.1:3306/my_test?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&useSSL=false
 username: root
 password: 密码
 driver-class-name: com.mysql.jdbc.Driver
 druid:
   # 初始化时建立物理连接的个数。初始化发生在显示调用init方法，或者第一次getConnection时
   initialSize: 1
   # 最小连接池数量
   minIdle: 1
   # 最大连接池数量
   maxActive: 10
   # 配置获取连接等待超时的时间
   maxWait: 10000
   # 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
   timeBetweenEvictionRunsMillis: 60000
   # 配置一个连接在池中最小生存的时间，单位是毫秒
   minEvictableIdleTimeMillis: 300000
   # 验证连接有效与否的SQL，不同的数据配置不同
   validationQuery: select 1
   # 建议配置为true，不影响性能，并且保证安全性。
   # 申请连接的时候检测，如果空闲时间大于
   # timeBetweenEvictionRunsMillis，
   # 执行validationQuery检测连接是否有效。
   testWhileIdle: true
   # 申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
   # 这里建议配置为TRUE，防止取到的连接不可用
   testOnBorrow: true
   # 归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能
   testOnReturn: false
   # 是否缓存preparedStatement，也就是PSCache。
   # PSCache对支持游标的数据库性能提升巨大，比如说oracle。
   # 在mysql5.5以下的版本中没有PSCache功能，建议关闭掉。
   # 作者在5.5版本中使用PSCache，通过监控界面发现PSCache有缓存命中率记录，
   # 该应该是支持PSCache。
   # 打开PSCache，并且指定每个连接上PSCache的大小
   poolPreparedStatements: true
   maxPoolPreparedStatementPerConnectionSize: 20
   # 属性类型是字符串，通过别名的方式配置扩展插件，
   # 常用的插件有：
   # 监控统计用的filter:stat
   # 日志用的filter:log4j
   # 防御sql注入的filter:wall
   filters: stat

# ====================MybatisPlus====================
mybatis-plus:
# 如果是放在src/main/java目录下 classpath:/com/yourpackage/*/mapper/*Mapper.xml
# 如果是放在resource目录 classpath:/mapper/*Mapper.xml
mapper-locations: classpath*:com/frame/**/**.xml,classpath*:mapping/**/**.xml
#实体扫描，多个package用逗号或者分号分隔
typeAliasesPackage: com.frame.**.entity,com.frame.**.dto
global-config:
 #刷新mapper 调试神器
 db-config:
   #主键类型  0:"数据库ID自增", 1:"用户输入ID",2:"全局唯一ID (数字类型唯一ID)", 3:"全局唯一ID UUID";
   id-type: UUID
   #字段策略 0:"忽略判断",1:"非 NULL 判断"),2:"非空判断"
   field-strategy: 2
   #驼峰下划线转换
   column-underline: false
   #数据库大写下划线转换
#      capital-mode: true
   #逻辑删除配置
   logic-delete-value: 1
   logic-not-delete-value: 0
 refresh: true
configuration:
 #配置返回数据库(column下划线命名&&返回java实体是驼峰命名)，自动匹配无需as（没开启这个，SQL需要写as： select user_id as userId）
 map-underscore-to-camel-case: true
 cache-enabled: false
 #配置JdbcTypeForNull, oracle数据库必须配置
 jdbc-type-for-null: 'null'
 log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
 database-id: mysql

application.yml
server:
  port: 8080

spring:
  profiles:
    include: jdbc

测试
127.0.0.1:8080






```