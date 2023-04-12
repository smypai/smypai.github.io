### 命令
```shell
Git global setup
Git config --global user.name "smypai1"
Git config --global user.email "smypai@hotmail.com"
Create a new repository
Git clone Git@gitlab.com:smypai1/wiki.Git
cd wiki
Git switch -c main
touch README.md
Git add README.md
Git commit -m "add README"
Git push -u origin main
Push an existing folder
cd existing_folder
Git init --initial-branch=main
Git remote add origin Git@gitlab.com:smypai1/wiki.Git
Git add .
Git commit -m "Initial commit"
Git push -u origin main
Push an existing Git repository
cd existing_repo
Git remote rename origin old-origin
Git remote add origin Git@gitlab.com:smypai1/wiki.Git
Git push -u origin --all
Git push -u origin --tags
```