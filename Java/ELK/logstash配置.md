```xml

下载对应的版本的logstashPast Releases of Elastic Stack Software | Elastic
到config文件夹下，复制一份logstash-sample.conf，命名为logstash.conf，这个是启动的默认配置文件，我这里配置了input和output，从微服务的日志文件读取。
 1.工作流程讲解：
input {     从哪个地方读取，输入数据。   } 
filter {    依据grok模式对数据进行分析结构化   } 
output {    将分析好的数据输出存储到哪些地方  }
  2.mysql同步数据到es配置
input {
  stdin {}
  jdbc {
  #mysql的jdbc连接工具jar包
    jdbc_driver_library => "D:/apache-maven-3.3.3/repository/mysql/mysql-connector-java/8.0.17/mysql-connector-java-8.0.17.jar"
    #jdbc驱动类全类名
    jdbc_driver_class => "com.mysql.cj.jdbc.Driver"
    #jdbc连接url
    jdbc_connection_string => "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8"
    #数据库用户名
    jdbc_user => "root"
    #数据库密码
    jdbc_password => "******"
    #数据同步时间（都是*则每一分钟同步一次）
    schedule => "* * * * *"
    #jdbc分页查询开启
    jdbc_paging_enabled => "true"
    #查询每页显示数据条数
    jdbc_page_size => "50000"
    #直接执行sql
    #statement =>"select * from xxxx"
    #sql文件路径（就是需要同步的数据sql语句）
    statement_filepath => "D:/Cx/devTool/logstash-7.12.0/mysql/statement.sql"
    #上次更新位置标记文件路径
    last_run_metadata_path => "D:/Cx/devTool/logstash-7.12.0/mysql/statement.txt"
    #每次启动是否清除上一项配置文件中数据
    clean_run => "false"
    #开启所有字段名转成小写
    lowercase_column_names => "true"
    #解决中文乱码问题
    codec => plain { charset => "UTF-8"}
    #是否记录上次运行的记录
    record_last_run => "true"
    #是否使用其他字段判断进行数据库同步
    use_column_value => "true"
    #数据库中的增量指标字段名
    tracking_column => "id"
  }
}
 
output {
    elasticsearch {
        index => "day_info"
        #es服务器
        hosts => "localhost:9200"
        document_id => "%{id}"
    }
    stdout {
        codec => json_lines
    }
}
log的流传方向就是
log日志文件----》logstash----》elasticsearch----》kibana








```