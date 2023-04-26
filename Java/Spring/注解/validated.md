## Spring注解之@validated的使用

### 使用场景

```java
@RequestMapping(value="/url.json",method= {RequestMethod.POST})
@ResponseBody
@Transactional
public Result<?> xxmethod( @RequestBody @Validated  XoPO xoPo)     
throws ParseException, UnsupportedEncodingException {}

@data
public class XoPO{
@validated
private List<OrderPerson> personList;

    @NotNull
    @Size(max=32,message="code is null")
    private String code;

    @NotBlank
    @Size(max=32,message="product is null")
    private String product;
}
```

### 二 常用注解类型

```java
@AssertFalse 校验false
@AssertTrue 校验true
@DecimalMax(value=,inclusive=) 小于等于value，inclusive=true,是小于等于
@DecimalMin(value=,inclusive=) 与上类似
@Max(value=) 小于等于value
@Min(value=) 大于等于value
@NotNull  检查Null
@Past  检查日期
@Pattern(regex=,flag=)  正则
@Size(min=, max=)  字符串，集合，map限制大小
@Validate 对po实体类进行校验
四 @pathvariable的校验
public Result<?> xoById( @NotNull @NotBlank @Size(min=10,max=32)@PathVariable(value="accountId") String id) {}

Class XoService{
public xoMethon( @NotNull String id){
}
}
五 @validated和@valid不同点
在spring项目中，@validated和@valid功能很类似，都可以在controller层开启数据校验功能。
但是@validated和@valid又不尽相同。有以下不同点：
限制    说明
@Null    限制只能为null
@NotNull    限制必须不为null
@AssertFalse    限制必须为false
@AssertTrue    限制必须为true
@DecimalMax(value)    限制必须为一个不大于指定值的数字
@DecimalMin(value)    限制必须为一个不小于指定值的数字
@Digits(integer,fraction)    限制必须为一个小数，且整数部分的位数不能超过integer，小数部分的位数不能超过fraction
@Future    限制必须是一个将来的日期
@Max(value)    限制必须为一个不大于指定值的数字
@Min(value)    限制必须为一个不小于指定值的数字
@Past    限制必须是一个过去的日期
@Pattern(value)    限制必须符合指定的正则表达式
@Size(max,min)    限制字符长度必须在min到max之间
@Past    验证注解的元素值（日期类型）比当前时间早
@NotEmpty    验证注解的元素值不为null且不为空（字符串长度不为0、集合大小不为0)
@NotBlank    验证注解的元素值不为空（不为null、去除首位空格后长度为0），不同于@NotEmpty，@NotBlank只应用于字符串且在比较时会去除字符串的空格
@Email    验证注解的元素值是Email，也可以通过正则表达式和flag指定自定义的email格式

@Pattern(regexp="^[a-zA-Z0-9]+$",message="{account.username.space}")
@Size(min=3,max=20,message="{account.username.size}")
```