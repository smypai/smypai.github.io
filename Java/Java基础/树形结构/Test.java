package Java.Java基础.树形结构;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        // ----------------------测试的菜单数据--------------------------
        ArrayList<Menu> menus = new ArrayList<>();
        menus.add(new Menu("1","一级","0",null));
        menus.add(new Menu("2","一级","0",null));
        menus.add(new Menu("3","一级","0",null));
        menus.add(new Menu("4","二级","1",null));
        menus.add(new Menu("5","二级","1",null));
        menus.add(new Menu("6","二级","2",null));
        menus.add(new Menu("7","三级","4",null));
        menus.add(new Menu("8","三级","5",null));
        menus.add(new Menu("9","三级","6",null));
        // ----------------------测试的菜单数据--------------------------
        // 以上数据实际应由数据库提供，本次测试写死

        // 创建生成树的工具类，调用构造树方法得到树形菜单
        List<Menu> menuList = new MenuTree(menus).buildTree();

        // fastjson的JSON  将java对象转为JSON字符串
        String jsonString = JSON.toJSONString(menuList);
        System.out.println(jsonString);
    }

}