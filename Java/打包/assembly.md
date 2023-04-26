### 1 导入pom 

```xml
 <build>
        <!--包名-->
        <finalName>${project.name}</finalName>
        <!--dubbo-->
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-dependency-plugin</artifactId>
                <version>3.2.0</version>
                <executions>
                    <execution>
                        <id>unpack</id>
                        <phase>prepare-package</phase>
                        <goals>
                            <goal>unpack</goal>
                        </goals>
                        <configuration>
                            <artifactItems>
                                <artifactItem>
                                    <groupId>com.alibaba</groupId>
                                    <artifactId>dubbo</artifactId>
                                    <version>2.5.3</version>
                                    <outputDirectory>${project.build.directory}/dubbo</outputDirectory>
                                    <includes>META-INF/assembly/**</includes>
                                </artifactItem>
                            </artifactItems>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
            <!--自定义格式打包插件-->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-assembly-plugin</artifactId>
                <version>2.3</version>
                <executions>
                    <execution>
                        <id>make-assembly</id>
                        <phase>package</phase>
                        <goals>
                            <goal>single</goal>
                        </goals>
                    </execution>
                </executions>
                <!--获取配置文件-->
                <configuration>
                    <descriptors>
                        <descriptor>src/main/assembly/assembly.xml</descriptor>
                    </descriptors>
                    <descriptorRefs>
                        <descriptorRef>bin</descriptorRef>
                    </descriptorRefs>
                </configuration>
            </plugin>
        </plugins>
    </build>
```

### 2. 创建配置xml

src/main/assembly/assemblu.xml

```xml
<assembly>

    <id>assembly</id>
    <!--打包文件格式-->
    <formats>
        <format>zip</format>
        <format>dir</format>
        <format>tar.gz</format>
    </formats>

    <includeBaseDirectory>false</includeBaseDirectory>

    <dependencySets>
        <dependencySet>
            <outputDirectory>lib</outputDirectory>
            <useProjectArtifact>true</useProjectArtifact>
            <useTransitiveDependencies>true</useTransitiveDependencies>
            <unpack>false</unpack>
            <useStrictFiltering>true</useStrictFiltering>
            <useTransitiveFiltering>true</useTransitiveFiltering>
        </dependencySet>
    </dependencySets>

    <fileSets>

        <fileSet>
            <directory>${basedir}/src/main/resources</directory>
            <includes>
                <include>*</include>
            </includes>
            <fileMode>0777</fileMode>
            <outputDirectory>conf/META-INF/spring</outputDirectory>
            <lineEnding>unix</lineEnding>
        </fileSet>

        <fileSet>
            <directory>${project.build.directory}/dubbo/META-INF/assembly/bin</directory>
            <includes>
                <include>*</include>
            </includes>
            <fileMode>0777</fileMode>
            <outputDirectory>bin</outputDirectory>
            <lineEnding>unix</lineEnding>
        </fileSet>

    </fileSets>

</assembly>
```