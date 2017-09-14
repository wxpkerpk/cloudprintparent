package com.wx.cloudprint.dataservice.entity;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Point {
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
     List<Integer>takeTime=new ArrayList<>();

     float minCharge;




}
