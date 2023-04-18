## spring boot 过滤器&拦截器与aop

#### 适用场景：  
- 拦截器的应用场景：权限控制，日志打印，参数校验
- 过滤器的应用场景：跨域问题解决，编码转换，shiro权限过滤


### 一、过滤器 (Filter) 
- 1.1  什么是过滤器
过滤器Filter基于 *****Servlet***** 实现，过滤器在spring上一层，所以不能通过@Component注解交给spring 管理。
但是拦截器可以通过@Component交给spring管理。

过滤器的主要应用场景是对字符编码、跨域等问题进行过滤。Servlet的工作原理是拦截配置好的客户端请求，然后对Request和Response进行处理。

Filter过滤器随着web应用的启动而启动，只初始化一次。

在开发过程中，拦截器用的很少，大部分使用场景都是过滤器。

- 1.2 springboot配置过滤器    
```text
方式一：使用@WebFilter  
1、创建过滤器，实现接口Filter：
2、使用注解@WebFilter(filterName="xxFilter",urlPatterns="/**")
3、实现doFilter方法
4、将过滤器交给spring管理  
5、在@Configuration类手动配置过滤器，@Bean标识FilterRegistrationBean
```

```java
package com.example.com_chenshuai.Controller;
 
import lombok.extern.slf4j.Slf4j;
 
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
 
// 过滤器
@Slf4j
@WebFilter(filterName = "myFilter",urlPatterns = "/*")
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初始化时调用
        Filter.super.init(filterConfig);
    }
 
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
 
        log.info("filter begin ...");
        // 强转
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        // 获取请求url
        String requestURI = request.getRequestURI();
        // 获取请求 参数
        String requestParameter = request.getParameter("");
 
        // FilterChain类似于责任链，
        // 继续调用下一个过滤器，如果下一个没有过滤器，则继续往下走（比如调用业务代码之类的）。
        filterChain.doFilter(servletRequest,servletResponse);
 
        // 如果基于url进行拦截，可以使用过滤器
        // servletResponse 是返回结果，过滤器可以改写返回结果。
        // 拦截器，我们也可以获取请求的url、参数等。也可以获取返回结果。但是我们不会在拦截器里改写返回结果。
        // 在开发过程中，拦截器用的很少，大部分使用场景都是过滤器。
        // 过滤器在spring上一层，所以不能通过spring注入过滤器的实例。但是拦截器可以通过spring注入。
        // aop不能获取请求的参数等信息，和返回结果。aop能获取到调用的类，方法及方法参数。以及方法的返回值。
        // 如果想通过方法判断，需要使用aop。如果想通过请求判断，需要使用过滤器。
    }
 
    @Override
    public void destroy() {
        // 销毁时调用
        Filter.super.destroy();
    }
}
````

说明：

```text
注解@WebFilter(filterName = "myFilter",urlPatterns = "/*")

filterName = "xxFilter"，设置过滤器名称

urlPatterns = "/*"，设置过滤的url，这里/*表示过滤所有的url。

方法介绍：

 init() ：web 应用程序启动时，web 服务器将创建Filter 的实例对象，并调用其init方法，读取web.xml配置，完成对象的初始化功能，从而为后续的用户请求作好拦截的准备工作（filter对象只会创建一次，init方法也只会执行一次）。开发人员通过init方法的参数，可获得代表当前filter配置信息的FilterConfig对象。（这个我们不需要太关注）

destroy()： 当容器销毁 过滤器实例时调用该方法，一般在方法中销毁或关闭资源，在过滤器 Filter 的整个生命周期也只会被调用一次（这个我们不需要太关注）

doFilter() ：该方法完成实际的过滤操作，当客户端请求方法与过滤器设置匹配的URL时，Servlet容器将先调用过滤器的doFilter方法。FilterChain用户访问后续过滤器。

public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) 
我们可以通过ServletRequest servletRequest 获取请求的相关信息，比如url、参数等。（这里需要先强转成HttpServletRequest）

       // 强转
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        // 获取请求url
        String requestURI = request.getRequestURI();
        // 获取请求 参数
        String requestParameter = request.getParameter("");
 我们可以通过ServletResponse servletResponse 获得请求的返回结果。我们可以通过过滤器来改写返回结果。

FilterChain filterChain 类似于责任链。

  // 继续调用下一个过滤器，如果下一个没有过滤器，则继续往下走（比如调用业务代码之类的）
        filterChain.doFilter(servletRequest,servletResponse);


```

2、将过滤器交给spring管理

在@Configuration类手动配置过滤器，@Bean标识FilterRegistrationBean

```java
package com.example.com_chenshuai.configuration;
 
import com.example.com_chenshuai.Controller.LoginInterceptor;
import com.example.com_chenshuai.Controller.MyFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
 
@Configuration
public class BaseConfiguration {
 
 
    // 将这个类交给spring管理。不使用注解的方式，因为使用注解，需要在源码上@Component
    // 使用方法，返回@Bean
    // FilterRegistrationBean 这个类，是第三方的，我们不能加@Component
    @Bean
    public FilterRegistrationBean<MyFilter> getFilter(){
        FilterRegistrationBean<MyFilter> filter = new FilterRegistrationBean<MyFilter>(new MyFilter());
        return filter;
 
    }
}
```

### 二、拦截器(Interceptor) 

2.1 什么是拦截器    
拦截器(Interceptor)同 Filter 过滤器一样，它俩都是面向切面编程——AOP 的具体实现。你可以使用 Interceptor 来执行某些任务，例如在 Controller 处理请求之前编写日志，添加或更新配置……，在 Spring中，当请求发送到 Controller 时，在被Controller处理之前，它必须经过 Interceptors（0或多个）。

2.2 使用拦截器方法    
1、创建拦截器，实现接口HandlerInterceptor。并通过注解@Component交给Spring管理

```java
package com.example.com_chenshuai.Controller;
 
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
// 拦截器
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 拦截器，我们也可以获取请求的url、参数等。也可以获取返回结果。
        log.info("interceptor  pre begin..");
        String token = request.getHeader("token");
        if(token !=null&&token.equals("1234")){
            log.info("login success");
            // return true 继续往下走，调用下一个拦截器
            return true;
 
        }
        // return false 就拦截该请求
        return false;
    }
 
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("interceptor  post begin..");
 
    }
 
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("interceptor  afterCompletion");
    }
}

说明
方法解释：

preHandler(HttpServletRequest request, HttpServletResponse response, Object handler)
方法在请求处理之前被调用。该方法在 Interceptor 类中最先执行，用来进行一些前置初始化操作或是对当前请求做预处理，也可以进行一些判断来决定请求是否要继续进行下去。该方法的返回至是 Boolean 类型，当它返回 false 时，表示请求结束，后续的 Interceptor 和 Controller 都不会再执行；当它返回为 true 时会继续调用下一个 Interceptor 的 preHandle 方法，如果已经是最后一个 Interceptor 的时候就会调用当前请求的 Controller 方法。 

我们可以通过request，获取用户请求的相关信息，如url、参数、header等等。

String token = request.getHeader("token");

我们可以通过response获取，请求的返回结果。做相应的处理

postHandler(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) 
方法在当前请求处理完成之后，也就是 Controller 方法调用之后执行，但是它会在 DispatcherServlet 进行视图返回渲染之前被调用，所以我们可以在这个方法中对 Controller 处理之后的 ModelAndView 对象进行操作。

afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handle, Exception ex) 
方法需要在当前对应的 Interceptor 类的 preHandle 方法返回值为 true 时才会执行。顾名思义，该方法将在整个请求结束之后，也就是在 DispatcherServlet 渲染了对应的视图之后执行。此方法主要用来进行资源清理。

```

2、在配置类@Configuration中配置

配置类需要实现接口 WebMvcConfigurer，然后重写addInterceptors

```text
package com.example.com_chenshuai.configuration;
 
import com.example.com_chenshuai.Controller.LoginInterceptor;
import com.example.com_chenshuai.Controller.MyFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
 
@Configuration
public class BaseConfiguration implements WebMvcConfigurer {
 
 
    // 将这个类交给spring管理。不使用注解的方式，因为使用注解，需要在源码上@Component
    // 使用方法，返回@Bean
    // FilterRegistrationBean 这个类，是第三方的，我们不能加@Component
    @Bean
    public FilterRegistrationBean<MyFilter> getFilter(){
        FilterRegistrationBean<MyFilter> filter = new FilterRegistrationBean<MyFilter>(new MyFilter());
        return filter;
 
    }
 
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(new LoginInterceptor());
        // 哪些请求被拦截
        // 注意，这里是两个*
        interceptorRegistration.addPathPatterns("/**");
        // 哪些请求不被拦截
//        interceptorRegistration.excludePathPatterns();
 
    }
}


在 addInterceptors方法中，addPathPatterns表示对哪些请求进行拦截。/**这里表示所有的请求。注意是2个*

        // 哪些请求被拦截
        // 注意，这里是两个*
        interceptorRegistration.addPathPatterns("/**");
excludePathPatterns表示哪些请求 ，不被拦截

        // 哪些请求不被拦截
//        interceptorRegistration.excludePathPatterns("");
```

### 三、拦截器&过滤器与spring aop的区别
过滤器是基于方法回调实现的。拦截器是基于反射实现的。

![img_8](/photo/img_8.png)

```text
作用域大小不一样。

过滤器Filter是在请求进入容器后，但在进入servlet之前进行预处理，请求结束是在servlet处理完以后。

拦截器 Interceptor 是在请求进入servlet后，在进入Controller之前进行预处理的，Controller 中渲染了对应的视图之后请求结束。

过滤器/拦截器与aop获取的信息不一样。过滤器/拦截器获取的是请求级别的信息，如url、传参、header、返回值等信息。

aop不能获取请求的参数等信息，和返回结果。aop能获取到调用的类，方法及方法参数。以及方法的返回值。

如果想通过方法进行拦截/过滤等处理，需要使用aop。如果想通过请求判断，需要使用过滤器。但是aop获取的是方法级别的信息，类、方法、方法的传参等等。

```

### 3.2添加aop
Spring AOP面向切面编程_做测试的喵酱的博客-CSDN博客

编写aop类

```java
package com.example.com_chenshuai.Controller;
 
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
 
@Aspect
@Slf4j
@Component
public class MyAspect {
 
    // 设置切点
    @Pointcut("execution(public * com.example.com_chenshuai.Controller.*.*(..) )")
    public void my(){
 
    }
 
    //
    @Before("my()")
    public void before(JoinPoint joinPoint){
        // 获得方法的参数
        Object[] args = joinPoint.getArgs();
 
        log.info("aspect before....");
    }
 
    @After("my()")
    public void after(){
        log.info("aspect after....");
    }
}
```

在@Configuration配置类中，添加注解@EnableAspectJAutoProxy就可以了。

```java

package com.example.com_chenshuai.configuration;
 
import com.example.com_chenshuai.Controller.LoginInterceptor;
import com.example.com_chenshuai.Controller.MyFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
 
@Configuration
@EnableAspectJAutoProxy
public class BaseConfiguration implements WebMvcConfigurer {
 
    // 将这个类交给spring管理。不使用注解的方式，因为使用注解，需要在源码上@Component
    // 使用方法，返回@Bean
    // FilterRegistrationBean 这个类，是第三方的，我们不能加@Component
    @Bean
    public FilterRegistrationBean<MyFilter> getFilter(){
        FilterRegistrationBean<MyFilter> filter = new FilterRegistrationBean<MyFilter>(new MyFilter());
        return filter;
 
    }
 
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(new LoginInterceptor());
        // 哪些请求被拦截
        // 注意，这里是两个*
        interceptorRegistration.addPathPatterns("/**");
        // 哪些请求不被拦截
//        interceptorRegistration.excludePathPatterns("");
 
    }
}
```


