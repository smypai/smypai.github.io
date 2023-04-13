### SpringBoot中系统多环境配置
 1. 创建多环境配置文件
    - 我们在resources目录下分别创建配置文件  
      - application.properties为项目主配置文件，包含项目所需的所有公共配置。
      - application-dev.properties为开发环境配置文件，包含项目所需的单独配置。
      - application-test.properties为测试环境配置文件。
      - application-prod.properties为生产环境配置文件。
  
2. 修改配置文件 application.properties
```properties
server.port=8088
spring.profiles.active=prod
```