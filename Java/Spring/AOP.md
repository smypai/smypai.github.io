##  AOP简要说明
### （1）什么是AOP？
AOP为Aspect Oriented Programming的缩写，是面向切面编程，通过预编译方式和运行期动态代理实现程序功能的统一维护的一种技术。

### （2）AOP的五种通知方式
- 前置通知（Before）:在目标方法或者说连接点被调用前执行的通知；
- 后置通知（After）：指在某个连接点完成后执行的通知；
- 返回通知（After-returning）：指在某个连接点成功执行之后执行的通知；
- 异常通知（After-throwing）：指在方法抛出异常后执行的通知；
- 环绕通知（Around）：指包围一个连接点通知，在被通知的方法调用之前和之后执行自定义的方法。

1. 基于Springboot 引入依赖

```yaml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```

2. 源码

```java
import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Component
public class WebLogAspect {
    @Pointcut("execution(public * com.spring.wx.oauth.conntroller.*.*(..))")
    public void webLog(){

    }
    @Around("webLog()")
    public Object saveSysLog(ProceedingJoinPoint proceedingJoinPoint) {

        System.out.println("环绕通知开始。。。。。");

        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();

        String className = proceedingJoinPoint.getTarget().getClass().getName();
        String methodName = method.getName();

        System.out.println(className);
        System.out.println(methodName);
        System.out.println(className + "." + methodName);

        //请求的参数
        Object[] args = proceedingJoinPoint.getArgs();
        String params = JSON.toJSONString(args);
        System.out.println(params);

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        System.out.println("URL : " + request.getRequestURL().toString());
        System.out.println("HTTP_METHOD : " + request.getMethod());
        System.out.println("IP : " + request.getRemoteAddr());


        //记录时间
        long start = System.currentTimeMillis();
     Object result =null;
        try {
            result = proceedingJoinPoint.proceed();
            System.out.println(result.toString());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            System.out.println(throwable.getMessage());
        }

        Long time = System.currentTimeMillis() - start;
        System.out.println(time);
        System.out.println("环绕通知结束。。。。。");
        return result;
    }
}
```
