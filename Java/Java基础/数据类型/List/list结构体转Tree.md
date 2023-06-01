### java list结构体 变成Tree ，同时统计层数


```java
import java.util.ArrayList;
import java.util.List;

class Node {
    public int id;
    public int parentId;
    public String name;
    public int level;
    public List<Node> children;

    public Node(int id, int parentId, String name, int level) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.level = level;
        this.children = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", name='" + name + '\'' +
                ", level=" + level +
                ", children=" + children +
                '}';
    }
}



import java.util.ArrayList;
import java.util.List;

public class TreeTest {

    public static void main(String[] args) {
        // 构建测试数据
        List<Node> nodeList = new ArrayList<>();
        nodeList.add(new Node(1, 0, "A", 1));
        nodeList.add(new Node(2, 1, "B", 2));
        nodeList.add(new Node(3, 1, "C", 2));
        nodeList.add(new Node(4, 2, "D", 3));
        nodeList.add(new Node(5, 2, "E", 3));
        nodeList.add(new Node(6, 3, "F", 3));
        nodeList.add(new Node(7, 3, "G", 3));

        // 转换为树形结构
        List<Node> treeList = buildTree(nodeList);

        System.out.println(treeList);
    }

    public static List<Node> buildTree(List<Node> nodeList) {
        List<Node> treeList = new ArrayList<>();
        for (Node node : nodeList) {
            if (node.parentId == 0) {
                treeList.add(node);
            } else {
                Node parent = findParent(treeList, node.parentId, node.level - 1);
                parent.children.add(node);
            }
        }
        return treeList;
    }

    public static Node findParent(List<Node> treeList, int parentId, int level) {
        for (Node node : treeList) {
            if (node.id == parentId && node.level == level) {
                return node;
            }
            if (!node.children.isEmpty()) {
                Node parent = findParent(node.children, parentId, level);
                if (parent != null) {
                    return parent;
                }
            }
        }
        return null;
    }
}

```