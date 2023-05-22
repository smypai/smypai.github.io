```java
docker pull redis 
docker run -itd --name redis-admin -p 6380:6379 redis:latest
docker run -itd --name redis-test -p 6380:6379 redis --requirepass 5a123456

为现有的redis创建密码或修改密码的方法：
1.进入redis的容器 docker exec -it 容器ID bash
2.进入redis目录 cd /usr/local/bin
3.运行命令：redis-cli
4.查看现有的redis密码：config get requirepass
5.设置redis密码config set requirepass 123456（123456为你要设置的密码）
6.若出现(error) NOAUTH Authentication required.错误，则使用 auth 密码 来认证密码

https://www.runoob.com/docker/docker-install-redis.html
https://www.redis.net.cn/order/3535.html
https://www.runoob.com/redis/redis-keys.html
```