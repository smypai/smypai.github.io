```
设置客户端psql -h host -u user 连接PostgreSQL不需要密码

在 Shell 命令行中，使用postgresql-client 连接 PostgreSQL 数据库时，每次都要输入密码。如果要写 Shell Script，做一些类似于备份的自动化管理工作，每次都还要手动输入密码是一件让人很不爽的事情，也没有实现真正意义上的自动化。
平常工作中，有时需要远端连接 PostgreSQL 数据库做些维护，例如远端备份等；如果备份脚本写在远端机器，备份的时候会弹出密码输入提示，那么脚本就不能后台执行，这里总结了几种不弹出密码输入提示的方法。

--测试环境
目标库IP: 192.168.1.25/5432 ； 数据库： Mydb ；用户名：postgres
客户端IP: 192.168.1.26
--在 192.168.1.26 连接数据库Mydb, 弹出密码提示
postgres@linux-> psql -h 192.168.1.25 -p 5432 Mydb postgres
Password for user postgres:

方法一：设置环境变量 PGPASSWORD
PGPASSWORD 是 PostgreSQL 系统环境变量，在客户端设置这后，那么在客户端连接远端数据库时，将优先使用这个密码。

--测试
postgres@linux-> export PGPASSWORD=mypassword 
postgres@linux-> psql -h 192.168.1.25 -p 5432 Mydb postgres
psql (8.4.4)
Type "help" for help.

Mydb=> \q
 备注：设置环境变量 PGPASSWORD ，连接数据库不再弹出密码输入提示。 但是从安全性方面考虑，这种方法并不推荐。

方法二：设置 .pgpass 密码文件

通过在客户端 /home/postgres 目录下创建隐藏文件 .pgpass ，从而避免连接数据库时弹出密码输入提示。
--创建密码文件 .pgpass ( on 客户端 )
vi /home/postgres/.pgpass

--格式
hostname:port:database:username:password

--范例
192.168.1.25:5432:Mydb:postgres:mypassword 

--权限
Chmod 600 .pgpass
--连接测试
postgres@linux-> psql -h 192.168.1.25 -p 5432 Mydb postgres
psql (8.4.4)
Type "help" for help.

Mydb=>

备注：在/home/postgres 目录创建了密码文件 .pgpass 文件后，并正确配置连接信息，那么客户端连接数据时会优先使用 .pgass文件， 并使用匹配记录的密码，从而不跳出密码输入提示,这种方法比方法一更安全，所以推荐使用创建 .pgpass 文件方式。

方法三：修改服务器端 pg_hba.conf
修改认证文件 $PGDATA/pg_hba.conf, 添加以下行， 并 reload使配置立即生效。

host Mydb  postgres  192.168.0.0/24  trust
[postgres@linux]$pg_ctl reload -D $PGDATA / service postgresql reload
server signaled
--服务器端 pg_hba.conf 的配置
# IPv4 local connections:
host all all 127.0.0.1/32  trust
host Mydb  postgres  192.168.0.0/24  trust
host all all 0.0.0.0/0 md5
--客户端再次连接测试
postgres@linux-> psql -h 192.168.1.25 -p 5432 Mydb postgres
psql (8.4.4)
Type "help" for help.
Mydb=> \q
备注：修改服务端 pg_hba.conf 并 reload 后，不再弹出密码输入提示。

引用官方说明文档：
用户家目录中的 .pgpass 或者 PGPASSFILE 引用的文件是一个可包含口令的文件。如果连接要求口令（并且没有用其它方法声明口令），那么可以用它。在 Microsoft Windows 上，文件名字是 %APPDATA%\postgresql\pgpass.conf （这里的 %APPDATA% 指用户配置里的 Application Data 子目录）。
这个文件应该有下面这样的格式行：
hostname:port:database:username:password
头四个字段每个都可以是一个文本值，或者 *，它匹配所有的东西。第一个匹配当前连接参数的口令行的口令域将得以使用。（因此，如果你使用了通配符，那么应该把最具体的记录放在前面。）如果记录包含 : 或者 \ ，应该用 \ 逃逸。一个 localhost 的主机名匹配来自本机的 host（TCP）和 local （Unix 域套接字）。
.pgpass 的权限必须不允许任何全局或者同组的用户访问；我们可以用命令 chmod 0600 ~/.pgpass 实现这个目的。如果权限比这个松，这个文件将被忽略。（不过目前在 Microsoft Windows 上没有检查这个文件的权限。）
```