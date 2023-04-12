### SpringBoot统一接口返回和全局异常处理

#### 一、为什么要返回统一格式

1.1 返回字符串
1.2 返回实体类对象
1.3 返回异常
#### 二、定义返回标准格式

2.1 定义返回格式
2.2 定义返回对象
2.3 定义状态码
2.4 统一返回格式
#### 三、高级封装

3.1 ResponseBodyAdvice类介绍
3.2 用法说明
3.3 全局异常处理器
3.4 实现全局异常处理
3.5 统一返回结果处理类最终版

##### 一、为什么要返回统一格式
如果SpringBoot不使用统一返回格式，默认会有如下三种返回情况。
1. 返回字符串
```java
@GetMapping("/getUserName")
public String getUserName(){
    return "Hello";
}
```
调用接口返回结果：Hello

2. 返回实体类对象
```java
@GetMapping("/getUserName")
public User getUserName(){
    return new User("Hello",18);
}
// 调用接口返回结果：
{
  "name": "Hello",
  "age": "18"
}
```
3. 返回异常
```
@GetMapping("/getUserName")
public static String getUserName(){
    HashMap hashMap = Maps.newHashMap();
    return hashMap.get(0).toString();
}
// 模拟一个空指针异常，在不做任何异常处理的情况下，可以看下SpringBoot的默认返回结果：
{
    "timestamp": "2021-08-09T06:56:41.524+00:00",
    "status": 500,
    "error": "Internal Server Error",
    "path": "/sysUser/getUserName"
}
```
对于上面这几种情况，如果整个项目没有定义统一的返回格式，不同开发人员可能会定义不同的返回格式，这样会使前后端对接出现一些问题。
#### 二、定义返回标准格式
常见的解决方案是封装一个工具类，类中定义需要返回的字段信息，把需要返回前端的接口信息，通过该类进行封装，这样就可以解决返回格式不统一的现象了。
- 2.1 定义返回格式
一个标准的返回格式至少包含3部分：
code： 状态码message： 接口调用的提示信息data： 返回数据
- 2.2 定义返回对象
```java
public class Result<T> {
    private int code;
    private String message;
    private T data;
    public Result() {}
    
    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }
    
    /**
     * 成功
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<T>();
        result.setCode(ResultMsgEnum.SUCCESS.getCode());
        result.setMessage(ResultMsgEnum.SUCCESS.getMessage());
        result.setData(data);
        return result;
    }

    /**
     * 失败
     */
    public static <T> Result<T> error(int code, String message) {
        return new Result(code, message);
    }
}
```

- 2.3 定义状态码
```java
public enum ResultMsgEnum {
    SUCCESS(0, "成功"),
    FAIL(-1, "失败"),
    AUTH_ERROR(502, "授权失败!"),
    SERVER_BUSY(503, "服务器正忙，请稍后再试!"),
    DATABASE_OPERATION_FAILED(504, "数据库操作失败");
    private int code;
    private String message;
    ResultMsgEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
    public int getCode() {
        return this.code;
    }
    public String getMessage() {
        return this.message;
    }
}
```
- 2.4 统一返回格式
上面定义了数据返回格式和状态码，下面在接口中使用。
@GetMapping("/getUserName")
public Result getUserName(){
    return Result.success("Hello");
}
返回结果如下，可以看到返回结果与在Result中定义的参数类型相同。
{
    "code": 0,
    "message": "成功",
    "data": "Hello"
}
通过在Controller层调用 ResultData.success()对返回结果进行包装后返回给前端，虽然能够满足日常需求，但是当有大量的接口时，每一个接口中都使用Result.success()来包装返回信息就会增加很多重复代码。
#### 三、高级封装
下面将进行二次封装，只需要借助SpringBoot提供的ResponseBodyAdvice即可。
- 3.1 ResponseBodyAdvice类介绍
ResponseBodyAdvice： 该接口是SpringMVC 4.1提供的，它允许在 执行@ResponseBody后自定义返回数据，用来封装统一数据格式返回；拦截Controller方法的返回值，统一处理返回值/响应体，一般用来统一返回格式，加解密，签名等
先来看下 ResponseBodyAdvice的源码：
```java
public interface ResponseBodyAdvice<T> {
        /**
        * 是否支持advice功能
        * true 支持，false 不支持
        */
    boolean supports(MethodParameter var1, Class<? extends HttpMessageConverter<?>> var2);

      /**
        * 对返回的数据进行处理
        */
    @Nullable
    T beforeBodyWrite(@Nullable T var1, MethodParameter var2, MediaType var3, Class<? extends HttpMessageConverter<?>> var4, ServerHttpRequest var5, ServerHttpResponse var6);
}

```
@RestControllerAdvice： 该注解是Controller的增强版，可以全局捕获抛出的异常，全局数据绑定，全局数据预处理。
- 3.2 用法说明
新建ResponseAdvice类；
该类用于统一封装controller中接口的返回结果。
实现ResponseBodyAdvice接口，实现supports、beforeBodyWrite方法。
```java
@RestControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 是否开启功能 true：是 
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    /**
     * 处理返回结果
     */
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        //处理字符串类型数据，如果Controller返回String的话，SpringBoot是直接返回.
        if(o instanceof String){
            try {
                return objectMapper.writeValueAsString(Result.success(o));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return Result.success(o);
    }
}
```
测试后发现和直接使用Result返回的结果是一致的，这样就再也不需要在Controller层通过 ResultData.success()来进行转换了，直接返回原始数据格式，SpringBoot会自动帮我们进行封装。
但是这里还存在一个问题，在ResponseAdvice我们全部使用了Result.success(o)来处理结果，对于error类型的结果未做处理。那么当发生异常情况时会发生什么情况？测试结果如下：
```json
{
    "code": 0,
    "message": "成功",
    "data": {
        "timestamp": "2021-08-09T09:33:26.805+00:00",
        "status": 405,
        "error": "Method Not Allowed",
        "path": "/sysUser/getUserName"
    }
}
```
虽然格式上没有毛病，但是在code、data字段的具体数据上是不正确的，接口都报错了还返回操作成功的响应码。
- 3.3 全局异常处理器
遇到异常时，第一时间想到的应该是try…catch，不过这种方式会导致大量代码重复，维护困难等问题，这里不用手写try…catch，由全局异常处理器统一捕获、
对于自定义异常，只能通过全局异常处理器来处理，使用全局异常处理器最大的便利就是程序员在写代码时不再需要手写 try…catch了。
```java
@GetMapping("error1")
public void empty(){
    throw  new RuntimeException("自定义异常");
}
```

当我们引入Validator参数校验器的时候，参数校验不通过会抛出异常，此时是无法用 try…catch捕获的，只能使用全局异常处理器。

- 3.4 实现全局异常处理
使用的全局异常处理也是比较简单的。首先新增一个类，增加@RestControllerAdvice注解，该注解的作用上面已经介绍过。
@RestControllerAdvice

```java
public class CustomerExceptionHandler {
    
}
```

如果我们有想要拦截的异常类型，就新增一个方法，使用@ExceptionHandler注解修饰，注解参数为目标异常类型。
例如：controller中接口发生Exception异常时，就会进入到Execption方法中进行捕获，将杂乱的异常信息，转换成指定格式后交给ResponseAdvice方法进行统一格式封装并返回给前端。

```java
@RestControllerAdvice
@Slf4j
public class CustomerExceptionHandler {

    @ExceptionHandler(AuthException.class)
    public String ErrorHandler(AuthorizationException e) {
        log.error("没有通过权限验证！", e);
        return "没有通过权限验证！";
    }

    @ExceptionHandler(Exception.class)
    public Result Execption(Exception e) {
        log.error("未知异常！", e);
        return Result.error(ResultMsgEnum.SERVER_BUSY.getCode(),ResultMsgEnum.SERVER_BUSY.getMessage());
    }
}
```
再次调用接口getUserName查看返回结果，发现还是有一些问题，因为我们在CustomerExceptionHandler中已经将接口返回结果封装成Result类型，而代码执行到统一结果返回类ResponseAdvice时，又会结果再次封装，就导致出现了如下问题。
```json
{
    "code": 0,
    "message": "成功",
    "data": {
        "code": 503,
        "message": "服务器正忙，请稍后再试!",
        "data": null
    }
}
```
- 3.5 统一返回结果处理类最终版
解决上述问题非常简单，只要在beforeBodyWrite中增加一条判断即可。
```java
@RestControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 是否开启功能 true:开启
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    /**
     * 处理返回结果
     */
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        //处理字符串类型数据
        if(o instanceof String){
            try {
                return objectMapper.writeValueAsString(Result.success(o));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        //返回类型是否已经封装
        if(o instanceof Result){
            return o;
        }
        return Result.success(o);
    }
}
```
至此，SpringBoot统一接口返回和全局异常处理就封装好了，可直接引用到项目中。

### 全局异常捕获
```java
package com.william.servicebase.handler;
 
import com.william.commonutils.MyResponse;
import com.william.commonutils.MyResponseEnums;
import com.william.commonutils.MyRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
 
import javax.servlet.http.HttpServletRequest;
import java.net.ConnectException;
import java.sql.SQLException;
 
/**
 * @program: online_edu
 * @description: 我的全局异常处理类
 * @author: William Munch
 * @create: 2020-11-27 20:07
 **/
@RestControllerAdvice(annotations = {RestController.class, Controller.class})
public class MyGlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(MyGlobalExceptionHandler.class);
 
    /*
     *
     * 总是返回 200，是否成功在 body 里面用一个字段来指示
     * 全200还有若干实际细节的好处，打比方阿里云看到你500 404什么的就会直接砍掉你的body，
     * 你的client到server之间有太多东西会经手你的http请求，大量使用200之外的状态码各种稀奇古怪的问题
     *
     * 业务逻辑的异常都做处理，友好返回，status返回200（HttpStatus.OK）
     * 系统运行期间的异常（例如空指针异常）也做 "系统错误"的返回，状态码也是200
     * 系统运行期间的异常中，有些我们单独处理，如下文的数据库异常，（特定异常），其余的才会走上边那条路
     *
     *
     * MyBasicErrorController里把映射到/error的错误也做友好返回，但是status显示404，500等真实状态码（保持HttpStatus），
     * 这些如果body被阿里截取调也没关系
     *
     * 因为系统异常做了处理，本该会被最终映射到500的错误上，因为处理了，状态码改了200，再配上错误提示，直接返回了。
     * 要是不做系统异常处理，映射到500，再映射到/error错误，错误提示就不会显示"系统错误"了，而会显示"内部服务器错误"
     * 又因为MyBasicErrorController没改状态码，所以甚至可能会被截了错误提示，只剩下500状态码
     */
 
 
    /*
     * 系统出现异常（能捕获所有的）
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public MyResponse systemError(Exception e) {
        logger.error("occurs error when execute method ,message {}", e.getMessage());
        return new MyResponse<>(false,MyResponseEnums.SYSTEM_ERROR);
    }
 
    /**
     * 数据库操作出现异常（特定异常）
     */
    @ExceptionHandler(value = {SQLException.class, DataAccessException.class})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public MyResponse databaseError(Exception e) {
        logger.error("occurs error when execute method ,message {}", e.getMessage());
        return new MyResponse<>(false, MyResponseEnums.DATABASE_ERROR);
    }
 
    /**
     * 远程连接失败（特定异常）
     */
    @ExceptionHandler(value = {ConnectException.class})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public MyResponse connectError(Exception e) {
        logger.error("occurs error when execute method ,message {}", e.getMessage());
        return new MyResponse<>(false, MyResponseEnums.CONNECTION_ERROR);
    }
 
    /*
     * 自定义异常（用于捕获自己业务中抛出的）
     */
    @ExceptionHandler(value = {MyRuntimeException.class})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public  MyResponse myError(MyRuntimeException exception, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        logger.error("occurs error when execute url ={} ,message {}", requestURI, exception.getMsg());
        return new MyResponse<>(false, exception.getCode(), exception.getMsg());
    }
}

```






