### 推荐一些常用镜像及国内maven仓库

```
阿里云的：http://maven.aliyun.com/nexus/content/groups/public/


配置方法：在setting.xml中配置
<mirrors>
    <mirror>
      <id>alimaven</id>
      <name>aliyun maven</name>
      <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
      <mirrorOf>central</mirrorOf>
    </mirror>
</mirrors>
私服nexus工具使用 http://www.sonatype.org/nexus/  

开源中国的： http://maven.oschina.net/content/groups/public/

Linux系统镜像下载

1、企业站

网易：http://mirrors.163.com/
阿里云：http://mirrors.aliyun.com/
淘宝：http://npm.taobao.org/
2、教育站

上海交通大学：http://ftp.sjtu.edu.cn/html/resources.xml（部分移动运营商出口状况不佳，无法访问）
华中科技大学：http://mirror.hust.edu.cn/（当前已用容量估计：4.83T）
清华大学：http://mirrors.tuna.tsinghua.edu.cn/（当前已用容量估计：9.8T）

北京理工大学：http://mirror.bit.edu.cn/web/
兰州大学：http://mirror.lzu.edu.cn/

中国科技大学： http://mirrors.ustc.edu.cn/（当前已用容量估计：21.32T）

大连东软信息学院：http://mirrors.neusoft.edu.cn/（当前已用容量估计：2.5T）

东北大学：http://mirror.neu.edu.cn/

大连理工大学：http://mirror.dlut.edu.cn/
北京交通大学：http://mirror.bjtu.edu.cn/cn/

浙江大学：http://mirrors.zju.edu.cn/

重庆大学：http://mirrors.cqu.edu.cn/（当前已用容量估计：3.93T）

西北农林科技大学：http://mirrors.nwsuaf.edu.cn/（只做CentOS镜像，当前已用容量估计：140GB） 

中国台湾淡江大学: http://ftp.tku.edu.tw/Linux/

（三）、其他
首都在线科技股份有限公司：http://mirrors.yun-idc.com/

常州贝特康姆软件技术有限公司：http://centos.bitcomm.cn/（只做CentOS镜像，当前已用容量估计：140GB）

公云PubYun（母公司为贝特康姆）：http://mirrors.pubyun.com/

Linux运维派：http://mirrors.skyshe.cn/（使用阿里云服务器，界面使用浙江大学的模板，首页维护，内容可访问）

中国互联网络信息中心：http://mirrors.cnnic.cn/（只做Apache镜像，当前已用容量估计：120GB）

Fayea工作室：http://apache.fayea.com/（只做Apache镜像，当前已用容量估计：120GB）

（四）、Ubuntu

阿里云：http://mirrors.aliyun.com/ubuntu-releases/

网易：http://mirrors.163.com/ubuntu-releases/

搜狐：http://mirrors.sohu.com/ubuntu-releases/

首都在线科技股份有限公司：http://mirrors.yun-idc.com/ubuntu-releases/

（五）、CentOS

网易：http://mirrors.163.com/centos/

搜狐：http://mirrors.sohu.com/centos/

阿里云：http://mirrors.aliyun.com/centos/

（六）、Apache

中国互联网络信息中心：http://mirrors.cnnic.cn/apache/
华中科技大学： http://mirrors.hust.edu.cn/apache/

北京理工大学：http://mirror.bit.edu.cn/apache/

TOMCAT全版本：https://archive.apache.org/dist/tomcat/

（七）、MySQL

北京理工大学：http://mirror.bit.edu.cn/mysql/Downloads/

中国电信天翼云：http://mirrors.ctyun.cn/Mysql/

搜狐镜像源：http://mirrors.sohu.com/mysql/

（八）、PostgreSQL

浙江大学：http://mirrors.zju.edu.cn/postgresql/

（九）、MariaDB

中国电信天翼云：http://mirrors.ctyun.cn/MariaDB/

（十）、VideoLAN

大连东软信息学院：http://mirrors.neusoft.edu.cn/videolan/
中国科技大学：http://mirrors.ustc.edu.cn/videolan-ftp/

（十一）、PHP

php全版本：https://secure.php.net/releases/

（十二）、Eclipse

中国科技大学：http://mirrors.ustc.edu.cn/eclipse/

中国科学院：http://mirrors.opencas.cn/eclipse/

东北大学：http://ftp.neu.edu.cn/mirrors/eclipse/，http://mirror.neu.edu.cn/eclipse/

（十三）、Android SDK

中国科学院：http://mirrors.opencas.ac.cn/android/repository/

南洋理工学院：http://mirror.nyist.edu.cn/android/repository/

中国科学院：http://mirrors.opencas.cn/android/repository/

腾讯：http://android-mirror.bugly.qq.com:8080/android/repository/（限流，不推荐）

（十四）、Xcode

腾讯：http://android-mirror.bugly.qq.com:8080/Xcode/（从7.2之后不再更新，建议直接从官网下载）

（十五）、容器

lxc国内镜像源：https://mirrors.tuna.tsinghua.edu.cn/lxc-images/

官方镜像列表状态地址

1CentOS：http://mirror-status.centos.org/#cn

2Archlinux https://www.archlinux.org/mirrors/status/

3Ubuntu：https://launchpad.net/ubuntu/+cdmirrors

4Debian：http://mirror.debian.org/status.html

5Fedora Linux/Fedora EPEL：https://admin.fedoraproject.org/mirrormanager/mirrors

6Apache：http://www.apache.org/mirrors/#cn

7Cygwin：https://www.cygwin.com/mirrors.html