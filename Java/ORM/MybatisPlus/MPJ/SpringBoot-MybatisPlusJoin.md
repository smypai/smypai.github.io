1. 引入

```xml
<dependency>
<groupId>com.baomidou</groupId>
<artifactId>mybatis-plus-boot-starter</artifactId>
<version>3.5.3</version>
</dependency>

<dependency>
    <groupId>com.github.yulichang</groupId>
    <artifactId>mybatis-plus-join-boot-starter</artifactId>
    <version>1.4.4</version>
</dependency>
```

2. 使用

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

3. java配置sqlSessionFactory - Wiki - Gitee.com

```java
@Configuration
public class MybatisPlusConfig {

    /**
     * GlobalConfig 配置 MPJSqlInjector
     * 其他配置 略
     */
    @Bean
    public GlobalConfig globalConfig(MPJSqlInjector mpjSqlInjector) {
        GlobalConfig config = new GlobalConfig();
        //绑定 mpjSqlInjector
        config.setSqlInjector(mpjSqlInjector);
        //其他配置 略
        return config;
    }

    /**
     * 关联SqlSessionFactory与GlobalConfig
     * 设置mybatis 拦截器
     * 其他配置 略
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource, GlobalConfig globalConfig,
                                               MybatisPlusInterceptor mybatisPlusInterceptor) throws Exception {
        MybatisSqlSessionFactoryBean sessionFactory = new MybatisSqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        //关联SqlSessionFactory与GlobalConfig
        sessionFactory.setGlobalConfig(globalConfig);
        //其他配置 略
        return sessionFactory.getObject();
    }
}


```