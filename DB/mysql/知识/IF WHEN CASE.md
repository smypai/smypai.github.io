```sql

1、IF( expr , v1 , v2 )
如果表达式expr的值为TURE,则IF()的返回值为v1；
如果表达式expr是值为FALSE,则IF()的返回值为v2。
IF()的返回值为数字值或者字符串，具体情况视其所在语境而定。
例1、使用IF()函数进行条件判断，输入语句如下：
select if(1>2,2,3),
if(1<2,'yes','no'),
if(strcmp('test','yes'),'no','yes');
结果如下：

那么这个 IF 有啥用处呢？举个例子：
例2、查找出班级为‘95033’的学生，如果是在1976年出生的话，就要标注为过关，否则淘汰。
那么对应的SQL语句该怎样去写呢
代码如下：
select*,if(year(sbirthday)='1976','过关','淘汰') as compete_result from  student where class='95033'
结果如下：

2、IFNULL(V1,V2) 函数
判断第一个参数V1是否为NULL：
如果V1不为空，直接返回V1；
如果V1为空，返回第二个参数 V2；
常用在算术表达式计算和组函数中，用来对null值进行转换处理(返回值是数字或者字符串)
例3、使用IFNULL()函数进行条件判断，输入语句如下：
代码如下：
select ifnull(1,2),ifnull(null,10),ifnull(1/0,'wrong');
结果如下：

注意：“1/0”的结果为空，因此ifnull(1/0,‘wrong’)返回字符串“wrong”。
3、CASE函数
CASE expr WHEN v1 THEN r1 [WHEN v2 THEN r2] [ELSE rn] END
该函数表示，如果expr值等于某个vn，则返回对应位置THEN后面的结果。如果与所有值都不相等，则返回ELSE后面的rn。
例4、有分数degree，degree>90则返回优秀，degree>70 and <90则返回中等，degree>60 and <70则返回及格
代码如下：
select sno,cno,degree,
(case when degree>=90 then '优秀'
when degree<90 and degree>70 then '中等'
when degree<=70 and degree>=60 then '及格'
else '不合格' end) as remark
from
score;
结果如下：

THEN后边的值与ELSE后边的值类型应一致，否则会报错。如下：
CASE SCORE WHEN 'A' THEN '优' ELSE 0 END
'优’和0数据类型不一致则报错：
[Err] ORA-00932: 数据类型不一致: 应为 CHAR, 但却获得 NUMBER
简单CASE WHEN函数只能应对一些简单的业务场景，而CASE WHEN条件表达式的写法则更加灵活。
例5、现要统计所有雇佣员工中，有多少男员工，多少女员工，并统计男员工中有几人是1953年出生的，女员工中有几人是1953年出生的，要求用一个SQL输出结果。
表结构如下：其中gender字段，F表示男生，M表示女生。emp_no表示员工编号。
代码如下：
select
sum(case when gender='F' then 1 else 0 end) as male_gender,
sum(case when gender='M' then 1 else 0 end) as female_gender,
sum(case when year(birth_date)='1953' and gender='F' then 1 else 0 end) as male_pass,
sum(case when year(birth_date)='1953' and gender='M' then 1 else 0 end) as female_pass
from
employees;
结果如下：

例6、经典行转列，并配合聚合函数做统计
现要求统计三种当前时间（to_date）下每个员工(emp_no)总共获得了多少工资sum(salary)，使用一条SQL语句输出结果
给出部分表格信息：
程序如下：
select emp_no,
sum(case when to_date='9999-01-01' then salary else 0 end) as a,
sum(case when to_date='2001-12-01' then salary else 0 end) as b,
sum(case when to_date='1997-06-23' then salary else 0 end) as c
from
salaries
group by emp_no;
结果如下：


    
```