


<plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <source>8</source>
                    <target>8</target>
                </configuration>
            </plugin>
————————————————
版权声明：本文为CSDN博主「骑马去买菜」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/tingding6650/article/details/122932480


2. 
3. 
4. 
5. 
6. maven-jar-plugin插件的配置信息来生成我们需要的指定依赖的可执行jar包。
- 如果没有依赖第三方包，可以用maven-jar-plugin插件，只是修改META-INFO下的MANIFEST.MF信息，指定运行jar包的main入口



<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-jar-plugin</artifactId>
            <version>2.3.1</version>
            <configuration>
                <archive>
                    <manifest>
                        <!--运行jar包时运行的主类，要求类全名-->
                        <mainClass>com.hafiz.Runner</mainClass>
                        <!-- 是否指定项目classpath下的依赖 -->
                        <addClasspath>true</addClasspath>
                        <!-- 指定依赖的时候声明前缀 -->
                        <classpathPrefix>./lib/</classpathPrefix>
                        <!--依赖是否使用带有时间戳的唯一版本号,如:xxx-1.3.0-20121225.012733.jar-->
                        <useUniqueVersions>false</useUniqueVersions>
                    </manifest>
                </archive>
            </configuration>
        </plugin>
    </plugins>
</build>

2. maven-dependency-plugin插件把当前项目的所有依赖放到target目录下的lib文件夹下
- 可以把第三方包下载到lib目录

<plugin>
   <groupId>org.apache.maven.plugins</groupId>
   <artifactId>maven-dependency-plugin</artifactId>
   <executions>
        <execution>
            <id>copy-dependencies</id>
            <phase>package</phase>
            <goals>
                <goal>copy-dependencies</goal>
            </goals>
            <configuration>
                <!-- 拷贝项目依赖包到lib/目录下 -->
                <outputDirectory>${project.build.directory}/lib</outputDirectory>
                <!-- 间接依赖也拷贝 -->
                <excludeTransitive>false</excludeTransitive>
                <!-- 带上版本号 -->
                <stripVersion>false</stripVersion>

                <overWriteReleases>false</overWriteReleases>
                <overWriteSnapshots>false</overWriteSnapshots>
                <overWriteIfNewer>true</overWriteIfNewer>

            </configuration>
        </execution>
   </executions>
</plugin>


3 三、把依赖也打进jar包：mainClass是jar包的main方法入口

<build>
        <plugins>
            <plugin>
                <artifactId>maven-assembly-plugin</artifactId>
                <configuration>
                    <archive>
                        <manifest>
                            <mainClass>com.wyz.Main</mainClass>
                        </manifest>
                    </archive>
                    <descriptorRefs>
                        <descriptorRef>jar-with-dependencies</descriptorRef>
                    </descriptorRefs>
                </configuration>
            </plugin>
        </plugins>
    </build>


<!--借助assembly插件完成包含项目依赖的打包-->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-assembly-plugin</artifactId>
                <configuration>
                    <appendAssemblyId>false</appendAssemblyId>
                    <descriptorRefs>
                        <!--设置将所有的依赖打进jar包中-->
                        <descriptorRef>jar-with-dependencies</descriptorRef>
                    </descriptorRefs>
                    <archive>
                        <manifest>
                            <!-- 此处指定main方法入口的class -->
                            <mainClass>com.chen.Application</mainClass>
                        </manifest>
                    </archive>
                    <!--指定打出的jar包输出目录-->
                    <outputDirectory>E:\IdeaWorkspace\PinYinHelper1\target</outputDirectory>
                </configuration>
                <executions>
                    <execution>
                        <id>make-assembly</id>
                        <!--将assembly插件绑定到package上，到时只需要双击package指令即可-->
                        <phase>package</phase>
                        <goals>
                            <goal>assembly</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
————————————————
版权声明：本文为CSDN博主「骑马去买菜」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/tingding6650/article/details/122932480