```java
   @Override
    public IPage<TcmcAlarm> eqAllAlarm(Map<String, Object> params) {
       // size 每页多少条
        long size = Long.parseLong(StringUtils.defaultIfBlank(String.valueOf(params.get("limit")), "10"));
        // 当前页
        long current = Long.parseLong(StringUtils.defaultIfBlank(String.valueOf(params.get("page")), "1"));
        }
        // 定义 QueryWrapper 
        QueryWrapper<TcmcAlarm> q = new QueryWrapper<>();
        // 用户名(主表)
        Object userName = params.get("userName");
        // 部门名称
        Object alarmType = params.get("departmentName");
        // 用户名模糊查询(常规查询)
         if (null != userName && userName.toString().trim().length() > 0) {
            q.lambda().like(User::getUserName(), userName);
        }
        // 联接表 
        if(null != departmentName && departmentName.toString().trim().length() > 0){
         q.eq("dt.department_name", departmentName);
        }
        IPage<TcmcAlarm> page = baseMapper.selectUserpage(
                new Page<>(current, size), q);
        return page;

```