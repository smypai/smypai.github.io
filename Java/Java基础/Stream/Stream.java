package Java.Java基础.Stream;

public class Stream {
    Stream<Long> keyStream = adminMap.keySet().stream();
    Stream<Boolean> valueStream = adminMap.values().stream();
    Stream<Map.Entry<Long, Boolean>> entryStream = adminMap.entrySet().stream();

    //单一条件 筛选
    admins.stream().filter(s->s.getAdminId().equals(1)).collect(Collectors.toList());
    //多条件 筛选
    admins.stream().filter(s->s.getAdminId().equals(1) && s.getAdminStatus().equals(1)).collect(Collectors.toList());

}
