## Springboot3使用swagger文档

- springdoc-openapi java library helps to automate the generation of API documentation using spring boot projects.
- 官网地址：https://springdoc.org/v2/
- 注意：使用的是V2版本，这个版本支持springboot3.0


1. 引入依赖
```gradle
implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2'
```

2. 配置application.yml文件（其实不配置也行）
```yaml
server:
  port: 8080
# 配置swagger文档的访问路径
springdoc:
  swagger-ui:
    path: /swagger-ui.html
```

3. 编写swagger配置类SwaggerConfig

```java
package com.config;
 
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 
@Configuration
public class SwaggerConfig {
 
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("标题")
                        .description("我的API文档")
                        .version("v1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("外部文档")
                        .url("https://springshop.wiki.github.org/docs"));
    }
 
}
```
4. 打开浏览器访问 http://localhost:8080/swagger-ui.html

5. sprigdoc 注解使用， 从Springfox迁移过来的，需要修改注解：     
```java
@Api → @Tag

@ApiIgnore → @Parameter(hidden = true) or @Operation(hidden = true) or @Hidden

@ApiImplicitParam → @Parameter

@ApiImplicitParams → @Parameters

@ApiModel → @Schema

@ApiModelProperty(hidden = true) → @Schema(accessMode = READ_ONLY)

@ApiModelProperty → @Schema

@ApiOperation(value = "foo", notes = "bar") → @Operation(summary = "foo", description = "bar")

@ApiParam → @Parameter

@ApiResponse(code = 404, message = "foo") → @ApiResponse(responseCode = "404", description = "foo")
```

6. 结构和支持
![结构](/photo/img_7.png)
```yaml
# Spring WebMvc支持
<dependency>
      <groupId>org.springdoc</groupId>
      <artifactId>springdoc-openapi-starter-webmvc-api</artifactId>
      <version>2.0.0-M2</version>
   </dependency>

# Spring WebFlux 支持
 <dependency>
      <groupId>org.springdoc</groupId>
      <artifactId>springdoc-openapi-starter-webflux-ui</artifactId>
      <version>2.0.0-M2</version>
   </dependency>
```

7. 全面配置    

```yaml

springdoc:
  # OpenAPI文档相关参数
  api-docs:
    # OpenAPI文档开关, true: 开启OpenAPI文档访问功能, false: 关闭。
    enabled: true       
    # JSON格式的OpenAPI文档的访问路径
    path: /v3/api-docs
  # 扫描哪些包来生成OpenAPI文档, 多个包名用逗号分隔
  packages-to-scan: * 
  # 路径匹配规则, API路径符合这些匹配规则才会包含到OpenAPI文档中, 多个规则用逗号分隔
  paths-to-match: /* 
  # 返回媒体类型匹配规则, 返回媒体类型符合这些匹配规则才会包含到OpenAPI文档中, 多个规则用逗号分隔
  produces-to-match: /* 
  # 请求头匹配规则, 请求头符合这些匹配规则才会包含到OpenAPI文档中, 多个规则用逗号分隔
  headers-to-match: /* 
  # 请求媒体类型匹配规则, 请求媒体类型符合这些匹配规则才会包含到OpenAPI文档中, 多个规则用逗号分隔
  consumes-to-match: /* 
  # 排除路径匹配规则, API路径符合这些匹配规则会排除在OpenAPI文档之外, 多个规则用逗号分隔
  paths-to-exclude: 
  # 排除包匹配规则, 包名符合这些匹配规则会排除在OpenAPI文档之外, 多个规则用逗号分隔
  packages-to-exclude: 
  # 默认请求媒体类型
  default-consumes-media-type: application/json
  # 默认返回的响应媒体类型
  default-produces-media-type: '*/*' 
  # 是否禁用OpenAPI文档缓存, 
  # 禁用后每次访问${springdoc.api-docs.path}都会重新生成(适合开发调试阶段)当响应会比较缓慢。
  cache.disabled: false 
  # 是否显示Spring Actuator的接口
  show-actuator: false 
  # 是否自动将类名生成为Tag
  auto-tag-classes: true 
  # 是否包含返回ModelAndView对象的接口
  model-and-view-allowed: false 
  # 是否从 @ControllerAdvice 注解获取接口的响应信息.
  override-with-generic-response: true 
  # 是否开启接口分组功能, 开启后, 一个App可以生成多个OpenAPI文档, 每个文档显示一部分接口。
  api-docs.groups.enabled: true 
  # 分组配置
  group-configs:
      # 分组名称
    - group: XXX
      # 同`springdoc.packages-to-scan`
      packages-to-scan: *    
      # 同`springdoc.paths-to-match`
      paths-to-match: /*     
      # 同`springdoc.paths-to-exclude`
      paths-to-exclude: ``   
      # 同`springdoc.packages-to-exclude`
      packages-to-exclude:   
      # 同`springdoc.produces-to-match`
      produces-to-match: /*  
      # 同`springdoc.consumes-to-match`
      consumes-to-match: /*  
      # 同`springdoc.headers-to-match`
      headers-to-match: /* 
  # webjar资源的访问路径前缀
  webjars.prefix: /webjars 
  # 是否翻译属性值, true: Schema中的属性的值可以用Spring的表达式来编写, 然后运行时自动转成真实的取值
  api-docs.resolve-schema-properties: false 
  # 删除无效的引用定义
  remove-broken-reference-definitions: true 
  # 是否格式化输出的OpenAPI文档, 方便人类阅读
  writer-with-default-pretty-printer: false 
  # 是否启用 deprecating model converter.
  model-converters.deprecating-converter.enabled: true 
  # 生成的Schema等组件的名称是否使用全名(类似java的Class.getName和getSimpleName的区别)
  use-fqn: false # FQN是指 fully qualified names.
  # 是否显示spring security的登录接口
  show-login-endpoint: false
  # 是否预加载OpenAPI文档, true: 程序启动的时候就生成OpenAPI文档, false: 第一次访问OpenAPI文档的时候生成。
  pre-loading-enabled: false 

```