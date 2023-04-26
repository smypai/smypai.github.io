## SpringMVC
1. pom.xml  xml配置sqlSessionFactory - Wiki - Gitee.com

```xml
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus</artifactId>
    <version>3.5.3</version>
</dependency>
<dependency>
    <groupId>com.github.yulichang</groupId>
    <artifactId>mybatis-plus-join</artifactId>
    <version>1.4.4</version>
</dependency>
```

2. xml配置 配置 MPJInterceptor 和 MPJSqlInjector

```xml
<bean id="sqlSessionFactory" class="com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean">
<!-- 其他属性 略 -->
<property name="plugins">
<array>
<!-- 其他拦截器 略 -->
<bean class="com.github.yulichang.interceptor.MPJInterceptor" /><!-- 这个拦截器要在最后 -->
</array>
</property>
<property name="globalConfig">
<bean class="com.baomidou.mybatisplus.core.config.GlobalConfig">
<!-- 其他属性 略 -->
<property name="sqlInjector">
<bean class="com.github.yulichang.injector.MPJSqlInjector"/>
</property>
</bean>
</property>
</bean>
```

3. 使用

```java
// mapper继承MPJBaseMapper
@Mapper
public interface UserMapper extends MPJBaseMapper<UserDO> {

}

// (可选)service继承MPJBaseService
public interface UserService extends MPJBaseService<UserDO> {

}

// (可选)serviceImpl继承MPJBaseServiceImpl
@Service
public class UserServiceImpl extends MPJBaseServiceImpl<UserMapper, UserDO> implements UserService {

}
```