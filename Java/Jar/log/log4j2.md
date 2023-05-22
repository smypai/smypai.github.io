```xml
appender.console.type = Console
appender.console.name = console
appender.console.layout.type = PatternLayout
#appender.console.layout.pattern = %highlight{[%-5level] %d{yyyy-MM-dd HH:mm:ss.SSS} [%t] %c{1} - %msg%n}{FATAL=red, ERROR=red, WARN=yellow, INFO=cyan, DEBUG=cyan,TRACE=blue}
#appender.console.layout.pattern = %style{%d{yyyy-MM-dd HH:mm:ss:SSS}}{bright,green}%highlight{%5level} %thread %style{%c}{bright,yellow} [%style{%L}{bright,blue}] %highlight{%msg}%n%style{%throwable}{red}
appender.console.layout.pattern = %d{yyyy-MM-dd HH:mm:ss.SSS} %highlight{%5p} ${PID:-} [%15.15t] %-40.40logger{39} : %m%n
appender.file.type = File
appender.file.name = LogToFile
appender.file.fileName=logs/app.log
appender.file.layout.type=PatternLayout
appender.file.layout.pattern=[%-5level] %d{yyyy-MM-dd HH:mm:ss.SSS} [%t] %c{1} - %msg%n
rootLogger.level = info
rootLogger.appenderRef.stdout.ref = console
rootLogger.appenderRef.all.ref = LogToFile
#rootLogger.appenderRef.error.ref = RollingFileError
-Dlog4j.skipJansi=false
```