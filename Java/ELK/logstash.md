## 后台运行

```shell
# 我们按照网上的教程启动之后，不能使用ctrl+c，要不然会退出命令，下面给出一个可以后台运行的命令！
# 启动logstash
nohup ./bin/logstash -f logpipeline.conf & > nohup.out
#启动filebeat 
nohup ./filebeat -e -c filebeat.yml -d "Publish" & > nohup.out
#启动metricbeat
nohup ./metricbeat -e -c metricbeat.yml -d "publish" & > nohup.out

```