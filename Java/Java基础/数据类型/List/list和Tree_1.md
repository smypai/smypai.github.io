```java
package _20230315_listToTree;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JeepCar implements Serializable {
    private static final long serialVersionUID = 1L;
    @JSONField(name = "car_id")
    private Integer carId ;
    @JSONField(name = "car_name")
    private String carName ;
    @JSONField(name = "parent_id")
    private Integer parentId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<JeepCar> children;

    //准换成树
    public static List<JeepCar> getTree(List<JeepCar> list, Integer pid){
        List<JeepCar> tree = new ArrayList<>();
        for(JeepCar car : list){
            if(car.getParentId().equals(pid)){
                car.setChildren(getTree(list, car.getCarId()));
                tree.add(car);
            }
        }
        return tree;
    }

    public JeepCar(Integer carId, String carName, Integer parentId) {
        this.carId = carId;
        this.carName = carName;
        this.parentId = parentId;
    }
}

```