[官网](https://nacos.io/zh-cn/docs/quick-start.html)
1. 预备环境准备 jdk8+  2C4g60g*3的机器配置
2. 下载源码或者安装包
```commandline
git clone https://github.com/alibaba/nacos.git
cd nacos/
mvn -Prelease-nacos -Dmaven.test.skip=true clean install -U  
ls -al distribution/target/

// change the $version to your actual path
cd distribution/target/nacos-server-$version/nacos/bin
```
[安装包](https://github.com/alibaba/nacos/releases)
```commandline
  unzip nacos-server-$version.zip 或者 tar -xvf nacos-server-$version.tar.gz
  cd nacos/bin
```
3. 启动服务器
```commandline
Linux/Unix/Mac
sh startup.sh -m standalone
Windows
startup.cmd -m standalone
```
4. 服务注册&发现和配置管理
```commandline
服务注册
curl -X POST 'http://127.0.0.1:8848/nacos/v1/ns/instance?serviceName=nacos.naming.serviceName&ip=20.18.7.10&port=8080'
服务发现
curl -X GET 'http://127.0.0.1:8848/nacos/v1/ns/instance/list?serviceName=nacos.naming.serviceName'
发布配置
curl -X POST "http://127.0.0.1:8848/nacos/v1/cs/configs?dataId=nacos.cfg.dataId&group=test&content=HelloWorld"
获取配置
curl -X GET "http://127.0.0.1:8848/nacos/v1/cs/configs?dataId=nacos.cfg.dataId&group=test"

```

5. 关闭服务器
```commandline
Linux/Unix/Mac
sh shutdown.sh
Windows
shutdown.cmd
```