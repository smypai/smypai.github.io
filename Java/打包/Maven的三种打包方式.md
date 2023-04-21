### 01 引言
Maven有三种打包方式，分别为：

- assembly：自定义的打包结构，也可以定制依赖项等;
- jar：默认的打包方式，用来打普通的project JAR包；
- shade：用来打可执行jar包，也就是所谓的fat JAR包。

### 02 assembly打包
- 2.1 介绍
插件：使用maven-assembly-plugin插件 。

我们日常使用比较多的是maven-assembly-plugin插件，例如：大数据项目中往往有很多shell脚本、SQL脚本、.properties及.xml配置项等，采用assembly插件可以让输出的结构清晰而标准化。

- 2.2 使用
首先在pom文件添加以下内容：

```yaml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-assembly-plugin</artifactId>
            <version>${maven-assembly-plugin.version}<version>
            <executions>
                <execution>
                    <id>make-assembly</id>
                    <!-- 绑定到package生命周期 -->
                    <phase>package</phase>
                    <goals>
                        <!-- 只运行一次 -->
                        <goal>single</goal>
                    </goals>
                </execution>
            </executions>
            <configuration>
                <!-- 配置描述符文件 -->
                <descriptor>src/main/assembly/assembly.xml</descriptor>
                <!-- 也可以使用Maven预配置的描述符
                <descriptorRefs>
                    <descriptorRef>jar-with-dependencies</descriptorRef>
                </descriptorRefs> -->
            </configuration>
        </plugin>
    </plugins>
</build>

```

然后编写描述符文件：


```xml

<assembly>
    <id>assembly</id>

    <formats>
        <format>tar.gz</format>
    </formats>

    <includeBaseDirectory>true</includeBaseDirectory>

    <fileSets>
        <fileSet>
            <directory>src/main/bin</directory>
            <includes>
                <include>*.sh</include>
            </includes>
            <outputDirectory>bin</outputDirectory>
            <fileMode>0755</fileMode>
        </fileSet>
        <fileSet>
            <directory>src/main/conf</directory>
            <outputDirectory>conf</outputDirectory>
        </fileSet>
        <fileSet>
            <directory>src/main/sql</directory>
            <includes>
                <include>*.sql</include>
            </includes>
            <outputDirectory>sql</outputDirectory>
        </fileSet>
        <fileSet>
            <directory>target/classes/</directory>
            <includes>
                <include>*.properties</include>
                <include>*.xml</include>
                <include>*.txt</include>
            </includes>
            <outputDirectory>conf</outputDirectory>
        </fileSet>
    </fileSets>

    <files>
        <file>
            <source>target/${project.artifactId}-${project.version}.jar</source>
            <outputDirectory>.</outputDirectory>
        </file>
    </files>

    <dependencySets>
        <dependencySet>
            <unpack>false</unpack>
            <scope>runtime</scope>
            <outputDirectory>lib</outputDirectory>
        </dependencySet>
    </dependencySets>
</assembly>



```

![img_09](/photo/img_9.png)

### 03 jar打包
- 3.1 介绍   
插件：使用maven-jar-plugin插件

默认的打包方式，用来打普通的project JAR包

- 3.2 使用

```xml
<build>
   <plugins>
       <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-jar-plugin</artifactId>
            <version>3.1.0</version>
            <configuration>
                <archive>
                    <manifest>
                        <!-- 指定入口函数 -->                             <mainClass>com.xx.main.HelloMavenJar</mainClass>
                        <!-- 是否添加依赖的jar路径配置 -->
                        <addClasspath>true</addClasspath>
                        <!-- 依赖的jar包存放未知，和生成的jar放在同一级目录下 -->
                        <classpathPrefix>lib/</classpathPrefix>
                    </manifest>
                </archive>
                <!-- 不打包com.yh.excludes下面的所有类 -->
                <excludes>com/xx/excludes/*</excludes>
            </configuration>
        </plugin>
    </plugins>
</build>

```

- 3.3 字段解析   
上面3.2的配置使用这个 jar包的时候就需要在它同一级的创建一个lib目录来存放。

可以使用includes或excludes选择的打包某些内容。


### 04 shade打包
- 4.1 介绍    
插件：使用maven-shade-plugin插件

maven-shade-plugin提供了两大基本功能：

- 将依赖的jar包打包到当前jar包（常规打包是不会将所依赖jar包打进来的）；
- 对依赖的jar包进行重命名（用于类的隔离）；

- 4.2 使用

```xml
<build>
  <plugins>
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-shade-plugin</artifactId>
      <version>3.1.1</version>
      <configuration>
        <!-- put your configurations here -->
      </configuration>
      <executions>
        <execution>
          <phase>package</phase>
          <goals>
            <goal>shade</goal>
          </goals>
        </execution>
      </executions>
    </plugin>
  </plugins>
</build>

```

4.3 字段解析
参考：https://blog.csdn.net/yangguosb/article/details/80619481

可以使用excludes排除部分jar包，例如：

```xml
<excludes>
  <exclude>jmock:*</exclude>
  <exclude>*:xml-apis</exclude>
  <exclude>org.apache.maven:lib:tests</exclude>
  <exclude>log4j:log4j:jar:</exclude>
</excludes>

```

将依赖jar包内部资源添加或排除，例如：

```xml
<excludes>
  <exclude>META-INF/*.SF</exclude>
  <exclude>META-INF/*.DSA</exclude>
  <exclude>META-INF/*.RSA</exclude>
</excludes>

```


自动将所有不使用的类排除，例如：

```xml


<configuration>
  <minimizeJar>true</minimizeJar>
</configuration>
```

将依赖的类重命名并打包进来 （隔离方案），例如将“org.codehaus.plexus.util”重命名为“org.shaded.plexus.util"：

```xml

<relocations>
  <relocation>
    <pattern>org.codehaus.plexus.util</pattern>
    <shadedPattern>org.shaded.plexus.util</shadedPattern>
    <excludes>
      <exclude>org.codehaus.plexus.util.xml.Xpp3Dom</exclude>
      <exclude>org.codehaus.plexus.util.xml.pull.*</exclude>
    </excludes>
  </relocation>
</relocations>
```

修改包的后缀名:

```xml
<configuration>
   <shadedArtifactAttached>true</shadedArtifactAttached>
   <shadedClassifierName>jackofall</shadedClassifierName> <!-- Any name that makes sense -->
 </configuration>
```

- 05 文末
具体细节可以参考：

- shade打包官方教程：https://maven.apache.org/plugins/maven-shade-plugin/examples/attached-artifact.html；
- jar打包官方教程： https://maven.apache.org/shared/maven-archiver/examples/classpath.html；
- assembly打包官方教程：https://maven.apache.org/plugins/maven-assembly-plugin/examples/single/filtering-some-distribution-files.html
