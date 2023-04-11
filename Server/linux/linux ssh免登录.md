SSH 免密登录
ssh-keygen -t [rsa|dsa]，将会生成密钥文件和私钥文件 id_rsa,id_rsa.pub或id_dsa,id_dsa.pub

这样生成了一对密钥，存放在用户目录的~/.ssh下。
将公钥考到对方机器的用户目录下，并将其复制到~/.ssh/authorized_keys中（操作命令：#cat id_dsa.pub >> ~/.ssh/authorized_keys）。

设置文件和目录权限：
设置authorized_keys权限
$ chmod 600 authorized_keys
设置.ssh目录权限
$ chmod 700 -R .ssh