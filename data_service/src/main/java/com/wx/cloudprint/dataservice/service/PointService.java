package com.wx.cloudprint.dataservice.service;

import com.wx.cloudprint.dataservice.dao.PointDao;
import com.wx.cloudprint.dataservice.dao.ResDao;
import com.wx.cloudprint.dataservice.entity.Point;
import com.wx.cloudprint.dataservice.entity.Res;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PointService {
    @Resource
    private PointDao pointDao;
    @Transactional
    public void add(Point point){
        pointDao.save(point);
    }
    @Transactional
    public void delete(Point point){
        pointDao.delete(point);
    }
    public Point get(String id){
        return pointDao.findOne(id);
    }
    public List<Point> getByAddressId(String addressId){
        return pointDao.findByAddressId(addressId);
    }


}
