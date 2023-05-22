### POM.XML配置国内镜像源.md

- pom.xml 文件添加 

```xml
<repositories>
    <repository>
        <id>spring</id>
        <url>https://maven.aliyun.com/repository/spring</url>
        <releases>
            <enabled>true</enabled>
        </releases>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
    </repository>
</repositories>
```



