```
EMQ X (Erlang/Enterprise/Elastic MQTT Broker) 是基于 Erlang/OTP 平台开发的开源物联网 MQTT 消息服务器。官方有提供Docker版本，可以直接使用docker pull 进行下载。
它是目前MQTT服务器中，最优秀的产品之一，其优点有：
稳定承载大规模的 MQTT 客户端连接，单服务器节点支持50万到100万连接。
分布式节点集群，快速低延时的消息路由，单集群支持1000万规模的路由。
消息服务器内扩展，支持定制多种认证方式、高效存储消息到后端数据库。
完整物联网协议支持，MQTT、MQTT-SN、CoAP、LwM2M、WebSocket 或私有协议支持。
下载命令：
docker pull emqx
 
启动容器
docker run  --name=mqtt  --net=host --restart=always -d emqx

docker run --name emq -p 18083:18083 -p 1883:1883 -p 8084:8084 -p 8883:8883 -p 8083:8083 -d registry.cn-hangzhou.aliyuncs.com/synbop/emqttd:2.3.6

docker run --name emq -p 18083:18083 -p 1883:1883 -p 8084:8084 -p 8883:8883 -p 8083:8083 --restart=always -d emqx
 
启动后，登录mqtt管理页面：http://{ip}:18083，确认部署成功。
默认账号：admin
默认密码：public


 
 
接下来我们可以通过编写client端和server端的脚本，来模拟订阅和发布。
```