### SpringBoot+Jpa+MySql学习

```java 

由于jpa的功能强大，后续会继续写关于jpa的介绍已经使用，本文只是简单介绍一下它与springboot的整合。
jpa不需要像mybatis一样创建表，首先给大家看一下application.properties文件代码，其中包含了jpa的配置和数据库配置，尤其注意一下spring.jpa.hibernate.ddl-auto属性，代码如下：
##端口号
server.port=8888

##数据库配置
##数据库地址
spring.datasource.url=jdbc:mysql://localhost:3306/test?characterEncoding=utf8&useSSL=false
##数据库用户名
spring.datasource.username=root
##数据库密码
spring.datasource.password=root
##数据库驱动
spring.datasource.driver-class-name=com.mysql.jdbc.Driver


##validate  加载hibernate时，验证创建数据库表结构
##create   每次加载hibernate，重新创建数据库表结构，这就是导致数据库表数据丢失的原因。
##create-drop        加载hibernate时创建，退出是删除表结构
##update                 加载hibernate自动更新数据库结构
##validate 启动时验证表的结构，不会创建表
##none  启动时不做任何操作
spring.jpa.hibernate.ddl-auto=create

##控制台打印sql
spring.jpa.show-sql=true
启动类application
package com.dalaoyang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootJpaApplication.class, args);
    }
}

pom文件大致和整合mybatis一样，只是把其中的mybatis改成了jpa，代码如下：
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
<modelVersion>4.0.0</modelVersion>

    <groupId>com.dalaoyang</groupId>
    <artifactId>springboot_jpa</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>springboot_jpa</name>
    <description>springboot_jpa</description>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>1.5.9.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
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

实体类city，其中@Table中的name对应数据库中表的名称
package com.dalaoyang.entity;

import javax.persistence.*;

/**
* @author dalaoyang
* @Description
* @project springboot_learn
* @package com.dalaoyang.Entity
* @email 397600342@qq.com
* @date 2018/4/7
  */
  @Entity
  @Table(name="city")
  public class City {

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private int cityId;
  private String cityName;
  private String cityIntroduce;

  public City(int cityId, String cityName, String cityIntroduce) {
  this.cityId = cityId;
  this.cityName = cityName;
  this.cityIntroduce = cityIntroduce;
  }

  public City(String cityName, String cityIntroduce) {
  this.cityName = cityName;
  this.cityIntroduce = cityIntroduce;
  }

  public City() {
  }

  public int getCityId() {
  return cityId;
  }

  public void setCityId(int cityId) {
  this.cityId = cityId;
  }

  public String getCityName() {
  return cityName;
  }

  public void setCityName(String cityName) {
  this.cityName = cityName;
  }

  public String getCityIntroduce() {
  return cityIntroduce;
  }

  public void setCityIntroduce(String cityIntroduce) {
  this.cityIntroduce = cityIntroduce;
  }
  }
  然后就是jpa的重要地方，CityRepository，继承了JpaRepository，
  由于本文只是简单介绍了jpa的简单功能，所以JpaRepository中内置的方法已经足够使用。
  代码如下：
  package com.dalaoyang.repository;

import com.dalaoyang.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

/**
* @author dalaoyang
* @Description
* @project springboot_learn
* @package com.dalaoyang.Repository
* @email 397600342@qq.com
* @date 2018/4/7
  */
  public interface CityRepository extends JpaRepository<City,Integer> {
  }
  最后是controller，里面和mybatis整合一样，方法上面写的就是对应的测试方法。
  package com.dalaoyang.controller;

import com.dalaoyang.entity.City;
import com.dalaoyang.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* @author dalaoyang
* @Description
* @project springboot_learn
* @package com.dalaoyang.controller
* @email 397600342@qq.com
* @date 2018/4/7
  */
  @RestController
  public class CityController {


    @Autowired
    private CityRepository cityRepository;


    //http://localhost:8888/saveCity?cityName=北京&cityIntroduce=中国首都
    @GetMapping(value = "saveCity")
    public String saveCity(String cityName,String cityIntroduce){
        City city = new City(cityName,cityIntroduce);
        cityRepository.save(city);
        return "success";
    }

    //http://localhost:8888/deleteCity?cityId=2
    @GetMapping(value = "deleteCity")
    public String deleteCity(int cityId){
        cityRepository.delete(cityId);
        return "success";
    }

    //http://localhost:8888/updateCity?cityId=3&cityName=沈阳&cityIntroduce=辽宁省省会
    @GetMapping(value = "updateCity")
    public String updateCity(int cityId,String cityName,String cityIntroduce){
        City city = new City(cityId,cityName,cityIntroduce);
        cityRepository.save(city);
        return "success";
    }

    //http://localhost:8888/getCityById?cityId=3
    @GetMapping(value = "getCityById")
    public City getCityById(int cityId){
        City city = cityRepository.findOne(cityId);
        return city;
    }
}
到这里启动项目就可以简单测试一下整合的效果了。
```