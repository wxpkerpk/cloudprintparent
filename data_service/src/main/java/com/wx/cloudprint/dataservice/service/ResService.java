package com.wx.cloudprint.dataservice.service;

import com.wx.cloudprint.dataservice.dao.ResDao;
import com.wx.cloudprint.dataservice.entity.Res;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class ResService {
    @Resource
    private ResDao resDao;
    public Res getByMD5(String md5){
        return resDao.findOne(md5);
    }
    @Transactional
    public void add(Res res){
        resDao.save(res);
    }

}
