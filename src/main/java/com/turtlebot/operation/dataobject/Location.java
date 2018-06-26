package com.turtlebot.operation.dataobject;

/**
 * Created by kewenkang on 2018/6/26/0026.
 */
public class Location {
    private Integer goodsId;
    private Float goodsXAxis;
    private Float goodsYAxis;
    public Location(){}

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Float getGoodsXAxis() {
        return goodsXAxis;
    }

    public void setGoodsXAxis(Float goodsXAxis) {
        this.goodsXAxis = goodsXAxis;
    }

    public Float getGoodsYAxis() {
        return goodsYAxis;
    }

    public void setGoodsYAxis(Float goodsYAxis) {
        this.goodsYAxis = goodsYAxis;
    }

    @Override
    public String toString() {
        return "Location{" +
                "goodsId=" + goodsId +
                ", goodsXAxis=" + goodsXAxis +
                ", goodsYAxis=" + goodsYAxis +
                '}';
    }
}
