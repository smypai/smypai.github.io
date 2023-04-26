```java
class Mybatis{
    @Test
    public void selectByWrapper1() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("name", "age").like("name", "雨");
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    } 
}
```