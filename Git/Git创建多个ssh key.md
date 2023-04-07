### 1. 创建多个SSH-Key，使用ssh-key生成公钥和私钥
```
# 生成ssh-key，~/:指的是C盘用户的地址，一般是C:\Users\Administrator\.ssh
# 作为github使用
ssh-keygen -t rsa -C "username@email.com" -f ~/.ssh/gitlab_id_rsa
# 生成第二个ssh-key,指的是公司的地址。username可以定义为你在公司提交的名字，生成密钥
# 作为gitlab使用
ssh-keygen -t rsa -C "username1@email.com" -f ~/.ssh/gitlab_id_rsa
```
### 2. 进入到~/.ssh目录下 
* ssh key生成了如下两个文件，一个id_rsa私钥，一个id_rsa.pub公钥
### 3. 使用ssh-add添加私钥 
因为Git默认使用id_rsa，我们需要将密钥添加进去，同时配置config，作用是告知Git，让Git根据不同的域名选择不同的私钥
```
# 右键打开Git Base Here
ssh-add ~/.ssh/id_rsa
ssh-add ~/.ssh/gs_rsa
# 如果提示：Could not open a connection to your authentication agent，则先执行这个命令
ssh-agent bash
# 如果成功，会打印出SHA256:你的私钥，一串加密字符串 rsa的地址
ssh-add -l
```

- 在~./ssh目录下新建一个config文件,命令如下：
`touch config`
`vim cofig`
配置文件如下：
```#github
Host github.com
IdentityFile ~/.ssh/id_rsa
User a@b.com

#gitlab
Host gitlab.com
IdentityFile ~/.ssh/id_rsa_gitlab
User a@b.com
```

* 名称	            释义
* Host	        自定义名称，任意字符串，可以看作识别码，使用时改为(Git@[Host].aliyuan.com)
* HostName	    Git服务器的域名，使用IP地址也可,一定要是有效的
* User	        你在Git服务器使用的用户名，使用ssh-add时的用户名也可
* IdentityFile	配置对应的私钥文件存放的地址，建议写全地址


### 4. 使用以下命令分别测试GitHub和Gitee，查看SSH Key是否添加成功。
```
ssh -T Git@gitee.com
ssh -T Git@github.com
```