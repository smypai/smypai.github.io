```
win10 修改docker的镜像文件存储位置-通过WSL2修改
人之常态，是孤独；尤为珍贵，是陪伴。
创作声明：内容包含虚构创作
WSL2模式下docker-desktop-data vm磁盘映像通常位于以下位置：
C:\Users\admin\AppData\Local\Docker\wsl\data\ext4.vhdx
按照以下说明将其重新定位到其他目录，并保留所有现有的Docker数据
1、首先，右键单击Docker Desktop图标关闭Docker桌面，然后选择退出Docker桌面，然后，打开命令提示符：
wsl --list -v
2、您应该能够看到，确保两个状态都已停止

默认情况下，Docker Desktop for Window会创建如下两个发行版（distro)
 C:\Users\jayzhen\AppData\Local\Docker\wsl

docker-desktop (对应distro/ext4.vhdx)
docker-desktop-data （对应data/ext4.vhdx）
按官网提示：vhdx文件最大支持256G，超出大小会有异常[1]
3、将docker-desktop-data导出到文件中(备份image及相关文件)，使用如下命令：
wsl --export docker-desktop-data "D:\\docker-desktop-data.tar"
4、wsl取消注册docker-desktop-data，请注意C:\Users\admin\AppData\Local\Docker\wsl\data\ext4.vhdx文件将被自动删除
wsl --unregister docker-desktop-data
5、将导出的docker-desktop-data再导入回wsl，并设置路径，即新的镜像及各种docker使用的文件的挂载目录，我这里设置到F:\Docker-wsl\wsl
wsl --import docker-desktop-data "F:\Docker-wsl\wsl" "D:\\docker-desktop-data.tar" --version 2
6、命令执行完毕，就能再目录下看到文件了，这时次启动Docker Desktop，一切正常
验证是否有效
可以pull一个镜像，查看目录大小变化
```