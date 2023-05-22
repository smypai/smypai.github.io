##### 0. fedora28 mysql8

##### 1. 下载MySQL社区版

mysql-8.0.11-1.fc28.x86\_64.rpm-bundle.tar

地址：<https://dev.mysql.com/downloads/mysql/>

##### 2. 解压文件 mysql-8.0.11-1.fc28.x86\_64.rpm-bundle.tar

    tar xvf mysql-8.0.11-1.fc28.x86_64.rpm-bundle.tar

##### 3.安装MySQL社区版本

    rpm -ivh mysql-community-common-8.0.11-1.fc28.x86_64.rpm
    rpm -ivh mysql-community-libs-8.0.11-1.fc28.x86_64.rpm
    rpm -ivh mysql-community-client-8.0.11-1.fc28.x86_64.rpm

    yum install * #安装提示的缺少依赖

    rpm -ivh mysql-server-server-8.0.11-1.fc28.x86_64.rpm

##### 3.MySQL启动

    service mysqld start #运行MySQL服务
    systemctl enable mysqld.service #开机启动

#### 4.mysql配置文件地址

    cat /etc/my.cnf #linux 下
    mysql/bin/php.ini #window 下

    ## 配置文件内容
    [mysqld]
    skip-grant-tables #跳过密码验证

    datadir=/var/lib/mysql #数据路
    socket=/var/lib/mysql/mysql.sock

    log-error=/var/log/mysqld.log #错误日志
    pid-file=/var/run/mysqld/mysqld.pid #pid

#### 5.错误处理

##### 5.1 密码验证错，修改密码

    ERROR 1045 (28000): Access denied for user 'root'@'localhost' (using password: NO)

解决：

    1. 在mysql配置文件 /etc/my.cnf
        [mysqld]
        skip-grant-tables ##用来跳过密码验证的过程
        default_authentication_plugin=mysql_native_password   ##(这个是修改认证方式，8.0之前版本是默认的，8.0之后需要手动修改)

    2.重启MySQL
        service mysqld restart
    3.进入mysql
        mysql -uroot
    4.修改密码
        mysql> use mysql;
        mysql> flush privileges;
        mysql> ALTER USER "root"@"localhost" IDENTIFIED  BY "你的新密码";
        ## 密码要求 字符 字母大小写 数组 8位以上
        mysql> flush privileges;
        mysql> quit
    5.编辑my.cnf,去掉刚才添加的内容，然后重启MySQL。

##### 5.2新版本的mysql账号密码解锁机制不一致导致的

    The server requested authentication method unknown to the client

    ## 找到mysql配置文件my.cnf并加入
    default_authentication_plugin=mysql_native_password
    ## 变为原来的验证方式，然后从新创建用户并授权即可

