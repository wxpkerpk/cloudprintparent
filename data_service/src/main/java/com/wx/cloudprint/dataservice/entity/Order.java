package com.wx.cloudprint.dataservice.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "orders")
public class Order implements Serializable {

    public  static enum States{
        PAYING("PAYING"),PAID("PAID"),CANCEL("CANCEL"),REFUNDING("REFUNDING"),REFUNDED("REFUNDED"),FINISH("FINISH"),PRINTED("PRINTED");
        private final String text;
         States(final String text){
            this.text=text;
        }
        @Override
        public String toString(){
            return text;
        }
    }

    @Id
    String id;
    @ManyToOne(targetEntity = User.class,cascade = CascadeType.ALL)
    User user;
    String dispatching;
    float money;
    @Column(length = 4000)
    String files;
    String pointId;
    String pointName;
    long orderDate;
    String payWay;
    String payState;
    long printDate;
    @Column(length = 4000)
    String settle;

    public String getSettle() {
        return settle;
    }

    public void setSettle(String settle) {
        this.settle = settle;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public long getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(long orderDate) {
        this.orderDate = orderDate;
    }


    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getPayState() {
        return payState;
    }

    public void setPayState(String payState) {
        this.payState = payState;
    }

    public long getPrintDate() {
        return printDate;
    }

    public void setPrintDate(long printDate) {
        this.printDate = printDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDispatching() {
        return dispatching;
    }

    public void setDispatching(String dispatching) {
        this.dispatching = dispatching;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public String getPointId() {
        return pointId;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId;
    }
}
