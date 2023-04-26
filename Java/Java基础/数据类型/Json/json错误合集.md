***Q*** 使用Fastjson解析List对象时出现“$ref”:“$.data[0].children[0]”的问题

**A** 问题原因
- 原因：后台传过去的json数据用了阿里的fastjson转换，但是解析list中引用的数据时，jvm会自动将其处理为“循环引用”，因此，也就出现了问题{"r e f " : " ref":"ref":".data[0].children[0]"}，数据以引用的方式传给前台，前台却无法解析到那段引用的数据。
循环引用就是：当一个对象包含另一个对象时，fastjson就会把该对象解析成引用。

- 解决方案
> JSON.toJSONString(list,SerializerFeature.DisableCircularReferenceDetect)



**Q** 后台返回的Json为null的字段不显示的方法
- 方法一：在项目pom添加

```xml
<dependency>
<groupId>com.fasterxml.jackson.core</groupId>
<artifactId>jackson-databind</artifactId>
<version>2.7.9.1</version>
</dependency>
```
在实体类上添加注解, 将该标记放在属性上，如果该属性为NULL则不参与序列化 ；如果放在类上边,那对这个类的全部属性起作用。
> @JsonInclude(value=Include.NON_NULL)
> @JsonInclude(Include.NON_NULL)
具体取值有：
- Include.Include.ALWAYS 默认
- Include.NON_DEFAULT 属性为默认值不序列化
- Include.NON_EMPTY 属性为 空（“”） 或者为 NULL 都不序列化
- Include.NON_NULL 属性为NULL 不序列化
注：使用了该注解，那么在import时需要把一下两个类引入到源文件中
- import com.fasterxml.jackson.annotation.JsonInclude;
- import com.fasterxml.jackson.annotation.JsonInclude.Include;

- 方法二：
在实体类前，增加 注解
> @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
- JSON原来经过JACKSON转换以后为{"name"："name","sex":null}
- 加入注解后，结果为{"name"："name"} sex节点被去掉了

**Q** Json返回结果为null属性不显示解决方法

```java
返回时null属性不显示：String str = JSONObject.toJSONString(obj); 
返回为null属性显示：String str = JSONObject.toJSONString(obj,SerializerFeature.WriteMapNullValue); 
Fastjson的SerializerFeature序列化属性
QuoteFieldNames———-输出key时是否使用双引号,默认为true。
WriteMapNullValue——–是否输出值为null的字段,默认为false。
WriteNullNumberAsZero—-数值字段如果为null,输出为0,而非null。
WriteNullListAsEmpty—–List字段如果为null,输出为[],而非null。
WriteNullStringAsEmpty—字符类型字段如果为null,输出为”“,而非null。
WriteNullBooleanAsFalse–Boolean字段如果为null,输出为false,而非null。

@Bean
public
HttpMessageConverters fastJsonConfigure() {
    FastJsonHttpMessageConverter converter = new MyFastJsonHttpMessageConverter();
    converter.setSupportedMediaTypes(supportedMediaTypes);
    FastJsonConfig fastJsonConfig = new FastJsonConfig();
    fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteDateUseDateFormat);
    fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteMapNullValue);
    converter.setFastJsonConfig(fastJsonConfig);
    return new HttpMessageConverters(converter);
}
```