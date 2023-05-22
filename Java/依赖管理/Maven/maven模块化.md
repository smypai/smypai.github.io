```java
Maven 模块化配置   <dependency> 相互引用 
Maven 包之间依赖  
直接/间接 
包冲突（相同包，最后配置为准）
Maven 选择使用包，排除方法 
自己的工程 <dependency>  <option> True/False
别人的工程<exclusion>
Maven 聚合 父工程 
// 父工程配置 PO.XML
<package>POM</package>
<modules>
    <module>子工程师</module>
</modules>

<dependencyManagment>
</dependencyManagment>  //管理可配置资源

//子工工程配置
<parent>
    <relativePath>  父工工程配置
子工程，不能指定父工程版本
Maven 属性 
定义 属性
<properties>
<spring.version>20.0.1</spring.version>
</properties>
使用
${psring.version}


```