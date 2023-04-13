## Gradle生成Jar的两种方式

### 1.不可执行的Jar

- 方式1：在控制台执行gradle  build
                   
- 方式2鼠标选择Gradle->Tasks->build->jar
                        

### 2.可执行Jar
#### 方式1：将依赖Jar放入lib文件夹(一般项目和SpringBoot都可以)
```yaml
task copyDependencies(type: Copy) {
    from configurations.runtime
    into 'build/libs/lib'
}
jar.dependsOn(copyDependencies)
jar {
    manifest {
        attributes "Implementation-Title": project.name
        attributes "Implementation-Version": '1.0.0'
        attributes 'Main-Class': 'Test'
    }
    if (!configurations.runtime.isEmpty()) {
        manifest.attributes('Class-Path': '. lib/' + configurations.runtime.collect { it.name }.join(' lib/'))
    }
}

// 或者 

jar {
    String someString = ''
    configurations.runtime.each {someString = someString + " lib//"+it.name}
    manifest {
        attributes 'Main-Class': 'Program'
        attributes 'Class-Path': someString
    }
}
//清除上次的编译过的文件
task clearPj(type:Delete){
    delete 'build','target'
}
task copyJar(type:Copy){
    from configurations.runtime
    into ('build/libs/lib')
}
//把JAR复制到目标目录
task release(type: Copy,dependsOn: [build,copyJar]) {
// from 'conf'
// into ('build/libs/eachend/conf') // 目标位置
}
```
展开右侧的Gradle侧边栏，找到在other下可以看到clearPj,copyJar以及release，双击release即可打jar包（它默认会执行copyJar，就是把所有的第三方依赖包放到lib目录下）

#### 方式2：依赖包会和代码生成一个jar包（非SpringBoot项目）
- 然后鼠标执行Gradle->Tasks->build->jar

- 或者控制台gradle build

- 或者控制台gradle jar
```yaml


jar {
    archivesBaseName = 'MiniRPC'//基本的文件名
    archiveVersion = '0.0.3' //版本
    manifest { //配置jar文件的manifest
        attributes(
                "Manifest-Version": 1.0,
                'Main-Class': 'com.mirs.minirpc.App' //指定main方法所在的文件
        )
    }
 //打包依赖包
    from {
        (configurations.runtimeClasspath).collect {
            it.isDirectory() ? it : zipTree(it)
        }
    }
}
```

#### 方式3：依赖包会和代码生成一个jar包（SpringBoot项目）
在build.gradle增加
```yaml


plugins {
  id "org.springframework.boot" version "2.5.2"
}
或者

buildscript {
  repositories {
    maven { url 'http://maven.aliyun.com/nexus/content/groups/public/' 
    maven { url 'http://maven.aliyun.com/nexus/content/repositories/jcenter' }
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    classpath "org.springframework.boot:spring-boot-gradle-plugin:2.5.2"
  }
}
 
apply plugin: "org.springframework.boot"
```
- 然后鼠标执行Task->build->bootJar
- 或者控制台执行gradle bootJar
————————————————   
原文链接：https://blog.csdn.net/myli92/article/details/114992691