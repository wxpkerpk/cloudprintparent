package com.wx.cloudprint.dataservice.service;

import com.wx.cloudprint.dataservice.dao.PointDao;
import com.wx.cloudprint.dataservice.dao.UserDao;
import com.wx.cloudprint.dataservice.entity.Point;
import com.wx.cloudprint.dataservice.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {
    @Resource
    private UserDao userDao;
    @Transactional
    public void add(User user){
        userDao.save(user);
    }
    @Transactional
    public void delete(User user){
        userDao.delete(user);
    }
    public User get(String id){
        return userDao.findOne(id);
    }
    public User findByShulianId(String shulianId){
        return userDao.findByShulianId(shulianId);
    }


}
