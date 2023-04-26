```java
Class Car{
    private int id ;
    private String name ;
    private int parentId;
    private List<Car> childrend;
}

public List<Car> getTree(List<Car> list ,int pid){
    List<Car> tree = new ArrayList();
    for(Car car : list){
        if(car.parentId().equst(pid)){
            car.setChildrend(getTree(list,car.getId())));
            tree.add(car)        
        }    
    }
    return tree ;
}

public List<Car> getlist(List<Car> tree){
    List<Car> list = new ArrayList();
    for(Car car : tree){
        if(car.getChildren().size()>0)   {
            list.addAll(getList(car.getChildren()));
            car.setChildren(null);                  
        }
        list.add(car);  
    }
    return list ;
}


```