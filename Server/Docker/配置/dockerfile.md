```
dockerfile 介绍
Docker简介
Docker项目提供了构建在Linux内核功能之上，协同在一起的的高级工具。其目标是帮助开发和运维人员更容易地跨系统跨主机交付应用程序和他们的依赖。Docker通过Docker容器，一个安全的，基于轻量级容器的环境，来实现这个目标。这些容器由镜像创建，而镜像可以通过命令行手工创建或 者通过Dockerfile自动创建。
Dockerfile
Dockerfile是由一系列命令和参数构成的脚本，这些命令应用于基础镜像并最终创建一个新的镜像。它们简化了从头到尾的流程并极大的简化了部署工作。Dockerfile从FROM命令开始，紧接着跟随者各种方法，命令和参数。其产出为一个新的可以用于创建容器的镜像。
Dockerfile 语法
在我们深入讨论Dockerfile之前，让我们快速过一下Dockerfile的语法和它们的意义。
什么是语法？
非常简单，在编程中，语法意味着一个调用命令，输入参数去让应用执行程序的文法结构。这些语法被规则或明或暗的约束。程序员遵循语法规范以和计算机 交互。如果一段程序语法不正确，计算机将无法识别。Dockerfile使用简单的，清楚的和干净的语法结构，极为易于使用。这些语法可以自我释义，支持注释。
Dockerfile 语法示例
Dockerfile语法由两部分构成，注释和命令+参数
# Line blocks used for commenting
command argument argument..
一个简单的例子：
# Print "Hello docker!"
RUN echo"Hello docker!"
Dockerfile 命令
Dockerfile有十几条命令可用于构建镜像，下文将简略介绍这些命令。
ADD
ADD命令有两个参数，源和目标。它的基本作用是从源系统的文件系统上复制文件到目标容器的文件系统。如果源是一个URL，那该URL的内容将被下载并复制到容器中。
# Usage: ADD [source directory or URL] [destination directory]
ADD/my_app_folder/my_app_folder
CMD
和RUN命令相似，CMD可以用于执行特定的命令。和RUN不同的是，这些命令不是在镜像构建的过程中执行的，而是在用镜像构建容器后被调用。
# Usage 1: CMD application "argument", "argument", ..
CMD"echo""Hello docker!"
ENTRYPOINT
配置容器启动后执行的命令，并且不可被dockerrun提供的参数覆盖。
每个Dockerfile中只能有一个ENTRYPOINT，当指定多个时，只有最后一个起效。
ENTRYPOINT 帮助你配置一个容器使之可执行化，如果你结合CMD命令和ENTRYPOINT命令，你可以从CMD命令中移除“application”而仅仅保留参数，参数将传递给ENTRYPOINT命令。
# Usage: ENTRYPOINT application "argument", "argument", ..
# Remember: arguments are optional. They can be provided by CMD
# or during the creation of a container.
ENTRYPOINT echo
# Usage example with CMD:
# Arguments set with CMD can be overridden during *run*
CMD"Hello docker!"
ENTRYPOINT echo
ENV
ENV命令用于设置环境变量。这些变量以”key=value”的形式存在，并可以在容器内被脚本或者程序调用。这个机制给在容器中运行应用带来了极大的便利。
# Usage: ENV key value
ENV SERVER_WORKS4

EXPOSE
EXPOSE用来指定端口，使容器内的应用可以通过端口和外界交互。
# Usage: EXPOSE [port]
EXPOSE8080
FROM
FROM命令可能是最重要的Dockerfile命令。改命令定义了使用哪个基础镜像启动构建流程。基础镜像可以为任意镜 像。如果基础镜像没有被发现，Docker将试图从Docker image index来查找该镜像。FROM命令必须是Dockerfile的首个命令。
# Usage: FROM [image name]
FROM ubuntu
MAINTAINER
我建议这个命令放在Dockerfile的起始部分，虽然理论上它可以放置于Dockerfile的任意位置。这个命令用于声明作者，并应该放在FROM的后面。
# Usage: MAINTAINER [name]
MAINTAINER authors_name
RUN
RUN命令是Dockerfile执行命令的核心部分。它接受命令作为参数并用于创建镜像。不像CMD命令，RUN命令用于创建镜像（在之前commit的层之上形成新的层）。
# Usage: RUN [command]
RUN aptitude install-y riak
USER
USER命令用于设置运行容器的UID。
# Usage: USER [UID]
USER751
VOLUME
VOLUME命令用于让你的容器访问宿主机上的目录。
# Usage: VOLUME ["/dir_1", "/dir_2" ..]
VOLUME["/my_files"]
WORKDIR
WORKDIR命令用于设置CMD指明的命令的运行目录。
# Usage: WORKDIR /path
WORKDIR~/
如何使用Dockerfiles
使用Dockerfiles和手工使用Docker Daemon运行命令一样简单。脚本运行后输出为新的镜像ID。
# Build an image using the Dockerfile at current location
# Example: sudo docker build -t [name] .
sudo docker build-t my_mongodb.
Dockerfile 示例一：创建一个MongoDB的镜像
在这部分中，我们讲一步一步创建一个Dockfile，这个Dockerfile可用于构建MongoDB镜像进而构建MongoDB容器。
创建一个Dockerfile
使用nano文本编辑器，让我们创建Dockerfile。
sudo nanoDockerfile
定义文件和它的目的
让阅读者明确Dockerfile的目的永远是必要的。为此，我们通常从注释开始写Dockerfile。
############################################################
# Dockerfile to build MongoDB container images
# Based on Ubuntu
############################################################
设置基础镜像
# Set the base image to Ubuntu
FROM ubuntu
定义作者
# File Author / Maintainer
MAINTAINERExampleMcAuthor
设置命令与参数下载MongoDB
################## BEGIN INSTALLATION ######################
# Install MongoDB Following the Instructions at MongoDB Docs
# Ref: http://docs.mongodb.org/manual/tutorial/install-mongodb-on-ubuntu/
# Add the package verification key
RUN apt-key adv--keyserver hkp://keyserver.ubuntu.com:80 --recv 7F0CEB10
# Add MongoDB to the repository sources list
RUN echo'deb http://downloads-distro.mongodb.org/repo/ubuntu-upstart dist 10gen'|tee/etc/apt/sources.list.d/mongodb.list
# Update the repository sources list once more
RUN apt-getupdate
# Install MongoDB package (.deb)
RUN apt-getinstall-y mongodb-10gen
# Create the default data directory
RUN mkdir-p/data/db
##################### INSTALLATION END #####################

设置MongoDB端口
# Expose the default port
EXPOSE27017
# Default port to execute the entrypoint (MongoDB)
CMD["--port 27017"]
# Set default container command
ENTRYPOINT usr/bin/mongod
保存Dockerfile。
构建镜像
使用上述的Dockerfile，我们已经可以开始构建MongoDB镜像
sudo docker build-t my_mongodb.
Dockerfile 示例二：创建一个Nginx的镜像
Nginx简述
Nginx是一个高性能的 HTTP 和 反向代理 服务器。它因为它的轻量级，易用，易于扩展而流行于业界。基于优良的架构设计，它能够比之前的类似软件处理更多的请求。它也可以用来提供静态文件服务，比如图片，脚本和CSS。
和上个例子一样，我们还是从基础镜像开始，运用FROM命令和MAINTAINER命令
############################################################
# Dockerfile to build Nginx Installed Containers
# Based on Ubuntu
############################################################
# Set the base image to Ubuntu
FROM ubuntu
# File Author / Maintainer
MAINTAINERMaintanerName
安装Nginx
# Install Nginx
# Add application repository URL to the default sources
RUN echo"deb http://archive.ubuntu.com/ubuntu/ raring main universe">>/etc/apt/sources.list
# Update the repository
RUN apt-getupdate
# Install necessary tools
RUN apt-getinstall-y nano wget dialog net-tools
# Download and Install Nginx
RUN apt-getinstall-y nginx
Bootstrapping
安装Nginx后，我们需要配置Nginx并且替换掉默认的配置文件
# Remove the default Nginx configuration file
RUN rm-v/etc/nginx/nginx.conf
# Copy a configuration file from the current directory
ADD nginx.conf/etc/nginx/
# Append "daemon off;" to the beginning of the configuration
RUN echo"daemon off;">>/etc/nginx/nginx.conf
# Expose ports
EXPOSE80
# Set the default command to execute
# when creating a new container
CMD service nginx start
保存 dockfile。
使用Dockerfile自动构建Nginx容器
因为我们命令Docker用当前目录的Nginx的配置文件替换默认的配置文件，我们要保证这个新的配置文件存在。在Dockerfile存在的目录下，创建nginx.conf：
sudo nano nginx.conf
然后用下述内容替换原有内容：
worker_processes1;
events{worker_connections1024;}
http{
  sendfile on;
  server{
    listen80;
    location/{
       proxy_pass http://httpstat.us/;
       proxy_set_header X-Real-IP $remote_addr;
    }
  }
}
让我们保存nginx.conf。之后我们就可以用Dockerfile和配置文件来构建镜像。
```