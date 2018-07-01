package com.turtlebot.operation.service.trolleys;

import com.turtlebot.operation.dao.*;
import com.turtlebot.operation.dataobject.*;
import com.turtlebot.operation.service.traditional.IgniteTest;
import com.turtlebot.operation.utils.SSHshell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * @Auther: kewenkang
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
    private String command2 = "python /home/turtlebot/helloworld/turtlebot/get_trolley_location_on_map.py";
    private String command3 = "rostopic echo /odom | sed -n '11,12p'";


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

        String res = new SSHshell(host,user,psw,port,command3).exec();
        System.out.println(res);

//        Location goodsLocation = locationDAO.getLocationByGoods(indent.getOrderId());
//        System.out.println(goodsLocation);
//        System.out.println(getDistance(goodsLocation, null));

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

    /**
     *
     * @param location: location of goods
     * @param trolley: location of trolley
     * @return distance between goods and trolley
     */
    public Double getDistance(Location location, Trolley trolley){
//        Float xDiff = location.getGoodsXAxis() - trolley.getxAxis();
//        Float yDiff = location.getGoodsYAxis() - trolley.getyAxis();
        IgniteTest igniteTest = new IgniteTest();
        Float xDiff = location.getGoodsXAxis() - Float.valueOf(igniteTest.getKV(001));
        Float yDiff = location.getGoodsYAxis() - Float.valueOf(igniteTest.getKV(002));
        System.out.println("X:"+xDiff + "   Y:" + yDiff);
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

    @Override
    public void bestDispatch(){
        //get all avaliable trolleys
        List<Trolley> avaliableTrolleys = trolleyDAO.getAllActiveAndRemainTrolleys();
        //get not sent orderitems
        List<OrderItem> orderItems = getUnsentOrderItem();
        List<Dispatch> dispatches = new ArrayList<>();

        //print trolleys
        for (Trolley trolley: avaliableTrolleys){
            System.out.println(trolley.getName() + "'s coordinate: ("+ trolley.getxAxis() +", " + trolley.getyAxis() + "), current capacity: " + trolley.getRemainCapacity());
        }

        for (OrderItem orderItem: orderItems){

            List<Trolley> updatedTrolleys = new ArrayList<>();

            //get goods's location
            Location location = locationDAO.getLocationByGoods(orderItem.getGoodsid());
            //print orderitem
            System.out.println("\norderItem's coordinate: ("+ location.getGoodsXAxis() +", " + location.getGoodsYAxis() + ")");
            Double distance = Double.MAX_VALUE;
            Trolley trolleySelected = null;
            for (Trolley trolley: avaliableTrolleys){
                if (trolley.getRemainCapacity() == 0)
                    continue;
                Double curDistance = getDistance(location, trolley);
                if (curDistance < distance){
                    //record nearest trolley and distance
                    trolleySelected = trolley;
                    distance = curDistance;
                }
            }

            if (trolleySelected != null){
                Dispatch dispatch = new Dispatch();
                dispatch.setItemId(orderItem.getItemid());
                dispatch.setIsFinish(0);
                dispatch.setTrolleyId(trolleySelected.getId());
                dispatches.add(dispatch);
                //update trolley's capacity
                trolleySelected.setRemainCapacity(trolleySelected.getRemainCapacity() - 1);
                //print
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
                //update avaliable trolleys
                avaliableTrolleys = updatedTrolleys;

                //print
                for (Trolley trolley: avaliableTrolleys){
                    System.out.println(trolley.getName() + "'s coordinate: ("+ trolley.getxAxis() +", " + trolley.getyAxis() + "), current capacity: " + trolley.getRemainCapacity());
                }
            }else{
                System.out.println("No avaliable trolley at present.");
            }
        }

        //wirte dispatch into database
        for (Dispatch dispatch: dispatches){
            dispatchDAO.addDispatch(dispatch);
            //System.out.println(dispatch);
        }

        //update trolleys
        for (Trolley trolley: avaliableTrolleys){
            trolleyDAO.updateTrolley(trolley);
            //System.out.println(trolley);
        }
    }

    @Override
    public boolean updateTrolleyLocation(Trolley trolley) {
        String res = new SSHshell(host,user,psw,port,command2).exec();
        System.out.println(res);
        //parse result
        String[] splited = res.split(";");
        for (String line: splited){
            String[] fields = line.split(" ");
            Trolley trolley1 = trolleyDAO.getTrolleyById(Integer.valueOf(fields[0].trim()));
            trolley1.setxAxis(Float.valueOf(fields[1]));
            trolley1.setyAxis(Float.valueOf(fields[2]));
            trolleyDAO.updateTrolley(trolley1);
        }
        return false;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        new TrolleyServiceImpl().orderDispatch(null);
//        IgniteTest igniteTest = new IgniteTest();
//        igniteTest.setKV(1,"caonima,shabi");
//        igniteTest.setKV(2,"rinimahai");
    }
}
