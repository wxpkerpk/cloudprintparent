package com.wx.cloudprint.dataservice.service;

import com.wx.cloudprint.dataservice.dao.AbstractDBCommonOperate;
import com.wx.cloudprint.dataservice.dao.ResDao;
import com.wx.cloudprint.dataservice.entity.Res;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class ResService {
    @Resource
    private ResDao resDao;

    @Autowired
    AbstractDBCommonOperate abstractDBCommonOperate;

    public Res getByMD5(String md5){

        return abstractDBCommonOperate.find(Res.class, md5);
    }
    @Transactional
    public void add(Res res){
        resDao.save(res);
    }

}
