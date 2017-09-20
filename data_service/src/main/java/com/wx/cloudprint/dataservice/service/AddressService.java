package com.wx.cloudprint.dataservice.service;

import com.wx.cloudprint.dataservice.dao.AddressDao;
import com.wx.cloudprint.dataservice.entity.Address;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AddressService {
    @Resource
    AddressDao addressDao;


    public Address getRoot()
    {
        return addressDao.findOne("1");

    }

    public void add(Address address){

        addressDao.save(address);
    }
}
