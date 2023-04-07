package Java.Java基础.树形结构;

import java.util.List;

public class Menu {

    private String id;
    private String name;
    private String pId;
    private List<Menu> children;

    public Menu(String id, String name, String pId, List<Menu> children) {
        this.id = id;
        this.name = name;
        this.pId = pId;
        this.children = children;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }
}