````docker
docker-compose up &  #后台启动
docker-compose up      #全部服务启动
docker-compose up eurake0  #指定具体的服务启动
docker-compose up -d
docker-compose -f docker-compose.yml up -d

docker-compose down
docker-compose down -v
docker-compose down eurake0  #卸载掉eurake0服务

docker-compose start eurake1

docker-compose stop eurake1

docker-compose logs       #查看日志
docker-compose logs  eurake0     #查看具体某个服务日志

docker-compose -f   #指定文件 默认docker-compose.yml

docker-compose --help

````