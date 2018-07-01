package com.turtlebot.operation.dataobject;

/**
 * Created by kewenkang on 2018/6/26/0026.
 */
public class Trolley {
    private Integer id;
    private String name;
    private Integer remainCapacity;
    private Float xAxis;
    private Float yAxis;
    private Integer isActive;
    public Trolley(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRemainCapacity() {
        return remainCapacity;
    }

    public void setRemainCapacity(Integer remainCapacity) {
        this.remainCapacity = remainCapacity;
    }

    public Float getxAxis() {
        return xAxis;
    }

    public void setxAxis(Float xAxis) {
        this.xAxis = xAxis;
    }

    public Float getyAxis() {
        return yAxis;
    }

    public void setyAxis(Float yAxis) {
        this.yAxis = yAxis;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "Trolley{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", remainCapacity=" + remainCapacity +
                ", xAxis=" + xAxis +
                ", yAxis=" + yAxis +
                ", isActive=" + isActive +
                '}';
    }
}
