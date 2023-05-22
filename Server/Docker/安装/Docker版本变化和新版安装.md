```
Docker版本变化和新版安装
Docker从1.13版本之后采用时间线的方式作为版本号，分为社区版CE和企业版EE。
社区版是免费提供给个人开发者和小型团体使用的，企业版会提供额外的收费服务，比如经过官方测试认证过的基础设施、容器、插件等。
社区版按照stable和edge两种方式发布，每个季度更新stable版本，如17.06，17.09；每个月份更新edge版本，如17.09，17.10。
官方文档：https://docs.docker.com/engine/installation/linux/docker-ce/centos/

 1 #1.配置仓库
 2 sudo yum install -y yum-utils device-mapper-persistent-data lvm2
 3 sudo yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
 4  
 5 #2.可以选择是否开启edge和test仓库
 6 sudo yum-config-manager --enable docker-ce-edge
 7 sudo yum-config-manager --enable docker-ce-test
 8 sudo yum-config-manager --disable docker-ce-edge
 9 sudo yum-config-manager --disable docker-ce-test
10  
11 #3.安装docker-ce
12 sudo yum install docker-ce     #由于repo中默认只开启stable仓库，故这里安装的是最新稳定版17.09
13  
14 #4.可以查看所有仓库中所有docker版本，并选择特定版本安装
15 yum list docker-ce --showduplicates | sort -r
16  
17 docker-ce.x86_64            17.09.0.ce-1.el7.centos            docker-ce-stable
18 docker-ce.x86_64            17.09.0.ce-1.el7.centos            @docker-ce-stable
19 docker-ce.x86_64            17.06.2.ce-1.el7.centos            docker-ce-stable
20 docker-ce.x86_64            17.06.1.ce-1.el7.centos            docker-ce-stable
21 docker-ce.x86_64            17.06.0.ce-1.el7.centos            docker-ce-stable
22 docker-ce.x86_64            17.03.2.ce-1.el7.centos            docker-ce-stable
23 docker-ce.x86_64            17.03.1.ce-1.el7.centos            docker-ce-stable
24 docker-ce.x86_64            17.03.0.ce-1.el7.centos            docker-ce-stable
25  
26 sudo yum install <FQPN>  例如：sudo yum install docker-ce-17.09.0.ce
27  
28 #5.启动并加入开机启动
29 sudo systemctl start docker
30 sudo systemctl enable docker
31  
32 #6.关闭docker-daemon
33 sudo systemctl stop docker
34 sudo systemctl disable docker
35  
36 #7.docker安装时默认创建了docker用户组，将普通用户加入docker用户组就可以不使用sudo来操作docker
37 sudo usermod -aG docker peter
38 #注：添加用户组之后要退出重新登录才会生效
39  
40 #8.运行hello-world镜像来测试是否安装成功
41  
42 docker run hello-world         #本地没有镜像时会自动从docker hub中下载
43  
44 #9.当出现Hello from Docker!即表示安装成功


```