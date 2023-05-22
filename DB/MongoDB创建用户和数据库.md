```
MongoDB创建用户和数据库
转载a13662080711 最后发布于2018-03-14 07:45:52 阅读数 13675  收藏
展开
、添加一个 userAdminAnyDatabase 用户,这是一个能够管理所有用户的的用户, 类似于超级管理员吧
#打开mongo shell
[root@localhost]# mongo
#添加超级管理账号
> use admin #进入admin表
> db.createUser(
     {
       user:"myadmin",
       pwd:"secret",
       roles:[{role:"root",db:"admin"}]
     }
  )
# 查看用户是否创建成功
>show users
# 输出以下内容表示添加成功 
{
  "_id" : "admin.myadmin",
  "user" : "myadmin",
  "db" : "admin",
  "roles" : [
    {
      "role" : "userAdminAnyDatabase",
      "db" : "admin"
    }
  ]
}
>exit

mongodb 有哪些权限:
1. 数据库用户角色：read、readWrite;  
2. 数据库管理角色：dbAdmin、dbOwner、userAdmin；       
3. 集群管理角色：clusterAdmin、clusterManager、clusterMonitor、hostManager；
4. 备份恢复角色：backup、restore；
5. 所有数据库角色：readAnyDatabase、readWriteAnyDatabase、userAdminAnyDatabase、dbAdminAnyDatabase
6. 超级用户角色：root  
// 这里还有几个角色间接或直接提供了系统超级用户的访问（dbOwner 、userAdmin、userAdminAnyDatabase）
7. 内部角色：__system
read:允许用户读取指定数据库
readWrite:允许用户读写指定数据库
dbAdmin：允许用户在指定数据库中执行管理函数，如索引创建、删除，查看统计或访问system.profile
userAdmin：允许用户向system.users集合写入，可以找指定数据库里创建、删除和管理用户
clusterAdmin：只在admin数据库中可用，赋予用户所有分片和复制集相关函数的管理权限。
readAnyDatabase：只在admin数据库中可用，赋予用户所有数据库的读权限
readWriteAnyDatabase：只在admin数据库中可用，赋予用户所有数据库的读写权限
userAdminAnyDatabase：只在admin数据库中可用，赋予用户所有数据库的userAdmin权限
dbAdminAnyDatabase：只在admin数据库中可用，赋予用户所有数据库的dbAdmin权限。
root：只在admin数据库中可用。超级账号，超级权限
2、验证用户登录
[root@localhost]# mongo
# 注意: 这里要验证刚才创建的用户,必须要先切换到admin库
>use admin
>show dbs 
# 这里会报错
2016-12-11T13:11:01.981+0800 E QUERY    [thread1] Error: listDatabases failed:{
  "ok" : 0,
  "errmsg" : "not authorized on admin to execute command { listDatabases: 1.0 }",
  "code" : 13
} :
_getErrorWithCode@src/mongo/shell/utils.js:23:13
Mongo.prototype.getDBs@src/mongo/shell/mongo.js:53:1
shellHelper.show@src/mongo/shell/utils.js:700:19
shellHelper@src/mongo/shell/utils.js:594:15
@(shellhelp2):1:1
 
# 验证用户
>db.auth('myadmin', 'secret')
1 # 输出1 表示验证成功
# 再次执行 
>show dbs 
# 这次就会列出所有数据库

3、创建数据库
语法：
use DATABASE 语句的基本语法如下：
use DATABASE_NAME
示例：
如果想创建一个数据库名称 , 那么 use DATABASE 语句如下：
>use mydb
switched to db mydb

要检查当前选择的数据库使用命令 db
>db
mydb
如果想检查数据库列表，使用命令show dbs
>show dbs
local     0.78125GB
test      0.23012GB

创建的数据库mydb 列表中是不存在的。要显示的数据库，需要把它插入至少一个文件。
>db.movie.insert({"name":"aaa"})
>show dbs
local      0.78125GB
mydb       0.23012GB
test       0.23012GB

4、为单个数据库添加管理用户
# 切换到要添加用户的数据库中
>use mydb
>db.createUser({
    user: 'test',
    pwd: 'test123',
    roles: [ { role: "readWrite", db: "mydb" } ]
})
Successfully added user: {
    "user" : "test",
    "roles" : [
        {
            "role" : "readWrite",
            "db" : "mydb"
        }
    ]
}
#返回fuccessfully 表示成功了~~~
# 查看刚才创建的用户
show users

```