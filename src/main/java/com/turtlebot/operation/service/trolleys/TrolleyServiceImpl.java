package com.turtlebot.operation.service.trolleys;

import com.turtlebot.operation.dao.*;
import com.turtlebot.operation.dataobject.*;
import com.turtlebot.operation.utils.SSHshell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * @Auther: davidddl
 * @Date: 2018/6/12 15:24
 * @Description:
 */
@Service
public class TrolleyServiceImpl implements TrolleyService {

    private Double x = -2.58;
    private Double y = 0.23;
    private String host = "192.168.199.124";
    private String user = "turtlebot";
    private Integer port = 22;
    private String psw = "1012";
    private String command = "python /home/turtlebot/helloworld/turtlebot/go_to_specific_point_on_map.py " + x + " " + y;

    @Autowired
    TrolleyDAO trolleyDAO;
    @Autowired
    LocationDAO locationDAO;
    @Autowired
    DispatchDAO dispatchDAO;
    @Autowired
    IndentDAO indentDAO;
    @Autowired
    OrderItemDAO orderItemDAO;

    @Override
    public Integer orderDispatch(Indent indent) {

        String res = new SSHshell(host,user,psw,port,command).exec();
        System.out.println(res);
        return 1;
    }

    @Override
    public List<OrderItem> getUnsentOrderItem(){
        List<Indent> indents = indentDAO.getNeedRecvIndents();
        List<OrderItem> orderItems = new ArrayList<>();
        for (Indent indent: indents){
            List<OrderItem> orderItemList = orderItemDAO.getOrderItemByOrderId(indent.getOrderId());
            for (OrderItem orderItem: orderItemList){
                orderItems.add(orderItem);
            }
        }
        return orderItems;
    }

    public Double getDistance(Location location, Trolley trolley){
        Float xDiff = location.getGoodsXAxis() - trolley.getxAxis();
        Float yDiff = location.getGoodsYAxis() - trolley.getyAxis();

        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

    @Override
    public void bestDispatch(){
        List<Trolley> avaliableTrolleys = trolleyDAO.getAllActiveAndRemainTrolleys();
        List<OrderItem> orderItems = getUnsentOrderItem();

        //print
        for (Trolley trolley: avaliableTrolleys){
            System.out.println(trolley.getName() + "'s coordinate: ("+ trolley.getxAxis() +", " + trolley.getyAxis() + "), current capacity: " + trolley.getRemainCapacity());
        }

        for (OrderItem orderItem: orderItems){


            List<Trolley> updatedTrolleys = new ArrayList<>();

            Location location = locationDAO.getLocationByGoods(orderItem.getGoodsid());
            System.out.println("\norderItem's coordinate: ("+ location.getGoodsXAxis() +", " + location.getGoodsYAxis() + ")");
            Double distance = Double.MAX_VALUE;
            Trolley trolleySelected = null;
            for (Trolley trolley: avaliableTrolleys){
                if (trolley.getRemainCapacity() == 0)
                    continue;
                Double curDistance = getDistance(location, trolley);
                if (curDistance < distance){
                    trolleySelected = trolley;
                    distance = curDistance;
                }
            }

            if (trolleySelected != null){
                Dispatch dispatch = new Dispatch();
                dispatch.setItemId(orderItem.getItemid());
                dispatch.setIsFinish(0);
                dispatch.setTrolleyId(trolleySelected.getId());
                trolleySelected.setRemainCapacity(trolleySelected.getRemainCapacity() - 1);
                System.out.println("------------ Dispatching -----------\nassign trolley: " + trolleySelected.getName() + " to get orderItem: "
                        + orderItem.getItemid() + ", goodsId: " + orderItem.getGoodsid() + ".");

                for (Trolley trolley: avaliableTrolleys){
                    if (trolley.getId().equals(trolleySelected.getId())){
//                        if (trolleySelected.getRemainCapacity() > 0)
                            updatedTrolleys.add(trolleySelected);
                    }else{
                        updatedTrolleys.add(trolley);
                    }
                }
                avaliableTrolleys = updatedTrolleys;

                //print
                for (Trolley trolley: avaliableTrolleys){
                    System.out.println(trolley.getName() + "'s coordinate: ("+ trolley.getxAxis() +", " + trolley.getyAxis() + "), current capacity: " + trolley.getRemainCapacity());
                }
            }else{
                System.out.println("No avaliable trolley at present.");
            }


        }
    }

    public static void main(String[] args) {
        new TrolleyServiceImpl().bestDispatch();
    }
}
