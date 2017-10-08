package com.wx.cloudprint.dataservice.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class Order {

    public  enum States{
        PAYING("PAYING"),PAID("PAID"),CANCEL("CANCEL"),REFUNDING("REFUNDING"),REFUNDED("REFUNDED");
        private final String text;
        private States(final String text){
            this.text=text;
        }
        @Override
        public String toString(){
            return text;
        }
    }

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
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
    String payway;
    String payState;
    long printDate;

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
