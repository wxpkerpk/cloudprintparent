package com.wx.cloudprint.dataservice.service;

import com.wx.cloudprint.dataservice.dao.AbstractDBCommonOperate;
import com.wx.cloudprint.dataservice.dao.PointDao;
import com.wx.cloudprint.dataservice.entity.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PointService {
    @Autowired
    private AbstractDBCommonOperate abstractDBCommonOperate;
    @Resource
    private PointDao pointDao;
    @Transactional
    public void add(Point point){
        pointDao.save(point);
    }
    @Transactional
    public void delete(String id) {
        pointDao.delete(id);
    }


    public List<Point> getAll() {
        return abstractDBCommonOperate.find(Point.class, null, null);
    }

    public Point get(String id){
        return pointDao.findOne(id);
    }
    public List<Point> getByAddressId(String addressId){
        return pointDao.findByAddressId(addressId);
    }


}
