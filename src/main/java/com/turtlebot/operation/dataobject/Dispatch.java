package com.turtlebot.operation.dataobject;

/**
 * Created by kewenkang on 2018/6/26/0026.
 */
public class Dispatch {
    private Integer id;
    private Integer itemId;
    private Integer trolleyId;
    private Integer isFinish;

    public Dispatch(){
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getTrolleyId() {
        return trolleyId;
    }

    public void setTrolleyId(Integer trolleyId) {
        this.trolleyId = trolleyId;
    }

    public Integer getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(Integer isFinish) {
        this.isFinish = isFinish;
    }

    @Override
    public String toString() {
        return "Dispatch{" +
                "id=" + id +
                ", itemId=" + itemId +
                ", trolleyId=" + trolleyId +
                ", isFinish=" + isFinish +
                '}';
    }
}
