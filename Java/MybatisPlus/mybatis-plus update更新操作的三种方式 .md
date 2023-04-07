### 1.@ 根据id更新
```User user = new User();
user.setUserId(1);
user.setAge(29);
userMapper.updateById(user);
```
### 2.@ 条件构造器作为参数进行更新
```//把名字为rhb的用户年龄更新为18，其他属性不变
UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
updateWrapper.eq("name","rhb");
User user = new User();
user.setAge(18);
userMapper.update(user, updateWrapper);
@ 假设只更新一个字段在使用updateWrapper 的构造器中也需要构造一个实体对象,这样比较麻烦。可以使用updateWrapper的set方法
//只更新一个属性，把名字为rhb的用户年龄更新为18，其他属性不变
UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
updateWrapper.eq("name","rhb").set("age", 18);
userMapper.update(null, updateWrapper);
```
### 3.@ lambda构造器
```
LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
lambdaUpdateWrapper.eq(User::getName, "rhb").set(User::getAge, 18);
Integer rows = userMapper.update(null, lambdaUpdateWrapper);
