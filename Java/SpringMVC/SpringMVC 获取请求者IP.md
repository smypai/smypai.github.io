```java

public class Api{
    
    @Autowired
    protected HttpServletRequest request;
    //获取类

    @PostMapping("ip")
    public String IpAddress() {
        //获取方法
        return request.getRemoteAddr();
    }
}

```