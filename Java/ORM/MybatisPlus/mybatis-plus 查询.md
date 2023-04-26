```java
class com.test.Test{
    // 根据 ID 查询
    T selectById(Serializable id);
    // 根据 entity 条件，查询一条记录
    T selectOne(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

    // 查询（根据ID 批量查询）
    List<T> selectBatchIds(@Param(Constants.COLLECTION) Collection<? extends Serializable> idList);
    // 根据 entity 条件，查询全部记录
    List<T> selectList(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
    // 查询（根据 columnMap 条件）
    List<T> selectByMap(@Param(Constants.COLUMN_MAP) Map<String, Object> columnMap);
    // 根据 Wrapper 条件，查询全部记录
    List<Map<String, Object>> selectMaps(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
    // 根据 Wrapper 条件，查询全部记录。注意： 只返回第一个字段的值
    List<Object> selectObjs(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

    // 根据 entity 条件，查询全部记录（并翻页）
    IPage<T> selectPage(IPage<T> page, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
    // 根据 Wrapper 条件，查询全部记录（并翻页）
    IPage<Map<String, Object>> selectMapsPage(IPage<T> page, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
    // 根据 Wrapper 条件，查询总记录数
    Integer selectCount(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
}