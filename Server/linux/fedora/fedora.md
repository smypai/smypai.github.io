```
开机启动
chkconfig --list 启动列表
chkconfig --add mysqld 添加启动项
chkconfig mysqld on 启动启动项
chkconfig list 启动列表
chkconfig add mysqld 添加启动项
chkconfig mysqld on 启动启动项
/sbin/chkconfig

程序启动
service mysqld status
service mysqld start
service mysqld stop
service mysqld reload 
systemctl start mysqld.service
systemctl stop mysqld.service
systemctl reload mysqld.service 
/sbin/service

包管理
yum install php
dnf install php
dnf search php


Yum  
yum list
rpm-q php
rpm-ql php
rpm -q ( or --query) options环境环境
参数：
pkg1 ... pkgN ：查询已安装的软件包
详细选项
-p (or ``-'') 查询软件包的文件
-f 查询属于哪个软件包
-a 查询所有安装的软件包
--whatprovides 查询提供了 功能的软件包
-g 查询属于 组的软件包
--whatrequires 查询所有需要 功能的软件包
信息选项
显示软件包的全部标识
-i 显示软件包的概要信息
-l 显示软件包中的文件列表
-c 显示配置文件列表
-d 显示文档文件列表
-s 显示软件包中文件列表并显示每个文件的状态
--scripts 显示安装、卸载、校验脚本
--queryformat (or --qf) 以用户指定的方式显示查询信息
--dump 显示每个文件的所有已校验信息
--provides 显示软件包提供的功能
--requires (or -R) 显示软件包所需的功能
通用选项
-v 显示附加信息
-vv 显示调试信息
--root 让RPM将指定的路径做为"根目录"，这样预安装程序和后安装程序都会安装到这个目录下
--rcfile 设置rpmrc文件为
--dbpath 设置RPM 资料库存所在的路径为



```