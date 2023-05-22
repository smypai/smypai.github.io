```java
一、安装
docker pull postgres
二、运行
docker run --name postgres2 -e POSTGRES_PASSWORD=pw123a456 -p 5434:5432 -d postgres:latest

解释：
run，创建并运行一个容器；
--name，指定创建的容器的名字；
-e POSTGRES_PASSWORD=password，设置环境变量，指定数据库的登录口令为password；
-p 54321:5432，端口映射将容器的5432端口映射到外部机器的54321端口；前面机器端口 后面容器端口
-d postgres:9.4，指定使用postgres:9.4作为镜像。
注意：
postgres镜像默认的用户名为postgres，
登陆口令为创建容器是指定的值。


三、常用命令
## Docker连接服务
docker exec -ti postgres1 bash

### 登录
su postgres

### 创建数据库
createdb mydb

###连接：
psql -h 192.168.1.25 -p 5432 -Upostgress -dtest









PostgreSQL 语法
https://www.runoob.com/postgresql/postgresql-syntax.html
https://www.yiibai.com/postgresql/postgresql_python.html



2. 使用docker-compose
新建文件夹: postgresql , 并进入，将以下内容保存为docker-compose.yml, 然后执行docker-compose up
version: '3'
services:
  mydb:
    image: postgres:9.4
    volumes:
      - db-data:/var/lib/postgresql/data
    environment:
      POSTGRES_USER: root
      POSTGRES_DB: mydb
      POSTGRES_PASSWORD: password
    ports:
      - "54321:5432"
    restart:
      always
volumes:
    db-data:
networks:
    postgresnetwork000:
      driver: 'local'
docker run --name postgres1 -e POSTGRES_PASSWORD=password -p 54321:5432 -d postgres:9.4




一、安装：
docker pull postgres:9.6
二、启动：
docker run --name postgres -e POSTGRES_PASSWORD=123456 -p 5432:5432 -d postgres:9.6
解释：
run，创建并运行一个容器；
--name，指定创建的容器的名字；
-e POSTGRES_PASSWORD=password，设置环境变量，指定数据库的登录口令为password；
-p 54321:5432，端口映射将容器的5432端口映射到外部机器的54321端口；
-d postgres:9.4，指定使用postgres:9.4作为镜像。
注意：
postgres镜像默认的用户名为postgres，
登录口令为创建容器是指定的值。
查看端口使用情况：
netstat -ano
netstat -aon|findstr "5432"
netstat -ano
netstat -aon|findstr "5432"
CREATE SEQUENCE public.user_info
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;
ALTER table public.user_info alter column id set DEFAULT nextval('public.user_info');
DROP SEQUENCE public.user_info
select setval('user_info_id_seq',1,false);


https://www.runoob.com/docker/docker-install-mongodb.html

#取最新版的 MongoDB 镜像
docker pull mongo:latest

#安装完成后，我们可以使用以下命令来运行 mongo 容器：
docker run -itd --name mongo -p 27017:27017 mongo --auth

#接着使用以下命令添加用户和设置密码，并且尝试连接。
$ docker exec -it mongo mongo admin

# 创建一个名为 admin，密码为 123456 的用户。
>  db.createUser({ user:'admin',pwd:'123456',roles:[ { role:'userAdminAnyDatabase', db: 'admin'}]});

# 尝试使用上面创建的用户信息进行连接。
> db.auth('admin', '123456')


MongoDB 教程
https://www.runoob.com/mongodb/mongodb-create-database.html
MongoDB 创建数据库
https://www.cnblogs.com/jinbuqi/p/11268443.html
```