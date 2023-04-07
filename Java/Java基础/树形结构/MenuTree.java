package Java.Java基础.树形结构;

import java.util.ArrayList;
import java.util.List;

public class MenuTree {

    // 流程：数据库查出的菜单记录装载在承载菜单的列表中---
    // 构建树（获取根节点，遍历根节点，对每一个根节点构建子树）---得到最终树形菜单
    // 承载菜单的列表
    private List<Menu> menuList = new ArrayList<>();
    // 带参构造器，将数据库中的菜单数据记录，装载在我们承载菜单的列表中
    public MenuTree(List<Menu> menuList){
        this.menuList = menuList;
    }

    // 获取根节点
    public List<Menu> getRootNode(){
        List<Menu> rootNode = new ArrayList<>();
        for (Menu menu : menuList) {
            if (menu.getpId().equals("0")){
                rootNode.add(menu);
            }
        }
        return rootNode;
    }

    // 构建子树
    public Menu buildChildren(Menu rootNode){
        List<Menu> childrenTree = new ArrayList<>();
        for (Menu menu : menuList) {
            if (menu.getpId().equals(rootNode.getId())){
                childrenTree.add(buildChildren(menu));
            }
        }
        rootNode.setChildren(childrenTree);
        return rootNode;
    }

    // 构建树
    public List<Menu> buildTree(){
        List<Menu> menus = getRootNode();
        for (Menu menu : menus) {
            buildChildren(menu);
        }
        return menus;
    }
}