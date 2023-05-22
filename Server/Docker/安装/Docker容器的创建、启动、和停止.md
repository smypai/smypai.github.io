```
1、容器是独立运行的一个或一组应用，及他们的运行环境。容器是Docker中的一个重要的概念。
2、docker容器的启动有三种方式
a.交互方式，基于镜像新建容器并启动
例如我们可以启动一个容器，打印出当前的日历表
[root@rocketmq-nameserver4 ~]# docker run my/python:v1 cal ##my/python:v1为镜像名和标签

我们还可以通过指定参数，启动一个bash交互终端。
[root@rocketmq-nameserver4 ~]# docker run -it my/python:v1 /bin/bash


参数-t让Docker分配一个伪终端并绑定在容器的标准输入上，-i让容器的标准输入保持打开。
使用docker run命令来启动容器，docker在后台运行的标准操作包括
1.检查本地是否存在指定的镜像，不存在则从公有仓库下载
2.使用镜像创建并启动容器
3.分配一个文件系统，并在只读的镜像层外面挂载一层可读可写层
4.从宿主主机配置的网桥接口中桥接一个虚拟接口道容器中去
5.从地址池分配一个ip地址给容器
6.执行用户指定的应用程序
7.执行完毕之后容器被终止


my/sinatra:v2基于training/sinatra镜像进行修改后的镜像，training/sinatra为公有仓库上的镜像。
b、短暂方式，直接将一个已经终止的容器启动运行起来
可以使用docker start命令，直接将一个已经终止的容器启动运行起来。
[root@rocketmq-nameserver4 ~]# docker run my/python:v1 /bin/echo hello test
hello test
命令执行完，控制台会打印"hello test"，container就终止了，不过并没有消失，
可以用"docker ps -n 5 "看一下最新前5个的container，第一个就是刚刚执行过的container，可以再次执行一遍：docker start container_id
不过这次控制台看不到”hello test”了，只能看到ID，用logs命令才能看得到：docker logs container_id。
可以看到两个”hello test”了，因为这个container运行了两次。

c、daemon方式，守护态运行
即让软件作为长时间服务运行，这就是SAAS啊！
例如我们启动centos后台容器，每隔一秒打印当天的日历。
$ docker run -d centos /bin/sh -c "while true;do echo hello docker;sleep 1;done"
启动之后，我们使用docker ps -n 5查看容器的信息
要查看启动的centos容器中的输出，可以使用如下方式：
$ docker logs $CONTAINER_ID ##在container外面查看它的输出
$ docker attach $CONTAINER_ID ##连接上容器实时查看：
3、终止容器
使用docker stop $CONTAINER_ID来终止一个运行中的容器。并且可以使用docker ps -a来看终止状态的容器。

终止状态的容器，可以使用docker start来重新启动。

使用docker restart命令来重启一个容器。
```