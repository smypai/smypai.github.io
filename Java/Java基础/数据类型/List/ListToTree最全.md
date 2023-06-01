```java 

// 1.基础数据结构

package com.happy.springboot.listtotree;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor()
public class Node {
    private static final long serialVersionUID = 1L;
    Integer id;
    String name;
    Integer parentId;
}

// 2.扩展数据结构

package com.happy.springboot.listtotree;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NodeEx extends Node implements Serializable {
    private static final long serialVersionUID = 1L;
    private String parentName;
    private List<NodeEx> children = new ArrayList<>();
    private Integer level = 0;

    @Override
    public String toString() {
        return "Node{" +
                "parentName='" + parentName + '\'' +
                ", children=" + children +
                ", level=" + level +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", parentId=" + parentId +
                '}';
    }
}

// 3.转换结构类型-工具类
package com.happy.springboot.listtotree;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NodeUtil {

    // 转成JSON 输出
    public static String getJson(Object data) {
        System.out.println(data);
        String s = JSON.toJSONString(data, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.DisableCircularReferenceDetect);
        System.out.println(s);
        return s;
    }


    // 扩展字段，数据类型转变
    public static List<NodeEx> getDto(List<Node> list) {
        List<NodeEx> dto = new ArrayList<>();
        for (Node node : list) {
            NodeEx nodeEx = new NodeEx();
            BeanUtils.copyProperties(node, nodeEx);
            dto.add(nodeEx);
        }
        return dto;
    }

    // 获取类名称和层数
    public static List<NodeEx> getName(List<NodeEx> list) {
        Map<Integer, String> names = list.stream().collect(Collectors.toMap(NodeEx::getId,NodeEx::getName));
        List<NodeEx> nameList = new ArrayList<>();
        for (NodeEx nodeEx : list) {
            nodeEx.setParentName(names.getOrDefault(nodeEx.getParentId(),"--"));
            nodeEx.setLevel(getLevel(list, nodeEx.parentId));
            nameList.add(nodeEx);
        }
        return nameList;
    }

    // 获取所在层数
    public static Integer getLevel(List<NodeEx> list, Integer pid){
        int i =  0 ;
        while (true){
            NodeEx nodeEx = getParent(list,pid);
            if(ObjectUtils.isEmpty(nodeEx)) break;
            pid = nodeEx.getParentId();
            i++ ;
        }
        return i ;
    }

    // 检查父节点是否存在
    public static NodeEx getParent(List<NodeEx> list, Integer pid){
        for(NodeEx nodeEx: list){
            if(nodeEx.getId().equals(pid)){
                return nodeEx;
            }
        }
        return null;
    }


    // 转换成树形
    public static List<NodeEx> getTree(List<NodeEx> list, int index) {
        List<NodeEx> tree = new ArrayList<>();
        for (NodeEx nodeEx : list) {
            if (nodeEx.getParentId().equals(index)) {
                nodeEx.setChildren(getTree(list, nodeEx.getId()));
                tree.add(nodeEx);
            }
        }
        return tree;
    }

    // 树形转化为列表
    public static List<NodeEx> getList(List<NodeEx> tree){
        List<NodeEx> list = new ArrayList<>();
        for(NodeEx car : tree){
            if(car.getChildren().size()>0)   {
                list.addAll(getList(car.getChildren()));
                car.setChildren(new ArrayList<>());
            }
            list.add(car);
        }
        return list ;
    }


    // 获取节点最大层高
    public static int getTreeHeight(NodeEx one) {
        if (one == null) {
            return 0;
        }
        int maxHeight = 0;
        for (NodeEx child : one.getChildren()) {
            int childHeight = getTreeHeight(child);
            if (childHeight > maxHeight) {
                maxHeight = childHeight;
            }
        }
        return maxHeight + 1;
    }
}

// 4.实例
package com.happy.springboot.listtotree;

import java.util.ArrayList;
import java.util.List;

public class NodeTest {
    public static void main(String[] args) {
        List<Node> l = new ArrayList<>();
        l.add(new Node(1, "0-1",0));
        l.add(new Node(2, "1-2",1));
        l.add(new Node(3, "2-3",2));
        l.add(new Node(4, "0-4",0));
        l.add(new Node(5, "2-5",2));

        // 扩展信息 child name level
        List<NodeEx> nodeExList = NodeUtil.getDto(l);

        // 获取 name level
        List<NodeEx> nameList = NodeUtil.getName(nodeExList);

        // to tree
        List<NodeEx> treeList = NodeUtil.getTree(nodeExList, 0);
    }
}
