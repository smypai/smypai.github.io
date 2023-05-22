## springboot2操作mongodb基本功能
```java

先上结构图

pom.xml

<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.2.5.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.mongodb</groupId>
    <artifactId>demo</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>mongodb_demo</name>
    <description>com mongodb demo project for Spring Boot</description>

    <properties>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web-services</artifactId>
        </dependency>
        
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-mongodb</artifactId>
        </dependency>        

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
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

属性文件
src/main/resources/application.properties
spring.data.mongodb.uri=mongodb://localhost:27017/test
1.编写文档文件
src/main/java/com/mongodb/demo/document/Book.java

package com.mongodb.demo.document;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="book")
public class Book
{
    @Id
    private String id;
    private Integer price;
    private String name;
    private String info;
    private String publish;
    public String getPublish() {
        return publish;
    }
    public void setPublish(String publish) {
        this.publish = publish;
    }
    private Date createTime;
    private Date updateTime;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Integer getPrice() {
        return price;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getInfo() {
        return info;
    }
    public void setInfo(String info) {
        this.info = info;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
}

2.编写控制器
src/main/java/com/mongodb/demo/controller/MongoDbController.java

package com.mongodb.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.demo.document.Book;
import com.mongodb.demo.service.MongoDbService;

@RestController
public class MongoDbController
{
    @Autowired
    private MongoDbService mongoDbService;
    
    @PostMapping("/mongo/save")
    public String saveObj(@RequestBody Book book)
    {
        return mongoDbService.saveObj(book);
    }
    
    @GetMapping("/mongo/findAll")
    public List<Book> findAll()
    {
        return mongoDbService.findAll();
    }
    
    @GetMapping("/mongo/findOne")
    public Book findOne(@RequestParam String id)
    {
        return mongoDbService.getBookById(id);
    }
    
    @GetMapping("/mongo/findOneByName")
    public Book findOneByName(@RequestParam String name)
    {
        return mongoDbService.getBookByName(name);
    }
    
    @PostMapping("/mongo/update")
    public String update(@RequestBody Book book)
    {
        return mongoDbService.updateBook(book);
    }
    
    @PostMapping("/mongo/delOne")
    public String delOne(@RequestBody Book book)
    {
        return mongoDbService.deleteBook(book);
    }
    
    
    @GetMapping("/mongo/delById")
    public String delById(@RequestParam String id)
    {
        return mongoDbService.deleteBookById(id);
    }
    
    
    @GetMapping("/mongo/findlikes")
    public List<Book> findByLikes(@RequestParam String search) {
        return mongoDbService.findByLikes(search);
    }

}

3.编写服务层
src/main/java/com/mongodb/demo/service/MongoDbService.java

package com.mongodb.demo.service;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mongodb.demo.document.Book;


@Service
public class MongoDbService
{
    

    private static final Logger logger = LoggerFactory.getLogger(MongoDbService.class);
    
    @Autowired
    private MongoTemplate mongoTemplate;
    
    /**
     * 保存对象
     * @param book
     * @return
     */
    public String saveObj(Book book)
    {
        book.setCreateTime(new Date());
        book.setUpdateTime(new Date());
        mongoTemplate.save(book);
        return "save success";
    }
    
    /**
     * 查询所有
     * turn
     */
    public List<Book> findAll()
    {
        return mongoTemplate.findAll(Book.class);
    }
    
    public Book getBookById(String id)
    {
        Query query = new Query(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query,Book.class);
    }
    
    public Book getBookByName(String name)
    {
        Query query = new Query(Criteria.where("name").is(name));
        return mongoTemplate.findOne(query, Book.class);
    }
    
    public String updateBook(Book book)
    {
        Query query = new Query(Criteria.where("_id").is(book.getId()));
        Update update = new Update().set("publish",book.getPublish()).set("info",book.getInfo()).set("updateTime", new Date());
        // 更新第一条并返回第一条
        mongoTemplate.updateFirst(query, update, Book.class);
        //更新w全部并返回全部
        // mongoTemplate.updateMulti(query,update,Book.class);
        // 更新对像，不存在，则不添加        
        // mongoTemplate.upsert(query, update, Book.class);
        return "success-update";
    }
    
    public String deleteBook(Book book)
    {
        mongoTemplate.remove(book);
        return "success";
    }
    
    public String deleteBookById(String id)
    {
        Book book = getBookById(id);
        deleteBook(book);
        return "success-delete";
    }
    
    public List<Book> findByLikes(String search)
    {
        Query query = new Query();
        Criteria criteria = new Criteria();
        Pattern pattern = Pattern.compile("^.*"+search+".*$",Pattern.CASE_INSENSITIVE);
        criteria.where("name").regex(pattern);
        List<Book> lists = mongoTemplate.findAllAndRemove(query, Book.class);
        return lists;
    }
}

```