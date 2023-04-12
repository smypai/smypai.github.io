[官网](https://nacos.io/zh-cn/docs/quick-start.html)
1. 预备环境准备 jdk8+  2C4g60g*3的机器配置
2. 下载源码或者安装包
```shell
git clone https://github.com/alibaba/nacos.git
cd nacos/
mvn -Prelease-nacos -Dmaven.test.skip=true clean install -U  
ls -al distribution/target/
// change the $version to your actual path
cd distribution/target/nacos-server-$version/nacos/bin
```
[安装包](https://github.com/alibaba/nacos/releases)  
```shell
  unzip nacos-server-version.zip 或者 tar -xvf nacos-server-version.tar.gz
  cd nacos/bin
```
3. 启动服务器
```shell
Linux/Unix/Mac
sh startup.sh -m standalone
Windows
startup.cmd -m standalone
```
4. 服务注册&发现和配置管理
```shell
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
```shell
Linux/Unix/Mac
sh shutdown.sh
Windows
shutdown.cmd
```


6. 运行报错问题：
61. ***Q:*** Nacos Caused by:Cannot determine JNI library name for ARCH=‘x86‘ OS=‘windows 10‘ name=‘rocksdb‘  
***A:*** 请安装Nacos对应版本的 64位JDK 


62. ***Q:*** nacos2.2启动报错The specified key byte array is 16 bits which is not secure enough for any JWT HMAC-SHA  
***A:*** conf/application.properties 配置文件修改  
`nacos.core.auth.default.token.secret.key=SecretKey012345678901234567890123456789012345678901234567890123456789`
> 下面的是官网给出的解决方案。 自定义密钥 开启鉴权之后，你可以自定义用于生成JWT令牌的密钥，application.properties中的配置信息为：
> 文档中提供的密钥为公开密钥，在实际部署时请更换为其他密钥内容，防止密钥泄漏导致安全风险。
> 在2.2.0.1版本后，社区发布版本将移除以文档如下值作为默认值，需要自行填充，否则无法启动节点。
> 密钥需要保持节点间一致，长时间不一致可能导致403 invalid token错误。
