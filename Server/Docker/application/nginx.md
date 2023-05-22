```

docker pull nginx 

docker run --name nginx-test -p 8080:80 -d nginx

第一步 pull nginx
命令：docker pull nginx

第二步 启动nginx
命令：docker run --name nginx -p 80:80 -d nginx

第三步 查看成果
1）命令查看是否启动，命令：docker ps
2）网页访问，浏览器输入IP地址回车，就可以看到 “Welcome to nginx!”

附加项：
原因：虽然咱们能正常启动nginx，但配置得在容器中进行，这样的话太麻烦了，所以把配置文件给映射出来，方便配置与管理
第一步 本地创建管理目录
命令：
mkdir -p /data/nginx
mkdir -p /data/nginx/www
mkdir -p /data/nginx/conf
mkdir -p /data/nginx/logs

第二步 将容器中的相应文件copy到刚创建的管理目录中
docker cp 67e:/etc/nginx/nginx.conf /data/nginx/
docker cp 67e:/etc/nginx/conf.d /data/nginx/conf/
docker cp 67e:/usr/share/nginx/html/ /data/nginx/www/
docker cp 67e:/var/log/nginx/ /data/nginx/logs/
注：docker cp 67e 中的 "67e" 为容器ID前缀，只要唯一就好了

第三步 停止并移除容器
命令：
停止容器：docker stop 67e
移除容器：docker rm 67e

第四步 再次启动容器并作目录挂载(也相当于共享)
命令：
docker run --name nginx -p 80:80 
-v /data/nginx/nginx.conf:/etc/nginx/nginx.conf
-v /data/nginx/www/:/usr/share/nginx/html/
-v /data/nginx/logs/:/var/log/nginx/
-v /data/nginx/conf/:/etc/nginx/conf.d
--privileged=true -d nginx
注：为了好看所以做了换行，执行的时候还是需要改成一行，每行一个空格隔开就可以了

部署就完成了！！！
彩蛋（配置相关）：
1、在location 中 echo "hello Nginx！" 访问可以直接输出文字
例：
location / {
    echo "hello Nginx！"
}

2、location匹配规则：
1）最低级别匹配规则：
location / {
    echo "hello Nginx！"
}
2）最高级别匹配规则：
location /user {
    echo "hello user.hmtl"
}
3）其它级别匹配规则：
location ^~ /user {
    echo "hello user.hmtl"
}
location ~^ /user {
    echo "hello user.hmtl"
}
location ~ ^/[a-z] {
    echo "hello user.hmtl"
}
location ~ ^/\a {
    echo "hello user.hmtl"
}

3、反向代理细节：
location /user {
    proxy_pass http://ip;
}
location /order/ {
    proxy_pass http://ip/;
}
访问结果：
http://ip/user/xx...
http://ip/xx...

4、负载均衡配置
upstream order {
    server 192.168.5.18:8080 weight=1;
    server 192.168.5.18:8081 weight=1;
}
server{
    location /order/ {
        proxy_pass http://order/;
    }
}
注：weight=1，配置的为权重，值越高权重越高

分类: linux 服务器环境搭建


目录
Nginx - 配置及反向代理
1 Nginx 安装与启动
1.1 什么是Nginx
1.2 安装 Nginx
2 Nginx 反向代理
2.1 反向代理
2.1.1 什么是反向代理
2.1.2 准备工作
2.1.3 配置反向代理
2.2 负载均衡
2.2.1 什么是负载均衡
2.2.2 准备工作
2.2.3 配置负载均衡

--------------------------------------------------------------------------------
1 Nginx 安装与启动
1.1 什么是Nginx
Nginx是一款高性能的http服务器 / 反向代理服务器及电子邮件(IMAP/POP3)代理服务器。
Nginx应用场景：
http服务器。Nginx是一个http服务器可以独立提供http服务，可以做网⻚静态服务器。
虚拟主机。可以实现在一台服务器虚拟出多个网站。例如个人网站使用的虚拟主机。
反向代理，负载均衡。当网站的访问量达到一定程度后，单台服务器不能满足用户的请求时，需要用多台服务器集群可以使用nginx做反向代理。并且多台服务器可以平均分担负载，不会因为某台服务器负载高宕机而某台服务器闲置的情况。
1.2 安装 Nginx
这里在CentOS 7环境下使用docker安装nginx
搜索nginx镜像
docker search nginx
1
拉取nginx镜像
docker pull nginx
1
创建容器，设置端口映射、目录映射
# 在/root目录下创建nginx目录用于存储nginx数据信息
mkdir ~/nginx
cd ~/nginx
mkdir conf
cd conf
# 在~/nginx/conf/下创建nginx.conf文件,粘贴下面内容
vim nginx.conf
1234567
user nginx;
worker_processes 1 ;

error_log /var/log/nginx/error.log warn;
pid             /var/run/nginx.pid;

events {
        worker_connections 1024 ;
}

http {
        include /etc/nginx/mime.types;
        default_type application/octet-stream;
        
        log_format main '$remote_addr - $remote_user [$time_local] "$request" '
                                        '$status $body_bytes_sent "$http_referer" '
                                        '"$http_user_agent" "$http_x_forwarded_for"';
    
    access_log /var/log/nginx/access.log main;
    
    sendfile on;
        #tcp_nopush on;
        
        keepalive_timeout 65;
        
        #gzip on;
        include /etc/nginx/conf.d/*.conf;
}
12345678910111213141516171819202122232425262728
~/nginx/conf.d/80.conf
server {
    listen 80; # 监听的端口
    server_name localhost; # 域名或ip
    location / { # 访问路径配置
            root /usr/share/nginx/html;# 根目录
            index index.html index.htm; # 默认首⻚
        }
        error_page 500 502 503 504 /50x.html; # 错误⻚面
        location = /50x.html {
                root html;
        }
}
123456789101112
运行
docker run -id --name=c_nginx \
-p 80 :80 \
-p 81 :81 \
-p 82 :82 \
-v $PWD/conf/nginx.conf:/etc/nginx/nginx.conf \
-v $PWD/conf.d:/etc/nginx/conf.d \
-v $PWD/logs:/var/log/nginx \
-v $PWD/html:/usr/share/nginx/html \
nginx
123456789
参数说明：
-p 80:80 ：将容器的 80 端口映射到宿主机的 80 端口。
-v $PWD/conf/nginx.conf:/etc/nginx/nginx.conf ：将主机当前目录下的/conf/nginx.conf 挂载到容器的 :/etc/nginx/nginx.conf - 配置目录
-v $PWD/logs:/var/log/nginx ：将主机当前目录下的 logs 目录挂载到容器的/var/log/nginx - 日志目录
123
使用外部机器访问nginx
注意：此处需要在/nginx/html/目录下，自行创建一个index.html文件用以测试

在这里插入图片描述
--------------------------------------------------------------------------------
2 Nginx 反向代理
2.1 反向代理
2.1.1 什么是反向代理
反向代理(Reverse Proxy)方式是指以代理服务器来接受internet上的连接请求，然后将请求转发给内部网络上的服务器，并将从服务器上得到的结果返回给internet上请求连接的客户端，此时代理服务器对外就表现为一个反向代理服务器。
首先先理解正向代理，如下图：

在这里插入图片描述
正向代理是针对你的客户端，而反向代理是针对服务器，如下图

在这里插入图片描述
反向代理：外部的网络请求通过Nginx代理服务器访问企业内部的不同的虚拟主机
2.1.2 准备工作
在docker中部署tomcat
搜索tomcat镜像
docker search tomcat
1
拉取tomcat镜像
docker pull tomcat
1
创建容器，设置端口映射、目录映射
# 在/root目录下创建tomcat目录用于存储tomcat数据信息
mkdir ~/tomcat
cd ~/tomcat

docker run -id --name=c_tomcat \
-p 8080 :8080 \
-v $PWD:/usr/local/tomcat/webapps \
tomcat
12345678
参数说明：
-p 8080:8080： 将容器的 8080 端口映射到主机的 8080 端口
-v $PWD:/usr/local/tomcat/webapps： 将主机中当前目录挂载到容器的webapps
12
使用外部机器访问tomcat
2.1.3 配置反向代理
~/nginx/config.d/proxy.conf
upstream murphy-tomcat {
        server 192.168.2.190:8080;
}

server {
        listen 80; # 监听的端口
        server_name http://murphy-tomcat; # 域名或ip
        location / { # 访问路径配置
                # root index;# 根目录
                proxy_pass http://murphy-tomcat;
                index index.html index.htm; # 默认首⻚
        }
}
12345678910111213
注意：此处的域名需要在主机的hosts文件中进行注册。

在这里插入图片描述
重新启动Nginx，然后用浏览器测试：http://murphy-tomcat （此域名须配置域名指向）

在这里插入图片描述
2.2 负载均衡
2.2.1 什么是负载均衡
负载均衡建立在现有网络结构之上，它提供了一种廉价有效透明的方法扩展网络设备和服务器的带宽、增加吞吐量、加强网络数据处理能力、提高网络的灵活性和可用性。
负载均衡，英文名称为Load Balance，其意思就是分摊到多个操作单元上进行执行，例如Web服务器、FTP服务器、企业关键应用服务器和其它关键任务服务器等，从而共同完成工作任务。
2.2.2 准备工作
使用docker创建 3 个tomcat容器，端口分别为8080，8081，8082
docker run -id --name=c_tomcat1 \
-p 8081:8080 \
-v $PWD:/usr/local/tomcat/webapps \
tomcat

docker run -id --name=c_tomcat2 \
-p 8082:8080 \
-v $PWD:/usr/local/tomcat/webapps \
tomcat
123456789
分别启动这三个tomcat服务
为了能够区分是访问哪个服务器的网站，可以在首⻚标题加上标记以便区分
2.2.3 配置负载均衡
修改Nginx配置文件：lb.conf
upstream tomcat-nginx-lb {
        server 192.168.2.190:8080;
        server 192.168.2.190:8081;
        server 192.168.2.190:8082;
}
server {
        listen 80; # 监听的端口
        server_name www.murphynginx.com; # 域名或ip
        location / { # 访问路径配置
                # root index;# 根目录
                proxy_pass http://tomcat-nginx-lb;
                index index.html index.htm; # 默认首⻚
        }
        error_page 500 502 503 504 /50x.html; # 错误⻚面
        location = /50x.html {
                root html;
        }
}
123456789101112131415161718
地址栏输入http://www.murphynginx.com，刷新观察每个网⻚的标题，看是否不同。（注意：域名需要在hosts文件中注册）
经过测试，三台服务器出现的概率各为33.3333333%，交替显示。
如果其中一台服务器性能比较好，想让其承担更多的压力，可以设置权重。比如想让8081出现次数是其它服务器的2倍，则修改配置如下：
upstream tomcat-nginx-lb {
        server 192.168.2.190:8080;
        server 192.168.2.190:8081 weight=2;
        server 192.168.2.190:8082;
}
12345
重启Nginx服务器即可。
至此，简单实现了Nginx的负载均衡。


请求代理
location /abc/ {
    proxy_pass http://127.0.0.1:8880/;
    proxy_read_timeout 90;

    proxy_http_version 1.1;
    proxy_set_header Upgrade $http_upgrade;
    proxy_set_header Connection $http_connection;
    proxy_set_header Host $host;
    proxy_set_header X-Real-IP $remote_addr;
    proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    proxy_set_header X-Forwarded-Proto $scheme;
    proxy_cache_bypass $http_upgrade;
}

请求 http://127.0.0.1/abc/index.html 会代理到 http://127.0.0.1:8080/index.html
ssl证书配置
listen 443 ssl;
#填写绑定证书的域名
server_name www.test.com;
ssl on;
ssl_certificate /路径/www.test.com.crt;
ssl_certificate_key /路径/www.test.com.key;
ssl_session_timeout 5m;
#按照这个协议配置
ssl_protocols TLSv1 TLSv1.1 TLSv1.2;
#按照这个套件配置
ssl_ciphers ECDHE-RSA-AES128-GCM-SHA256:HIGH:!aNULL:!MD5:!RC4:!DHE;
ssl_prefer_server_ciphers on;

http强转https
所有的http请求都会被重写到https
server {
    listen 80;
    server_name www.test.com;
    default_type 'text/html';
    charset utf-8;
    rewrite ^/(.*) https://$server_name/$1 permanent; #跳转到Https
}

特定地址重定向
如将下载更新的页面重定向到腾讯应用宝
location /download.html {
    rewrite ^/(.*) https://sj.qq.com/myapp/detail.htm?apkName=com.xxx.yyy permanent;
}

将指定的地址重定向为另外一个地址，参数不变
if ($request_uri ~* /downLoad.do\?loadFile=/aaa/bbb) {
    rewrite ^/(.*) https://www.test.com/upload/file$arg_loadFile? permanent;
}

将路径中特定的关键字转换为另外的关键字
如将请求地址中的AAA转换为abc http://127.0.0.1/AAA/index.html 会重写为 http://127.0.0.1/abc/index.html
if ($request_uri ~* ^(/AAA/)) {
    rewrite ^/AAA/(.*)$ /abc/$1 last;
}

设置DNS服务器
resolver 223.5.5.5 223.6.6.6 1.2.4.8 114.114.114.114 valid=3600s;

代理特定的文件
如进行微信认证时需要指定特定的文件放在域名的根目录下root指向放置MP_verify_aaabbbcccddd.txt的目录
location /MP_verify_aaabbbcccddd.txt {
     root /opt/data/路径/;
}

查看当前nginx的状态
location /status {
    stub_status on;
    access_log /var/log/nginx/status.log; #日志
}

配置好并重启 可以通过 http://127.0.0.1/status 来查看当前nginx的状态

image-20201108213009213
lua文件配置
location /test/ {
    default_type text/html;
    #lua_code_cache off; #是否开启lua的缓存；开启之后只有第一次启动的时候会编译；不开启每次执行都编译
    set $lable 0; # 设置一个变量，用来校验是否匹配了lua脚本
    
    # if ($request_uri ~* /abc.*/bbb) { # p判断是否匹配abc打头的请求 如: /abcddd/bbb  /abc123/bbb
    # 判断请求路径是否包含了 /aaa/bbb
    if ( $request_uri ~* /aaa/bbb ) {
        set $lable 1; # 给变量赋值
        content_by_lua_file /opt/docker/nginx/lua/test.lua;
    }
    
    proxy_set_header Host $host;
    proxy_set_header X-Real-IP $remote_addr;
    proxy_set_header X-Real-Port $remote_port;
    proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    proxy_pass http://127.0.0.1:8080/;
}

代理静态文件（如图片）
location /aaa/bbb/ {
    root /opt/docker/nginx/;
}

如访问 http://127.0.0.1/aaa/bbb/1.png 实际是会代理到 /opt/docker/nginx/aaa/bbb目录下的1.png
静态页面代理
location /hello/ {
    index index.html;
    root /opt/docker/nginx/html/;
}

访问 http://127.0.0.1/hello/ 会代理到**/opt/docker/nginx/html/路径下的index.html**页面
配置日志输出
access_log  logs/access.log  main;

log_format  main
'[$time_iso8601] ' # 通用日志格式的本地时间
'[$hostname] ' # 主机名
'[$remote_addr] ' # 客户端地址
'[$remote_port] ' # 客户端端口
'[$connection] ' # 连接序列号
'[$server_addr] ' # 接受请求的服务器的地址
'[$server_port] ' # 接受请求的服务器的端口
'[$request_id] '  # 请求的唯一ID
'[$request_method] ' # 请求方式
'[$request_uri] ' # 完整的原始请求URI
'[$request_time] ' # 请求以毫秒为单位的处理时间，以毫秒为单位（1.3.9，1.2.6）；从客户端读取第一个字节以来经过的时间
'[$request_length] ' # 请求长度
'[$request_body] ' # 请求主体
'[-] ' #'[$bytes_received] ' #从客户端收到的字节数
'[$content_type] ' # 内容类型
'[$content_length] ' # 内容长度
'[$remote_user] ' # 基本身份验证随附的用户名
'[$request] ' # 完整的原始请求行
'[$scheme] ' # 请求方案 http或https
'[$msec] ' # 请求以秒为单位的时间戳
'[-] ' #'[$protocol] ' # 与客户端通信的协议： TCP或UDP
'[-] ' #'[$session_time] ' # 会话持续时间
'[$upstream_addr] ' # 与上游的连接地址
'[$upstream_response_time] ' # 从上游接受响应的耗时
'[$upstream_connect_time] ' # 与上游建立连接的时间
'[-] ' #'[$upstream_bytes_sent] ' # 发送到上游服务器的字节数
'[$upstream_bytes_received] ' # 发送到上游服务器的字节数
'[$upstream_status] ' # 从上游获取的响应状态
'[$status] ' # 响应状态
'[$body_bytes_sent] ' # 发送给客户端的字节数，不计算响应头
'[$bytes_sent] ' # 发送给客户端的字节数
'[$http_user_agent] '
'[$http_referer] '
'[$http_host] '
'[$http_x_forwarded_for] '
'[$http_Authorization] '
'[$cookie_uid] ';  

配置跨域
proxy_set_header Host $host;
proxy_set_header X-Real-IP $remote_addr;
proxy_set_header X-Real-Port $remote_port;
proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;

设置最大body的大小
如上传文件，指定最大的文件大小
client_max_body_size 50m;

设置请求头的大小
server_names_hash_bucket_size 256;
client_header_buffer_size 256k;
large_client_header_buffers 4 256k;

客户端保持连接的时间
keepalive_timeout 60s;


```