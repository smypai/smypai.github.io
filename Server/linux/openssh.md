```
1安装openssh-server
  yum install openssh-server

2) 查看是否已成功安装openssh-server
  rpm -qa | grep openssh-server

3)启动SSH服务
  /etc/rc.d/init.d/sshd start restart stop

4) 如果防火墙服务有开启, 则需要允许TCP协议的22端口通信.
 iptables -I INPUT -p tcp --dport 22 -j ACCEPT

6) 如果想让ssh服务开机就运行, 需要使用# ntsysv命令打开开机服务选项卡. 选中sshd后按”OK”
 ntsysv

7）如果想配置ssh服务的运行参数, 是通过修改配置文件/etc/ssh/ssh_config实现的.如果没什么特殊的要求这都不用配置. /etc/ssh/ssh_config文件的配置选项非常多, 但大部分都已经用”#”注释掉了.配置完记得保存配置文件.
```