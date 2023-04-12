## SpringBoot默认访问不存在资源就会出现404
1. properties 文件配置

```xml
#出现错误时, 直接抛出异常
spring.mvc.throw-exception-if-no-handler-found=true
#不要为我们工程中的资源文件建立映射
spring.web.resources.add-mappings=false
```
2. 全局处理异常：

```java
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 指定出现什么异常来执行这个方法
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public R error(Exception e){
        e.printStackTrace();
        if (e instanceof SQLIntegrityConstraintViolationException){
            return R.error().message("数据库主键冲突，如是本人，请联系管理员");
        }else if (e instanceof org.springframework.web.servlet.NoHandlerFoundException){
            return R.error().message("找不到资源，/(ㄒoㄒ)/~~");
        }
        return R.error().message("内部服务器错误，/(ㄒoㄒ)/~~!");
    }

    /**
     * 自定义异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(DujiaoshouException.class)
    @ResponseBody
    public R error(DujiaoshouException e){
        log.error(e.getMessage());
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}

/**
 * 资源映射路径，只能配置一个映射
 */
@Configuration
public class MyWebMvcConfigurer extends WebMvcConfigurerAdapter {
    @Value("${upload.file.path}")
    private String uploadPath;

    @Value("${upload.image.path}")
    private String uploadImagePath;

    @Value("${upload.crash.exception.file.path}")
    private String uploadCrashExceptionFilePath;

    @Value("${upload.apk.path}")
    private String uploadApkPath;

    /**
     * 将D:\\upload下的文件映射到当前项目/upload/下
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/static/");

        //addResourceHandler()里配置需要映射的文件夹，此处代表映射文件夹user下的所有资源。
        //addResourceLocations()配置文件夹在系统中的路径，使用绝对路径，格式为“file:你的路径/” 后面的 / 必须加上，否则映射失效
//        registry.addResourceHandler("/upload/**").addResourceLocations("file:D:/dujiaoshouapp/");
//        registry.addResourceHandler("/upload/**").addResourceLocations("file:"+uploadPath);
        registry.addResourceHandler("/upload/images/**").addResourceLocations("file:"+uploadImagePath);
        registry.addResourceHandler("/upload/crash/**").addResourceLocations("file:"+uploadCrashExceptionFilePath);
        registry.addResourceHandler("/upload/apk/**").addResourceLocations("file:"+uploadApkPath);
    }
}


```