### java有哪些版本？

*   1.JAVA SE：它是JAVA的标准版，是整个JAVA的基础和核心，这是我们主要学习的一个部分，也是JAVAEE和JAVAME技术的基础，主要用于开发桌面应用程序。学会后可以做一些简单的桌面应用如：扫雷，连连看等。
*   2.JAVA ME：它是JAVA的微缩版，主要应用于嵌入式开发，比如手机程序的开发。目前来说就业范围不是很广，在一些城市可能相对的不好找工作。
*   3.JAVA EE：也叫JAVA的企业版，它提供了企业级应用开发的完整解决方案，比如开发网站，还有企业的一些应用系统，是JAVA技术应用最广泛的领域。主要还是偏向于WEB的开发，而JAVA EE的基础就是JAVA SE，所以我们在学习JAVA SE的时候，基础一定要打好，因为这是最基本的，也是最核心的。

###### 说得更简单片面一点：

*   Java SE 是做电脑上运行的软件。
*   Java EE 是用来做网站的-（我们常见的JSP技术）
*   Java ME 是做手机软件的。

###### java即jdk，jdk和jvm有几个公司在做:

1.  oracle jdk，oracle维护，有限免费，个人使用足够，部分版本更新开始收费。这是我们常用的，很多初学就以为只有这一种了，并不是
2.  ibm jdk ibm维护，商业jdk
3.  zing jdk zing公司维护，商业jdk
4.  open jdk 开源社区维护
5.  open j9 开源社区维护
6.  Alibaba Dragonwell，阿里巴巴版jdk，由阿里主导维护，基于openjdk，做了很多更新

##### JDK 下载：

* https://jdk.java.net/  
* https://openjdk.org/
* https://mirrors.tuna.tsinghua.edu.cn/Adoptium/
* https://wiki.openjdk.org/display/jdk8u/Main
*   oracle jdk <https://www.oracle.com/java/technologies/javase-downloads.html>
*   OpenJDK <https://openjdk.java.net/>
*   <https://jdk.java.net/archive/>
*   <https://www.oracle.com/java/technologies/downloads/#jdk17-windows>
*   JDK For Windows <https://developers.redhat.com/products/openjdk/download?sc_cid=701f2000000RWTnAAO>
*   Oralc JDK <https://www.oracle.com/technetwork/java/javase/downloads/index.html>
* <https://www.openlogic.com/openjdk-downloads?field_java_parent_version_target_id=416&field_operating_system_target_id=436&field_architecture_target_id=391&field_java_package_target_id=396>

##### fedora Install JDK

dnf install java-11-openjdk               java
dnf install java-11-openjdk-level      javac

###### Community：

*   社区版，免费，但是功能有限制，Android Studio就是基于这个版本定制的。
    <http://idea-intellij.com/intellij-community/>
*   Ultimate：
    终极版，收费，功能无限制。
    <http://idea-intellij.com/intellij-ultimate/>
*   EAP：
    终极版的免费版，免费，功能无限制，但是每隔30天要重装一次。
    <http://idea-intellij.com/intellij-eap/>

#### install jdk  env

```
变量名：JAVA_HOME
变量值：C:\Program Files (x86)\Java\jdk1.8.0_91        // 要根据自己的实际路径配置
变量名：CLASSPATH
变量值：.;%JAVA_HOME%\lib\dt.jar;%JAVA_HOME%\lib\tools.jar;         //记得前面有个"."
变量名：Path
变量值：%JAVA_HOME%\bin;%JAVA_HOME%\jre\bin;
```

