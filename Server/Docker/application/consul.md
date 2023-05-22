```
docker教程 https://www.runoob.com/docker/docker-tutorial.html
DaoCloud Hub, 分享镜像 交流协作 https://hub.daocloud.io/


docker run -d --name=consul_study -p 8500:8500 consul agent -server -bootstrap -ui -client 0.0.0.0


docker run -d --name=consul_study -p 8500:8500 consul agent -server -bootstrap -ui -client 0.0.0.0

启动Consul
consul agent -dev 

注册到Consul
curl --request PUT --data @test.json http://127.0.0.1:8500/v1/agent/service/register

查看Consul服务
curl http://127.0.0.1:8500/v1/agent/services

删除注册服务
curl --request PUT http://127.0.0.1:8500/v1/agent/service/deregister/Test

golang 限号列表


```