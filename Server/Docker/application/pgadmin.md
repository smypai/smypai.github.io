```java
1. Docker 下载 pgadmin
docker pull dpage/pgadmin4


2. 运行镜像 ， 需要指定端口号，和账号密码
docker run -p 54320:80 -e "PGADMIN_DEFAULT_EMAIL=test@test" -e "PGADMIN_DEFAULT_PASSWORD=test@test" -d dpage/pgadmin4

```