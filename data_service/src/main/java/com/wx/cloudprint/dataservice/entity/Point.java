package com.wx.cloudprint.dataservice.entity;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Point implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    String id;
    String status;
    String delivery_scope;
    String delivery_time;
    String phone;
     String pointName;
     String address;
     String message;
     String image;
     String price;
     String addressId;
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }

    String take_time;

    public String getTake_time() {
        return take_time;
    }

    public void setTake_time(String take_time) {
        this.take_time = take_time;
    }

    @OneToOne(targetEntity = Dispatch.class,fetch = FetchType.EAGER,cascade = CascadeType.ALL)
     Dispatch dispatch;
     float minCharge;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDelivery_scope() {
        return delivery_scope;
    }

    public void setDelivery_scope(String delivery_scope) {
        this.delivery_scope = delivery_scope;
    }

    public String getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(String delivery_time) {
        this.delivery_time = delivery_time;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public Dispatch getDispatch() {
        return dispatch;
    }

    public void setDispatch(Dispatch dispatch) {
        this.dispatch = dispatch;
    }

    public float getMinCharge() {
        return minCharge;
    }

    public void setMinCharge(float minCharge) {
        this.minCharge = minCharge;
    }
}
