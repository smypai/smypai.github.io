## Springboot 3.0.0 整合swagger

- Automated JSON API documentation for API's built with Spring



1. 引入POM-
```pom
<dependencies>
    <!-- swagger 3.0.0 -->
    <dependency>
        <groupId>io.springfox</groupId>
        <artifactId>springfox-boot-starter</artifactId>
        <version>3.0.0</version>
    </dependency>
    
	<!--  低版本的swagger  -->
    <!--<dependency>
        <groupId>io.springfox</groupId>
        <artifactId>springfox-swagger-common</artifactId>
        <version>2.9.2</version>
    </dependency>
    <dependency>
        <groupId>io.springfox</groupId>
        <artifactId>springfox-swagger2</artifactId>
        <version>2.9.2</version>
    </dependency>-->
</dependencies>
```

2、配置类
```java
@Configuration
//@EnableSwagger2  // 老版本开启swagger
//@EnableOpenApi   // 网上搜到的解决报错添加的
public class SwaggerConfig {

    @Bean
    public Docket createRestApi1() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .enable(flag)//通过方法  判断自己项目所使用的的环境  在不在这里的指定的环境里 如果在就能访问的到 不在就访问不到
                .title("我的接口文档")
                .contact(new Contact("mySwagger", "heheheh", "hello@163.com"))
                .version("1.0")
                .description("接口文档描述")
                .build();

        //docket对象用于封装接口文档相关信息
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .groupName("用户接口组")
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.xf.swagger.controller"))
                .build();
        return docket;
    }
}

```
- Swagger3.0——>注解：@EnableOpenApi
- Swagger2.0——>注解：@EnableSwagger2


3. 访问地址 启动项目，访问swagger，访问地址：localhost:端口号/swagger-ui
- Swagger3.0——>测试地址：http://localhost:8080/swagger-ui/index.html
- Swagger2.0——>测试地址：http://localhost:8080/swagger-ui.html

4. 注解

- @Api(tags = “xxx模块说明”)	作用在模块类上
- @ApiOperation(“xxx接口说明”)	作用在接口方法上
- @ApiModel(“xxxPOJO说明”)	作用在模型类上：如VO、BO
- @ApiModelProperty(value = “xxx属性说明”,hidden = true)	作用在类方法和属性上，hidden设置为true可以隐藏该属性
- @ApiParam(“xxx参数说明”)	作用在参数、方法和字段上，类似@ApiModelProperty
- @ApiModel("用户实体类")：用于实体类上，描述实体类
- @ApiModelProperty(value = "用户名",hidden = false)：用于属性上描述实体类属性，hidden功能是，是否隐藏该属性
```
@Api模块配置，用在Controller类上，描述Api接口@ApiOperation接口配置，用在方法上，描述接口方法
@ApiParam方法参数配置，用在入参中或注解中@Apilgnore忽略此接口不生成文档
@ApiModel用于类，表示对类进行说明，描述一个Model的信息
@ApiModelProperty用于属性，表示对类中变量的说明，描述一个model的属性
@ApiImplicitParams用在方法上包含一组参数说明@ApiImplicitParam用来注解来给方法入参增加说明
@ApiResponses用于表示一组响应结果@ApiResponse用在@ApiResponses中，一般用于表达一个错误的响应信息
@Api模块配置，用在Controller类上，描述Api接口
@ApiOperation接口配置，用在方法上，描述接口方法
@ApiParam方法参数配置，用在入参中或注解中
@Apilgnore忽略此接口不生成文档
@ApiModel用于类，表示对类进行说明，描述一个Model的信息
@ApiModelProperty用于属性，表示对类中变量的说明，描述一个model的属性
@ApiImplicitParams用在方法上包含一组参数说明
@ApiImplicitParam用来注解来给方法入参增加说明
@ApiResponses用于表示一组响应结果
@ApiResponse用在@ApiResponses中，一般用于表达一个错误的响应信息


@Api：用在请求的类上，表示对类的说明
    tags="说明该类的作用，可以在UI界面上看到的注解"
    value="该参数没什么意义，在UI界面上也看到，所以不需要配置"
 
 
@ApiOperation：用在请求的方法上，说明方法的用途、作用
    value="说明方法的用途、作用"
    notes="方法的备注说明"
 
 
@ApiImplicitParams：用在请求的方法上，表示一组参数说明
    @ApiImplicitParam：用在@ApiImplicitParams注解中，指定一个请求参数的各个方面
        name：参数名
        value：参数的汉字说明、解释
        required：参数是否必须传
        paramType：参数放在哪个地方
            · header --> 请求参数的获取：@RequestHeader
            · query --> 请求参数的获取：@RequestParam
            · path（用于restful接口）--> 请求参数的获取：@PathVariable
            · body（不常用）
            · form（不常用）    
        dataType：参数类型，默认String，其它值dataType="Integer"       
        defaultValue：参数的默认值
 
 
@ApiResponses：用在请求的方法上，表示一组响应
    @ApiResponse：用在@ApiResponses中，一般用于表达一个错误的响应信息
        code：数字，例如400
        message：信息，例如"请求参数没填好"
        response：抛出异常的类
 
 
@ApiModel：用于响应类上，表示一个返回响应数据的信息
            （这种一般用在post创建的时候，使用@RequestBody这样的场景，
            请求参数无法使用@ApiImplicitParam注解进行描述的时候）
    @ApiModelProperty：用在属性上，描述响应类的属性
    
```



```java
swagger2	swagger3	注解位置
@Api(tags = “接口类描述”)	@Tag(tags = “接口类描述”)	Controller 类上
@ApiOperation(“接口方法描述”)	@Operation(summary =“接口方法描述”)	Controller 方法上
@ApiImplicitParams	@Parameters	Controller 方法上
@ApiImplicitParam	@Parameter(description=“参数描述”)	Controller 方法上 @Parameters 里
@ApiParam(“参数描述”)	@Parameter(description=“参数描述”)	Controller 方法的参数上
@ApiIgnore	@Parameter(hidden = true) 或 @Operation(hidden = true) 或 @Hidden	-
@ApiModel(description = “dto类描述”)	@Schema(description = “dto类描述”)	DTO类上
@ApiModelProperty(“属性描述”)	@Schema(description = “属性描述”)	DTO属性上
```

6.Knife4j增强方案
```yaml
<dependency>
  <groupId>com.github.xiaoymin</groupId>
  <artifactId>knife4j-spring-boot-starter</artifactId>
  <version>3.0.3</version>
</dependency>

# 配置文件
@EnableKnife4j//该注解是knife4j提供的增强注解,Ui提供了例如动态参数、参数过滤、接口排序等增强功能,如果你想使用这些增强功能就必须加该注解，否则可以不用加
# 访问地址
http://host:port/doc.html
```