package com.wx.cloudprint.dataservice.service;

import com.wx.cloudprint.dataservice.dao.AddressDao;
import com.wx.cloudprint.dataservice.entity.Address;

import javax.annotation.Resource;

public class AddressService {
    @Resource
    AddressDao addressDao;


    public Address getRoot()
    {
        return addressDao.findOne("0");

    }
}
