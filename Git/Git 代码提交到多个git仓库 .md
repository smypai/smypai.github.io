### 一、命令来进行添加仓库：
1. 先添加第一个GitHub的仓库地址：
`Git remote add origin https://github.com/huangge1199/my-blog.Git`
2. 再添加gitee的仓库地址
`Git remote set-url --add origin https://gitee.com/huangge1199_admin/my-blog.Git`
3. 查看远程仓库地址是否添加成功：
`Git remote -v`
4. 最后push提交代码 这样的话我们push时，就会将代码同时推送到两个仓库了。
`Git push origin master -f`

### 二、直接修改项目目录下隐藏目录.git中的config文件，在[remote “origin”]中添加多个仓库地址就可以了，参考如下：
```
[remote "origin"]
url = https://gitee.com/huangge1199_admin/my-blog.Git
fetch = +refs/heads/*:refs/remotes/origin/*
url = https://github.com/huangge1199/my-blog.Git
``