### Authorization

```java
package com.len.config;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@Slf4j
public class AuthAspect {

    @Value("${smbforbaiying.authorization}")
    private String authToken;

    @Pointcut("execution(public * com.len.controller.*.*(..))")
    public void pointCut() {
    }

    @Pointcut("execution(public * com.len.controller.HelloController.*(..))")
    public void hell() {
    }

    @Pointcut("execution(public * com.len.controller.CallbackController.*(..))")
    public void callback() {
    }

    @Around("pointCut() && !hell() && !callback()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        // 此处无需判断 requestAttributes 是否为空
        HttpServletRequest request = requestAttributes.getRequest();

        // 获取请求头中的 Authorization 信息
        String authorization = request.getHeader("Authorization");
        if (authToken.equals(authorization)) {
            // 通过了判断，可以认为是携带着登录信息进行请求的，则【允许目标方法继续执行】！！！！！！
            return joinPoint.proceed();
        }

        String message = "Error Authorization Code, please contact the administrator";
        if(StringUtils.isEmpty(authorization)) message = "Authorization is not null";

        Map<String,String> data = new HashMap<>();
        data.put("errcode","400");
        data.put("message",message) ;

        return JSONObject.toJSONString(data);
    }

}

```