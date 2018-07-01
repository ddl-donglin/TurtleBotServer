package com.turtlebot.operation.dataobject;

import java.util.Date;
import java.util.List;

/**
 * @Auther: davidddl
 * @Date: 2018/6/12 14:43
 * @Description:
 */
public class Indent {
    private Integer orderId;
    private Integer userId;
    private Date orderTime;
    private Float oldPrice;
    private Float newPrice;
    private Integer isPay;
    private Integer isSend;
    private Integer isReceive;
    private Integer isComplete;
    private Integer addressId;
    private List<Goods> goodsInfo;

    public List<Goods> getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(List<Goods> goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    @Override
    public String toString() {
        return "Indent{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", orderTime=" + orderTime +
                ", oldPrice=" + oldPrice +
                ", newPrice=" + newPrice +
                ", isPay=" + isPay +
                ", isSend=" + isSend +
                ", isReceive=" + isReceive +
                ", isComplete=" + isComplete +
                ", addressId=" + addressId +
                '}';
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Float getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(Float oldPrice) {
        this.oldPrice = oldPrice;
    }

    public Float getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(Float newPrice) {
        this.newPrice = newPrice;
    }

    public Integer getIsPay() {
        return isPay;
    }

    public void setIsPay(Integer isPay) {
        this.isPay = isPay;
    }

    public Integer getIsSend() {
        return isSend;
    }

    public void setIsSend(Integer isSend) {
        this.isSend = isSend;
    }

    public Integer getIsReceive() {
        return isReceive;
    }

    public void setIsReceive(Integer isReceive) {
        this.isReceive = isReceive;
    }

    public Integer getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(Integer isComplete) {
        this.isComplete = isComplete;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }
}
