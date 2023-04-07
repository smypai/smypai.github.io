```xml
<!-- POM.xml -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
    <version>5.3.23</version>
</dependency>
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>javax.servlet-api</artifactId>
    <version>4.0.1</version>
    <scope>provided</scope>
</dependency>

<!-- maven web -->
<plugin>
    <groupId>org.apache.tomcat.maven</groupId>
    <artifactId>tomcat8-maven-plugin</artifactId>
    <version>3.0-r1655215</version>
    <configuration>
        <port>8088</port>
        <path>/</path>
    </configuration>
</plugin>

<plugin>
    <groupId>org.eclipse.jetty</groupId>
    <artifactId>jetty-maven-plugin</artifactId>
    <version>9.4.9.v20180320</version>
    <configuration>
        <env>dev</env>
        <httpConnector>
            <port>8089</port>
        </httpConnector>
    </configuration>
</plugin>

<!-- 资源仓 -->
<pluginRepositories>
    <pluginRepository>
        <id>alfresco-public</id>
        <url>https://artifacts.alfresco.com/nexus/content/groups/public</url>
        <snapshots>
            <enabled>false</enabled>
        </snapshots>
        <releases>
            <enabled>true</enabled>
        </releases>
    </pluginRepository>
    <pluginRepository>
        <id>alfresco-public-snapshots</id>
        <url>https://artifacts.alfresco.com/nexus/content/groups/public-snapshots</url>
        <snapshots>
            <enabled>true</enabled>
            <updatePolicy>daily</updatePolicy>
        </snapshots>
    </pluginRepository>
    <pluginRepository>
        <id>beardedgeeks-releases</id>
        <url>http://beardedgeeks.googlecode.com/svn/repository/releases</url>
    </pluginRepository>
</pluginRepositories>


<!-- web.xml -->
<web-app>
    <display-name>Archetype Created Web Application</display-name>
    <servlet>
        <servlet-name>dispatcherServlet</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:applicationContext.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>

    <servlet-mapping>
        <servlet-name>dispatcherServlet</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
</web-app>

<!-- applicationContext.xml -->
    <context:component-scan base-package="com.lenovo"/>
    <mvc:annotation-driven/>