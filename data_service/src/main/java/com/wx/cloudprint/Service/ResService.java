package com.wx.cloudprint.Service;

import com.wx.cloudprint.Entity.Res;
import com.wx.cloudprint.dao.ResDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResService {
    @Autowired
    private ResDao resDao;
    public Res getByMD5(String md5){
        return resDao.findOne(md5);
    }
    @Transactional
    public void add(Res res){
        resDao.save(res);
    }

}
