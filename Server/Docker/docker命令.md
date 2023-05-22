```
#Docker 允许你在容器内运行应用程序， 使用docker run命令来在容器内运行一个应用程序
docker run ubuntu:15.10 /bin/echo "Hello world"
docker run -i -t ubuntu:15.10 /bin/bash
docker run -d ubuntu:15.10 /bin/sh -c "while true; do echo hello world; sleep 1; done"
# 载入镜像
docker pull training/webapp 
docker run -d -P training/webapp python app.py
#使用docker ps来查看我们正在运行的容器
docker ps 
#通过-p参数来设置不一样的端口
docker run -d -p 5000:5000 training/webapp python app.py 
#网络端口的快捷方式
docker port bf08b7f2cd89 
 #可以查看容器内部的标准输出
docker logs -f bf08b7f2cd89
 #来查看容器内部运行的进程
docker top wizardly_chandrasekhar
#来查看 Docker 的底层信息
docker inspect wizardly_chandrasekhar 
#停止 WEB 应用容器
docker stop wizardly_chandrasekhar 
#已经停止的容器，我们可以使用命令 docker start 来启动
docker start wizardly_chandrasekhar 
#查询最后一次创建的容器
docker ps -l  
#命令来删除不需要的容器
docker rm wizardly_chandrasekhar  
#我们可以使用docker images来列出本地主机上的镜像
docker images
#获取一个新的镜像
docker pull ubuntu:13.10
#查找镜像
 docker search httpd
#设置镜像标签
docker tag 860c279d2fec runoob/centos:dev


docker search 镜像名称
NAME为镜像名称
DESCRIPTION为镜像描述
STARS为社区关注度（与GitHub开源社区的STARS同理）
OFFICIAL表示是否为官方发布（一般适合我们开发使用）
AUTOMATED表示是否自动化操作

docker pull 镜像名称

docker images 查看本机器的Docker镜像命令
REPOSITORY表示镜像名称
TAG表示镜像版本（如果在下载时没有明确指定版本，它会优先下载最新版本latest）
IMAGE ID表示这个镜像的ID号唯一标识，在后面创建容器会用到
CREATED表示最近更新时间，SIZE表示大小

docker run --name 参数1 -p 参数2 -v 宿主机与容器交互的目录文件 -d
--name tomcat 表示上一步安装的镜像名称
-p 8080:8080 表示将容器的8080port映射到本机的8080port端口
-v /usr/test:/usr/local/tomcat/webapps/test 表示将本机下的usr目录下的test文件夹映射到容器tomcat的webapps文件夹下
-d tomcat 表示在后台守护进程的方式启动（不进入后台启动），指定上一步安装的镜像名称

docker ps -a : 查看已经在运行的所有容器

docker part 容器ID : 查看容器的内外端口映射

docker logs 容器ID : 查看容器的日志

docker stop 容器ID : 关闭容器（未删除）

docker start 容器ID : 开启容器

docker rm 容器ID : 删除对应的容器（注：删除之前必须要stop，否则删不掉的）

docker images : 查看机器上所有的镜像

docker rmi 镜像ID : 删除对应的镜像（注：删除之前必须停止或删除它创建的所有容器）

docker save 镜像ID -o 文件: 将指定的镜像打包（例：docker save ef6a7c98d192 -o /home/wuyongfei/springboot-demo.tar）

docker load -i 文件夹名称（自定义） 将指定的镜像移植到另外一台机器上在Docker中进行加载，再按照上面的步骤三查看镜像，启动容器就实现了Docker镜像的移植（例：docker load -i springboot-demo.tar）

Docker 命令

环境是Centos 7.2安装的docker
1 要删除docker部署的应用服务，就要先停止所有的container，这样才能够删除其中的images：
  docker stop $(docker ps -a -q) 
2 还要删除所有的container
 docker rm $(docker ps -a -q)
3 然后删除docker部署的应用
  docker rmi3458979c7744（后为服务的ID号）
4 查看服务的ID号
  docker images



不以物喜不以己悲
生活不会突变，你要做的只是耐心和积累。人这一辈子没法做太多的事情，所以每一件都要做得精彩绝伦。你的时间有限，做喜欢的事情会令人愉悦，所以跟随自己的本心。
1.docker 命令
1-1.查看 docker 版本
docker version

1-2.显示 docker 系统的信息
docker info

1-3.搜索 docker 镜像
docker search <镜像名>

1-4.获取容器或镜像的元数据
 	docker inspect <容器名或ID或镜像名>

1-5.登录 registry server
docker login <server>
# 示例：
	docker login daocloud.io

docker login 子命令：
-p, --password：密码
-u, --username：用户名
2.docker image 命令
2-1.查看本地镜像
docker image ls

docker image ls 子命令：
-a, --all：显示所有镜像
-q, --quiet：只显示镜像ID
--no-trunc：不截断输出
2-2.下载镜像
通常情况下，描述一个镜像需要包括“名称+标签”信息，如果不指定标签信息，默认会选择latest标签，这会下载仓库中最新版本的镜像。更严格地讲，镜像的仓库名称中还应该添加仓库地址（即registry,注册服务器）作为前缀，只是我们默认使用的是Docker Hub服务，该前缀可以忽略。
docker image pull <镜像名>[:标签(往往用来表示版本信息)]

docker image pull ubuntu:14.04
docker image pull hub.c.163.com/public/ubuntu:14.04

docker image pull 子命令：
-a,--all-tags=true|false：是否获取仓库中所有版本镜像，默认为否。
2-3.发布镜像
docker image push <镜像名>[:标签(往往用来表示版本信息)]

2-4.删除一个或多个镜像
# 删除一个镜像
docker image rm <镜像 ID>

# 删除多个镜像(中间用空格隔开)
docker image rm <镜像 ID> <镜像 ID> <...>

# 批量删除本地所有镜像
docker image rm $(docker image ls -a -q)

docker image rm 子命令：
-f, --force：强制删除
2-5.给镜像打上标签
docker image tag <镜像名> <标签名>

2-6.显示一个镜像的历史
docker image history <镜像名>

2-7.保存镜像
docker image save <镜像名> -o <文件名>

2-8.加载镜像
docker image load -i <文件名>
# 或者
docker image load <文件名>

2-9.根据 Dockerfile 构建出一个镜像
docker image build -t <镜像名>[:标签(往往用来表示版本信息)] <Dockerfile>

3.docker container 命令
3-1.查看本地容器
docker container ls

docker container ls 子命令：
-a, --all：显示所有容器，包括没有在运行的
-q, --quiet：只显示容器ID
--no-trunc：不截断输出
3-2.创建一个新的容器
docker container create <镜像名或ID> <命令>

docker container create 子命令：
--name <容器名>：给容器起个名字
-i, --interactive：表示让容器的标准输入打开
-t, --tty：表示分配一个伪终端
-p, --publish list：将容器的端口发布到主机
--rm：容器退出时自动删除
3-3.创建并启动一个新的容器
docker container run <镜像名或ID> <命令>

docker container run 子命令：
--name <容器名>：给容器起个名字
-d, --detach：在后台运行容器
-i, --interactive：表示让容器的标准输入打开
-t, --tty：表示分配一个伪终端
-p, --publish list：将容器的端口发布到主机
--rm：容器退出时自动删除
# 示例
docker container run --name mytomcat -d -it -p 8080:8080 tomcat

3-4.进入运行中的容器
docker container exec -it <容器名或ID> /bin/bash

3-5.停止一个或多个运行中的容器
# 停止一个运行中的容器
docker container stop <容器名或ID>

# 停止多个运行中的容器(中间用空格隔开)
docker container stop <容器名或ID> <容器名或ID> <...>

3-6.杀死一个或多个运行中的容器
# 停止一个运行中的容器
docker container kill <容器名或ID>

# 停止多个运行中的容器(中间用空格隔开)
docker container kill <容器名或ID> <容器名或ID> <...>

3-7.暂停一个或多个容器内的所有进程
# 停止一个运行中的容器
docker container pause <容器名或ID>

# 停止多个运行中的容器(中间用空格隔开)
docker container pause <容器名或ID> <容器名或ID> <...>

3-8.取消暂停一个或多个容器内的所有进程
# 停止一个运行中的容器
docker container unpause <容器名或ID>

# 停止多个运行中的容器(中间用空格隔开)
docker container unpause <容器名或ID> <容器名或ID> <...>

3-9.启动一个或多个停止的容器
# 启动一个停止的容器
docker container start <容器名或ID>

# 启动多个停止的容器(中间用空格隔开)
docker container start <容器名或ID> <容器名或ID> <...>

docker container start 子命令：
-a, --attach：附加标准输出和标准错误
-i, --interactive：表示让容器的标准输入打开
3-10.重启一个或多个容器
# 重启一个的容器
docker container restart <容器名或ID>

# 重启多个容器(中间用空格隔开)
docker container restart <容器名或ID> <容器名或ID> <...>

3-11.删除一个或多个容器
# 删除一个容器
docker container rm <容器名或ID>

# 删除多个容器(中间用空格隔开)
docker container rm <容器名或ID> <容器名或ID> <...>

# 批量删除本地所有容器
docker container rm $(docker container ls -a -q)

docker container rm 子命令：
-f, --force：强制删除
3-12.从一个容器中取日志
docker container logs <容器名或ID>

3-13.列出一个容器里面被改变的文件或者目录
list列表会显示出三种事件，A 增加的，D 删除的，C 被改变的
docker container diff <容器名或ID>

3-14.显示一个运行的容器里面的进程信息
docker container top <容器名或ID>

3-15.从容器里面拷贝文件或目录到本地一个路径
docker container cp <容器名或ID>:/container_path to_path

4.有图有真相(docker event state)


5.参考资料
docker简单使用 - CSDN博客
https://blog.csdn.net/tongzhenggang/article/details/54288351
Docker入门 - 278108678 - 博客园
http://www.cnblogs.com/sunyujun/p/9181069.html
Docker学习笔记(2)--Docker常用命令 - Go2Shell - CSDN博客
https://blog.csdn.net/we_shell/article/details/38368137
Docker的学习--命令使用详解 - 疯狂的原始人 - 博客园
http://www.cnblogs.com/CraryPrimitiveMan/p/4657835.html
Docker - 简书
https://www.jianshu.com/p/1ee42024b97c

1、删除容器实例
docker rm 容器ID或容器名
1. 先 docker ps -a 寻找已经停止运行的实例
2.docker rm 59ec 删除实例
3.docker ps -a 查看实例已经删除

2、删除镜像
docker rmi 容器ID或容器名
1. 首先 docker images 查看所有镜像
2. 然后 docker rmi fe200 删除镜像
3.再次通过 docker images 查看镜像 发现已经删除

3、测试删除顽固镜像
service docker stop
rm -rf /var/lib/docker
service docker start

```