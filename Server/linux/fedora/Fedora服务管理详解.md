```
服务概述

服务（常称作后台进程）是后台运行的的特殊程序，它们常常是非交互性的（没有控制台）。它可以用于各种用途：管理硬件，网络连接，监测，日志等等。几乎所有的操作系统都通过服务来运行一些自动化的任务。rc 脚本负责启动用户需要的所有服务。就像名字所描述的一样，所谓服务就是系统提供的有用的工具。可能会有很多服务需要启动。大部分的 Linux 系统会启动 sshd（安全Shell服务）、syslog（系统日志工具）和 lpd（打印服务），但还会有更多的服务需要启动。过多的服务会增加开机的时间，优化关闭不需要的服务将会提高启动时间。

运行级别

运行级别（runlevel）是一系列后台运行程序的集合，运行于不同运行级别的系统将启动不同的服务（也称后台进程）。基于 Fedora/Redhat Linux 系统的运行级别有：

runlevel 1：单用户模式

runlevel 2：多用户模式

runlevel 3：带网络功能的多用户模式

runlevel 5：图形用户模式（runlevel 3 + X 窗口系统“图形用户界面”）。

大多数用户的系统运行于包含 X-server 的运行级别 5，许多的服务器运行于不包含X-server 的运行级别 3。通常情况下，运行级别 1 不启动任何服务。

你可以通过运行下面的命令来查看系统的运行级别：

# /sbin/runlevel

通过下面的命令查看你的系统下次启动时的运行级别：

# cat /etc/inittab | grep :initdefault:

id:5:initdefault:

你可以通过修改 /etc/inittab 文件第 18 行 initdefault 的值来改变系统的运行级别，系统将在下次启动时运行于你指定的运行级别。

通过下面的命令来切换运行级别（用数字如 3，5 等来替换 RUNLEVEL）：

# /sbin/init RUNLEVEL

注意： 当你从运行级别 5 切换到其他运行级别时，你将同时关闭 X-server 和整个的图形界面。在切换运行级别时，总是确保你运行于文本终端（CTRL-ALT-F1, F2, F3, F4）。

通过编辑 Grub 的启动菜单项，可以在系统启动时指定运行级别。指定方法：系统启动时，选择你要启动的内核，按 e 键，然后在行末添加你期望的运行级别，比如 1，3，5 等。然后按 Enter 键，接着按 b 键启动系统。

启用和关闭服务（services）

对所有运行级别，每个服务都必须设为 On（启用） 或者 Off（关闭） 状态。

通过下面的命令查看各个运行级别启用的服务：

# /sbin/chkconfig --list

可以通过在命令行运行 system-config-services 来为不同的运行级别启用或关闭服务，该命令只能在图形界面（GUI）下工作。（早一点的 Fedora 版本可能使用 serviceconf 命令来实现 system-config-services 的功能）

chkconfig 命令可以启用指定的服务。下面的命令使运行级别 3 和 5 启用 crond 服务。

# /sbin/chkconfig --level 35 crond on

--level 应为 1，2，3，4，5 或它们的任意组合，on（启用）也可以是 off（关闭）。运行 man chkconfig 可以获得详细的使用帮助。

管理服务

不论服务在当前运行级别上是启用还是关闭，都可以运行和停止它。

查看运行中的服务：

# /sbin/service --status-all

使用 service 来控制服务。例如：

# /sbin/service crond status

crond (pid 1604) is running...

status 参数可以替换为 start，stop，status，reload，restart 等等，例如：

[root@localhost yichi]# service crond

Usage: /etc/init.d/crond {start|stop|status|reload|restart|condrestart}

[root@localhost yichi]# service crond stop

Stopping crond: [ OK ]

[root@localhost yichi]# service crond start

Starting crond: [ OK ]

[root@localhost yichi]# service crond restart

Stopping crond: [ OK ]

Starting crond: [ OK ]

/etc/init.d/ 目录下的命令都可以通过这种方式来控制。其它控制服务的类似方法：

# /etc/init.d/crond status

其实上面提到的所有功能都可以通过使用 system-config-services 命令在 GUI 下完成。不过有的时候你可能不能使用 GUI，比如通过 SSH 登录到其它系统时。这时候这些命令就变得非常有用。

单个服务介绍

现在我们介绍FedoraCore6中所包含的各种服务（services）的功能，并提供使用建议。这不是一份详尽的清单。小心：不要关闭你不确定或不知道的服务（services）。

不要关闭以下服务（除非你有充足的理由）：

acpid,haldaemon,messagebus,klogd,network,syslogd,udev-post

请确定修改的是运行级别3和5。

NetworkManager,NetworkManagerDispatcher

NetworkManager是一个自动切换网络连接的后台进程。很多笔记本用户都需要启用该功能，它让你能够在无线网络和有线网络之间切换。大多数台式机用户应该关闭该服务。一些DHCP用户可能需要开启它。

udev-post

"udev"的用途是Fedora的设备管理系统，默认情况下"udev"支持很多个规则，用于设备的权限和行为管理，该服务允许用于保存用户应用的规则，这里强烈的推荐保留该服务启用。

acpid

ACPI（全称AdvancedConfigurationandPowerInterface）服务是电源管理接口。建议所有的笔记本用户开启它。一些服务器可能不需要acpi。支持的通用操作有：“电源开关“，”电池监视“，”笔记本Lid开关“，“笔记本显示屏亮度“，“休眠”，“挂机”，等等。

anacron,atd,cron

这几个调度程序有很小的差别。建议开启cron，如果你的电脑将长时间运行，那就更应该开启它。对于服务器，应该更深入了解以确定应该开启哪个调度程序。大多数情况下，笔记本/台式机应该关闭atd和anacron。注意：一些任务的执行需要anacron，比如：清理/tmp或/var。

apmd

一些笔记本和旧的硬件使用apmd。如果你的电脑支持acpi，就应该关闭apmd。如果支持acpi，那么apmd的工作将会由acpi来完成。

btseed,bttrack

这些服务支持点对点网络系统BitTorrent使用的自动播种和跟踪torrents。应该禁用这些服务除非你特别想播种或跟踪torrents。通过播种本质上你可以和他人分享torrent中的内容和跟踪其它和你一样行为的BitTorrent客户端。

abrt

abrt (automatic bug report tool)服务将系统bug和SELinux收集汇报给Bugzilla以方便开发者修复。

autofs

该服务自动挂载可移动存储器（比如USB硬盘）。如果你使用移动介质（比如移动硬盘，U盘），建议启用这个服务。

avahi-daemon,avahi-dnsconfd

Avahi是zeroconf协议的实现。它可以在没有DNS服务的局域网里发现基于zeroconf协议的设备和服务。它跟mDNS一样。除非你有兼容的设备或使用zeroconf协议的服务，否则应该关闭它。我把它关闭。

bluetooth,hcid,hidd,sdpd,dund,pand

蓝牙（Bluetooth）是给无线便携设备使用的（非wifi,802.11）。很多笔记本提供蓝牙支持。有蓝牙鼠标，蓝牙耳机和支持蓝牙的手机。很多人都没有蓝牙设备或蓝牙相关的服务，所以应该关闭它。其他蓝牙相关的服务有：hcid管理所有可见的蓝牙设备，hidd对输入设备（键盘，鼠标）提供支持，dund支持通过蓝牙拨号连接网络，pand允许你通过蓝牙连接以太网。

capi

仅仅对使用ISDN设备的用户有用。大多数用户应该关闭它。

CPUspeed

该服务可以在运行时动态调节CPU的频率来节约能源（省电）。许多笔记本的CPU支持该特性，现在，越来越多的台式机也支持这个特性了。如果你的CPU是：Petium-M，Centrino，AMDPowerNow，Transmetta，IntelSpeedStep，Athlon-64，Athlon-X2，IntelCore2中的一款，就应该开启它。如果你想让你的CPU以固定频率运行的话就关闭它。

cron

参见anacron。

cupsd,cups-config-daemon

打印机相关。如果你有能在Fedora中驱动的CUPS兼容的打印机，你应该开启它。

dc_client,dc_server

磁盘缓存（Distcache）用于分布式的会话缓存。主要用在SSL/TLS服务器。它可以被Apache使用。大多数的台式机应该关闭它。

dhcdbd

这是一个让DBUS系统控制DHCP的接口。可以保留默认的关闭状态。

diskdump,netdump

磁盘转储（Diskdump）用来帮助调试内核崩溃。内核崩溃后它将保存一个“dump“文件以供分析之用。网络转储（Netdump）的功能跟Diskdump差不多，只不过它可以通过网络来存储。除非你在诊断内核相关的问题，它们应该被关闭。dund参见bluetooth。

firstboot

该服务是Fedora安装过程特有的。它执行在安装之后的第一次启动时仅仅需要执行一次的特定任务。它可以被关闭。

gpm

终端鼠标指针支持（无图形界面）。如果你不使用文本终端（CTRL-ALT-F1,F2..），那就关闭它。不过，我在运行级别3开启它，在运行级别5关闭它。

hidd

参见bluetooth。

hplip,hpiod,hpssd

HPLIP服务在Linux系统上实现HP打印机支持，包括Inkjet，DeskJet，OfficeJet，Photosmart，BusinessInkJet和一部分LaserJet打印机。这是HP赞助的惠普Linux打印项目（HPLinuxPrintingProject）的产物。如果你有相兼容的打印机，那就启用它。

iptables

它是Linux标准的防火墙（软件防火墙）。如果你直接连接到互联网（如，cable，DSL，T1），建议开启它。如果你使用硬件防火墙（比如：D-Link，Netgear，Linksys等等），可以关闭它。强烈建议开启它。

ip6tables

如果你不知道你是否在使用IPv6，大部分情况下说明你没有使用。该服务是用于IPv6的软件防火墙。大多数用户都应该关闭它。阅读这里了解如何关闭Fedora的IPv6支持。

hsqldb

Hsqldb是一个JAVA数据库服务器，除非你从事JAVA方面的开发，否则禁用这个服务。

halddaemon

HAL是指Hardware Abstraction Layer。这是个从几个来源收集和保持有关硬件信息的重要服务。接受由udev通过D-BUS传递来的消息后调fstab-sync修改 fstab文档，动态创建/media目录下的子目录作为挂载点。如果在进行系统优化时可能会无意中关闭haldaemon服务，这样就会导致无法动态更改/etc/fstab文档，从而导致移动设备接入系统后无法自动识别。此时能够通过mount命令进行手动挂载。

irda,irattach

IrDA提供红外线设备（笔记本，PDA''s，手机，计算器等等）间的通讯支持。大多数用户应该关闭它。

wpa_supplicant

如果你使用一个无线网卡被要求去使用基于WPA加密连接到一个无线接入点的话，VPN或者Rediuas服务器，这个服务必须被启用。多数没有这样需求的的用户可以保留该服务为停用。

irqbalance

在多处理器系统中，启用该服务可以提高系统性能。大多数人不使用多处理器系统，所以关闭它。但是我不知道它作用于多核CPU''s或超线程CPU''s系统的效果。在单CPU系统中关闭它应该不会出现问题。这个服务用于增强多处理器或多核心系统的性能。对于没有多处理器或多核心系统的用户可以禁用这个服务。对于较新的多核心计算机(intel Core 2 Duo,AMD X2)，应该启用这个服务，启用这个服务对于单核心的系统没有影响。

isdn

这是一种互联网的接入方式。除非你使用ISDN猫来上网，否则你应该关闭它。

kudzu

该服务进行硬件探测，并进行配置。如果更换硬件或需要探测硬件更动，开启它。但是绝大部分的台式机和服务器都可以关闭它，仅仅在需要时启动。

lm_sensors

该服务可以探测主板感应器件的值或者特定硬件的状态（一般用于笔记本电脑）。你可以通过它来查看电脑的实时状态，了解电脑的健康状况。它在GKrellM用户中比较流行。查看lm_sensors的主页获得更多信息。如果没有特殊理由，建议关闭它。

kernelloops

这是个报告服务，当内核遇到被称为“oops“的问题时向内核开发人员发送专门的调试信息

mctrans

如果你使用SELinux就开启它。默认情况下FedoraCore开启SELinux。

gamin

检测到 fstab 发生变化之后，负责通知桌面系统。桌面系统在 “电脑” 项目中建立驱动器图标。

hotplug

当硬件设备接入系统后，首先由hotplug服务检测到并自动配置内核状态，调用添加相关的内核模块。

mdmonitor

该服务用来监测SoftwareRAID或LVM的信息。它不是一个关键性的服务，可以关闭它。

mdmpd

该服务用来监测Multi-Path设备（该类型的存储设备能被一种以上的控制器或方法访问）。它应该被关闭。

messagebus

事件监控服务，在必要时向所有用户发送广播信息,如服务器将要重启。

这是Linux的IPC（InterprocessCommunication，进程间通讯）服务。确切地说，它与DBUS交互，是重要的系统服务。强烈建议开启它。

netdump

参见diskdump。

netplugd

Netplugd用于监测网络接口并在接口状态改变时执行指定命令。建议保留它的默认关闭状态。

netfs

该服务用于在系统启动时自动挂载网络中的共享文件空间，比如：NFS，Samba等等。如果你连接到局域网中的其它服务器并进行文件共享，就开启它。大多数台式机和笔记本用户应该关闭它。

nfs,nfslock

这是用于Unix/Linux/BSD系列操作系统的标准文件共享方式。除非你需要以这种方式共享数据，否则关闭它。

ntpd

该服务通过互联网自动更新系统时间。如果你能永久保持互联网连接，建议开启它，但不是必须的。

pand

参见bluetooth。

pcscd

该服务提供智能卡（和嵌入在信用卡，识别卡里的小芯片一样大小）和智能卡读卡器支持。如果你没有读卡器设备，就关闭它。

portmap

该服务是NFS（文件共享）和NIS（验证）的补充。除非你使用NFS或NIS服务，否则关闭它。

readahead_early,readahead_later

该服务通过预先加载特定的应用程序到内存中以提供性能。如果你想程序启动更快，就开启它。

restorecond

用于给SELinux监测和重新加载正确的文件上下文（filecontexts）。它不是必须的，但如果你使用SELinux的话强烈建议开启它。

rpcgssd,rpcidmapd,rpcsvcgssd

用于NFSv4。除非你需要或使用NFSv4，否则关闭它。

sendmail

除非你管理一个邮件服务器或你想在局域网内传递或支持一个共享的IMAP或POP3服务。大多数人不需要一个邮件传输代理。如果你通过网页（hotmail/yahoo/gmail）或使用邮件收发程序（比如：Thunderbird，Kmail，Evolution等等）收发程序。你应该关闭它。

smartd

SMARTDiskMonitoring服务用于监测并预测磁盘失败或磁盘问题（前提：磁盘必须支持SMART）。大多数的桌面用户不需要该服务，但建议开启它，特别是服务器。

jexec

如果你安装java 1.6 就会有这个，让你可以双击运行 *.jar 文件。不想要就关

smb

SAMBA服务是在Linux和Windows之间共享文件必须的服务。如果有Windows用户需要访问Linux上的文件，就启用它。查看如何在FedoraCore6下配置Samba。

sshd

SSH允许其他用户登录到你的系统并执行程序，该用户可以和你同一网络，也可以是远程用户。开启它存在潜在的安全隐患。如果你不需要从其它机器或不需要从远程登录，就应该关闭它。

xinetd

该服务默认可能不被安装，它是一个特殊的服务。它可以根据特定端口收到的请求启动多个服务。比如：典型的telnet程序连接到23号端口。如果有telent请求在23号端口被xinetd探测到，那xinetd将启动telnetd服务来响应该请求。为了使用方便，可以开启它。运行chkconfig--list，通过检查xinetd相关的输出可以知道有哪些

系统服务优化建议

此部分可能存在风险，如果你不确定的服务请不要关闭他，此优化针对桌面用户

NetworkManager 关闭

NetworkManagerDispatcher 关闭

anacron关闭

atd 关闭

apmd 关闭

avahi-daemon 关闭

avahi-dnsconfd 关闭

bluetooth hcid sdpd hidd dund pand 关闭 (你不使用蓝牙设备)

capi 关闭

cupsd 关闭 (你不使用打印机设备)

dc client dc server 关闭 (你不使用Apache)

firstboot 关闭

gpm 关闭 (终端中鼠标支持 你可以3中开启，5中关闭)

hplip hpiod hpssd 关闭 （你不使用打印机设备开启）

ip6tables 关闭

irqbalance 关闭 (多核CPU开启)

irda irattach 关闭 （使用红外开启）

im sensors 关闭

mdmonitor 关闭

mdmpd 关闭

netplugd 关闭

netfs 关闭

ntpd 关闭

pcscd 关闭

portmap 关闭

rpcgssd rpcidmapd rpcsvcgssd 关闭

sendmail 关闭

smb 关闭

参考至:http://hopehands.blog.163.com/blog/static/173114912006113111523223/

          http://nc.mofcom.gov.cn/news/17568280.html

          http://www.aooshi.org/blog/article/78.html

          http://blog.chinaunix.net/space.php?uid=20770507&do=blog&cuid=1404332

          http://linux.chinaunix.net/techdoc/system/2008/09/10/1031366.shtml

          http://linux.chinaunix.net/techdoc/install/2008/10/27/1041157.shtml

本文原创，转载请注明出处、作者

如有错误，欢迎指正

邮箱:czmcj@163.com
```