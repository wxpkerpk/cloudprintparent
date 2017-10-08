package com.wx.cloudprint.dataservice.service;

import com.wx.cloudprint.dataservice.dao.OrderDao;
import com.wx.cloudprint.dataservice.dao.UserDao;
import com.wx.cloudprint.dataservice.entity.Order;
import com.wx.cloudprint.dataservice.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class OrderService {
    @Resource
    private OrderDao orderDao;
    @Transactional
    public void add(Order order){
        orderDao.save(order);
    }
    @Transactional
    public void delete(Order order){
        orderDao.delete(order);
    }
    public Order get(String id){
        return orderDao.findOne(id);
    }


}
