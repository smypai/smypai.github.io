## Springboot整合SpringDataJPA基于Restful风格实现增删改查功能。

```java
Spring Boot介绍
Spring Boot是由Pivotal团队提供的全新框架，其设计目的是用来简化新Spring应用的初始搭建以及开发过程。该框架使用了特定的方式来进行配置，从而使开发人员不再需要定义样板化的配置。通过这种方式，我们不必像Spring MVC一样写大量的xml配置文件。
GitHub源码链接位于文章底部。
总结来说，springboot是一个快速开发的框架，开箱即用，提供了各种默认配置来简化项目配置，能够集成第三方框架，这是通过maven依赖实现的，它简化了xml，采用注解的形式，springboot还内嵌了tomcat容器，帮助开发者快速开发与部署。
添加依赖
新建一个maven项目，在pom文件中添加以下依赖
这个依赖引入了mvc框架，开启了接口访问的方式，它也正是以这种maven依赖的方式集成第三方框架的。
<dependencies>
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-web</artifactId>
<version>2.1.3.RELEASE</version>
</dependency>
</dependencies>
主程序启动入口
在controller、service等同级目录下，创建Application.java启动类
SpringBootApplication注解=Configuration注解+EnableAutoConfiguration注解+ComponentScan注解。
Configuration注解负责初始化并启动spring容器，这个类内部方法上使用Bean注解可以实现spring容器的注入。
EnableAutoConfiguration注解负责初始化配置，启动springboot需要启动的项，后边详细解释此注解的作用。
ComponentScan注解负责扫包，springboot启动程序后，能够被外部访问的只有被SpringBootApplication注解扫描到的包下的类。
SpringBootApplication注解启动类默认扫包范围是当前包下或者子包下所有的类。
@SpringBootApplication
public class Application {
public static void main(String[] args) {
SpringApplication.run(Application.class, args);
}
}
控制层
@RestController
public class HelloWordController {
@GetMapping("/hello")
public String index() {
return "Hello World";
}
}
成功启动主程序之后，浏览器输入http://localhost:8080/hello便可以查看信息。（因为没有配置端口，默认端口就是8080）
修改默认端口
在resource资源目录下创建application.yml文件
server:
port: 8081
重新启动后，浏览器输入http://localhost:8081/hello可以查看信息。
application.yml文件内容的格式，以上文中的端口为例：
server+冒号+空格，然后换行，+一个tab键占位符+port+冒号+空格+8080，
意为server属性下的port属性的值为8080.
基于Restful风格实现增删改查功能
一、Restful风格介绍
是一种网络应用程序的设计风格和开发方式，REST指的是一组架构约束条件和原则。满足这些约束条件和原则的应用程序或设计就是 RESTful。
每一个URI代表1种资源；
客户端使用GET、POST、PUT、DELETE4个表示操作方式的动词对服务端资源进行操作：GET用来获取资源，POST用来新建资源（也可以用于更新资源），PUT用来更新资源，DELETE用来删除资源；
二、开发准备
1、新建数据库和表
在MySql中创建一个名为springboot的数据库和名为一张t_user的表。
CREATE DATABASE `springboot`;

USE `springboot`;

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
`id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
`name` varchar(10) DEFAULT NULL COMMENT '姓名',
`age` int(2) DEFAULT NULL COMMENT '年龄',
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
2、修改pom文件，添加依赖
<!--父级依赖，它用来提供相关的 Maven 默认依赖。
使用它之后，常用的springboot包依赖可以省去version 标签
配置UTF-8编码，指定JDK8-->
<parent>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-parent</artifactId>
<version>2.1.3.RELEASE</version>
<relativePath ></relativePath>
</parent>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <!-- MySQL 连接驱动依赖 -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.39</version>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.16.20</version>
        </dependency>
    </dependencies>
    <build>
        <resources>
            <!--允许maven创建xml文件，否则xml要放在resources里-->
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.xml</include>
                </includes>
                <filtering>true</filtering>
            </resource>
        </resources>
    </build>
3.工程结构
com.lxg.springboot.controller - Controller 层
com.lxg.springboot.dao - 数据操作层 DAO
com.lxg.springboot.common - 存放公共类
com.lxg.springboot.entity - 实体类
com.lxg.springboot.service - 业务逻辑层
Application - 应用启动类
application.yml - 应用配置文件，应用启动会自动读取配置


4.修改application.yml文件，添加数据库连接属性
server:
port: 8080

spring:
datasource:
username: root
password: root
driver-class-name: com.mysql.jdbc.Driver
url: jdbc:mysql://localhost:3306/springboot?useUnicode=true&characterEncoding=utf8
三、编码
1.在common中创建公共类
1.1 封装分页结果PageResult
@Data
public class PageResult<T> {
/**
* 总记录数
*/
private Long total;
/**
* 每页数据
*/
private List<T> rows;
public PageResult(Long total, List<T> rows) {
this.total = total;
this.rows = rows;
}
}
1.2 封装统一返回结果
@Data
public class Result {
/**
* 返回结构状态
*/
private Boolean flag;

    /**
     * 返回状态码
     */
    private Integer code;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 返回数据内容
     */
    private Object data;

    public Result(Boolean flag, Integer code, String message, Object data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(Boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    public Result() {
    }
}
1.3 返回状态码
public class StatusCode {
//成功
public static final Integer OK = 20000;
//失败
public static final Integer ERROR = 20001;
//用户名或密码错误
public static final Integer USER_PASS_ERROR = 20002;
//权限不足
public static final Integer ACCESS_ERROR = 20003;
//远程调用失败
public static final Integer REMOTE_ERROR = 20004;
//重复操作
public static final Integer REPEATE_ERROR = 20005;
}
2.在entity中新建实体类
@Data
@Entity
@Table(name="t_user")
public class User implements Serializable {
/** 编号 */
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private String id;
/** 姓名 */
private String name;
/** 年龄 */
private Integer age;
}
注解：Data：安装lombok插件再使用该注解可省略getter/setter、无参构造等方法，具体使用见文章https://www.lxgblog.com/article/1572329460
Entity:对实体注释，声明实体。
Table:与数据库表绑定。
Id:声明Id。
GeneratedValue(strategy = GenerationType.IDENTITY):数据库主键自增。
3.在dao中新建UserDao
UserDao需要继承JpaRepository<实体类,实体类ID类型>, JpaSpecificationExecutor<实体类>，
JpaRepository<>泛型的第二个参数与id有关，如findById(id)的id类型
public interface UserDao  extends JpaRepository<U实体类ID类型ser,String>, JpaSpecificationExecutor<User> {
//这里的数字指的是第几个参数
@Modifying
@Query("update User u set u.age =?2 where u.id =?1")
void update(String id, Integer age);
}
4.service层新建UserService
@Service
public class UserService {
@Autowired
private UserDao userDao;

	/**
	 * 新增或修改，无id为新增，有id为修改
	 * @param user
	 */
//	@Transactional
public void saveUser(User user) {
userDao.save(user);
/**
* 这里的save方法会更新所有字段，如果只传了age属性进行更新，
* name属性就会修改为null，避免这个问题需要使用下面的原生Sql
* 并且加上方法上的@Transactional注解
* userDao.update(user.getId(),user.getAge());
*/
}

	/**
	 * 根据id删除
	 * 
	 * @param id
	 */
	public void deleteUser(String id) {
		userDao.deleteById(id);
	}

	/**
	 * 查询所有
	 */
	public List<User> findAll() {
		return userDao.findAll();
	}

	/**
	 * 根据id查询
	 * 
	 * @param id
	 */
	public User findUserById(String id) {
		return userDao.findById(id).get();
	}

    /**
     * 条件查询+age排序
     * @param searchMap
     */
	public List<User> findSearch(Map searchMap) {
        Specification<User> specification = createSpecification(searchMap);
		Sort sort = new Sort(Sort.Direction.ASC, "age");
        return userDao.findAll(specification,sort);
	}

    /**
     * 条件+分页+age排序
     * @param searchMap
     * @param page
     * @param size
     */
	public Page<User> findSearch(Map searchMap, int page, int size) {
		Specification<User> specification = createSpecification(searchMap);
		Sort sort = new Sort(Sort.Direction.ASC, "age");
		PageRequest pageRequest = PageRequest.of(page - 1, size,sort);
		return userDao.findAll(specification, pageRequest);
	}

	/**
	 * 创建查询条件
	 */
	private Specification<User> createSpecification(Map searchMap) {
		return new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> preList = new ArrayList<Predicate>();
				if (searchMap.get("name") != null && !(searchMap.get("name")).equals("")) {
					preList.add(
							criteriaBuilder.like(root.get("name").as(String.class), "%" + searchMap.get("name") + "%"));
				}
				if (searchMap.get("age") != null && !(searchMap.get("age")).equals("")) {
					preList.add(criteriaBuilder.equal(root.get("age").as(Integer.class), searchMap.get("age")));
				}
				Predicate[] preArray = new Predicate[preList.size()];
				return criteriaBuilder.and(preList.toArray(preArray));
			}
		};
	}
}
5.controller层
@RestController
@RequestMapping(value = "/user")
public class UserController {
@Autowired
private UserService userService;

    /**
     * 新增
     * @param user
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result addUser(@RequestBody User user) {
        userService.saveUser(user);
        return new Result(true, StatusCode.OK,"新增成功");
    }

    /**
     * 修改
     * @param user
     */
    @RequestMapping(method = RequestMethod.PUT)
    public Result updateUser(@RequestBody User user) {
        if (user.getId() == null || user.getId().equals("")) {
            return new Result(false, StatusCode.ERROR,"无id,更新失败");
        }
        userService.saveUser(user);
        return new Result(true, StatusCode.OK,"更新成功");
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        userService.deleteUser(id);
        return new Result(true, StatusCode.OK,"删除成功");
    }

    /**
     * 查询所有
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<User> findAllUser() {
        return userService.findAll();
    }

    /**
     * 根据id查询
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findByUserId(@PathVariable String id) {
        return new Result(true, StatusCode.OK,"查询成功",userService.findUserById(id));
    }

    /**
     * 条件查询
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功 ",userService.findSearch(searchMap));
    }

    /**
     * 条件+分页
     * @param searchMap
     * @param page
     * @param size
     */
    @RequestMapping(value = "/search/{page}/{size}",method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap,@PathVariable int page,@PathVariable int size){
        Page<User> pageBean = userService.findSearch(searchMap, page, size);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<>(pageBean.getTotalElements(),pageBean.getContent()) );
    }
}
@PathVariable注解的参数与@RequestMapping中value中的{}内参数一致。
四、测试
启动Application 之后，使用postman工具进行接口的测试。postman的使用教程查看另一偏博文。
1.新增

2.根据id属性删除，再次查询所有，会发现少了一条

3.根据id属性修改，再次查询所有，会发现已经改变。

4.查询所有

5.根据id属性查询

6.条件+age排序查询

6.条件+age排序+分页查询

经过上面的例子可以发现，增删改查的url都是同一个,即localhost:8080/user, 如果有一些简单的条件，则放到它的后面作为条件。作为区别，使用了不同的请求方式，如Post,Get,Put,Delete等。


```