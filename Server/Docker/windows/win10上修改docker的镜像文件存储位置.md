```
win10上修改docker的镜像文件存储位置
记住：修改的是docker从服务器上拉下来的镜像文件存储位置（本地），是不是镜像源地址（服务器）
首先
win10下的docker有可视化操作界面和命令行操作，下载了docker-ce.exe双击后就自己开始到结束自动完成，默认安装到了C盘目录下，也就是很多默认的参数或配置文件都在C盘目录下。pc win10虽然不想linux那么高内存、高存储，但是还是能启动两个docker容器的，考虑C盘是系统盘，docker使用过程中可能会拉取很多镜像文件，所以我们需要把docker默认的镜像文件存储地址修改一下。
修改
1.docker info看一下当前的


2.通过win10任务栏的docker gui界面来操作修改


选择settings进入操作界面，先勾选“advanced”，然后添加红框标注的一行（注意：路径是你想改变的路径）


填写完了，直接点击“apply”按钮，docker service会自动restarting。然后在看一下docker info


3.这样就证明生效了

看一下原理
不管是通过gui还是命令行，都是通过修改daemon.json来修改对应的参数配置：
1.Linux上配置文件的默认位置是 /etc/docker/daemon.json
2.Windows上配置文件的默认位置是 %programdata%\docker\config\daemon.json
win10上你想看到这个programdata目录需要勾选显示隐藏文件，就在C盘下，具体的参数及注意的参数细节可根据需求看一下官网文档【https://docs.docker.com/engine/reference/commandline/dockerd/#daemon-configuration-file】
推荐一下【linux修改镜像文件存储位置】的文档

```