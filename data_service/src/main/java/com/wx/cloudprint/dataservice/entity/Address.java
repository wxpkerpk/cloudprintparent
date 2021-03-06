package com.wx.cloudprint.dataservice.entity;

import com.wx.cloudprint.util.JsonUtil;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
public class Address implements Serializable {


    @Id
    String id;
    String name;
    @OneToMany(targetEntity = Address.class,fetch = FetchType.EAGER)                                          //指定一对多关系
    @Cascade(value={CascadeType.ALL})         //设定级联关系
    @JoinColumn(name="children_id")
    List<Address> children=new ArrayList<>();

    @OneToOne(targetEntity = Address.class,fetch = FetchType.EAGER)
    Address parent;

    public Address getParent() {
        return parent;
    }

    public void setParent(Address parent) {
        this.parent = parent;
    }

    public void addChild(Address address){
        address.setParent(this);
        children.add(address);


    }
    boolean is_root =false;

    public boolean isIs_root() {
        return is_root;
    }

    public void setIs_root(boolean is_root) {
        this.is_root = is_root;
    }

    public static void main(String []s){
        Address address=new Address();
        address.setId("1");
        address.setName("北京市");
        Address address1=new Address("xx区1",null);
        address1.setId("2");

        Address address2=new Address("xx区2",null);
        address2.setId("3");


        List<Address>addressList=new ArrayList<>();
        addressList.add(address1);
        addressList.add(address2);

        Address address3=new Address("直辖区",addressList);
        address.getChildren().add(address3);
        address3.setId("4");

        System.out.println(JsonUtil.toJson(address));
       System.out.println(JsonUtil.toJson(address.toMap()));

    }
    public  Object toMap( )
    {
        Map<String,Object> map=new LinkedHashMap<>();
        String childName=name;
        if(children!=null&&children.size()>0) {
            List<Object>mapList=new LinkedList<>();

            for (Address ad : children) {
                mapList.add(ad.toMap());
            }
            map.put(childName,mapList);
            return map;
        }else{
            Map<String,String>stringMap=new LinkedHashMap<>();
            stringMap.put(childName,id);
            return stringMap;
        }
    }

    public String getName() {
        return name;
    }

    public Address(String name, List<Address> children) {
        this.name = name;
        if(children!=null)
        this.children = children;
    }

    public Address() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Address> getChildren() {
        return children;
    }

    public void setChildren(List<Address> children) {
        this.children = children;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
