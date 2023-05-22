```

服务器版本
uname -r

#移除旧版本
sudo yum remove docker docker-client  docker-client-latest  docker-common  docker-latest   docker-latest-logrotate  docker-logrotate  docker-selinux docker-engine-selinux docker-engine

# 安装插件
sudo yum install -y yum-utils device-mapper-persistent-data lvm2
# 阿里镜像
sudo yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
#更新缓存
sudo yum makecache 
#安装系统
sudo yum -y install docker-ce
#启动Docker
sudo systemctl start docker
#随即启动
sudo systemctl enable docker
# docker 信息
docker info
docker -v 
docker version 

我是在guigu上学习的springboot的视频，有一些很难受的问题，这个问题已经让我难受一天多了，后来终于在一片文章中解决了，给大家分享一下：
docker 启动容器报错：Error response from daemon: oci runtime error: container_linux.go:247: starting container process caused "write parent: broken pipe"
其实原因还是，linux与docker版本的兼容性问题
第一步：通过uname -r命令查看你当前的内核版本
uname -r
第二步：使用 root 权限登录 Centos。确保 yum 包更新到最新。
sudo yum update
第三步：卸载旧版本(如果安装过旧版本的话)
sudo yum remove docker  docker-common docker-selinux docker-engine
第四步：安装需要的软件包， yum-util 提供yum-config-manager功能，另外两个是devicemapper驱动依赖的
sudo yum install -y yum-utils device-mapper-persistent-data lvm2
第五步：设置yum源
sudo yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
第六步：可以查看所有仓库中所有docker版本，并选择特定版本安装
yum list docker-ce --showduplicates | sort -r
第七步：安装docker
sudo yum install docker-ce
第八步：启动并加入开机启动
sudo systemctl start docker
sudo systemctl enable docker
第九步：验证安装是否成功(有client和service两部分表示docker安装启动都成功了)
 docker version
完美解决，如果还没有解决请联系做作者下方评论！


```