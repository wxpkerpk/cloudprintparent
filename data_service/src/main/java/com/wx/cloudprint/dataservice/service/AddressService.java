package com.wx.cloudprint.dataservice.service;

import com.wx.cloudprint.dataservice.dao.AddressDao;
import com.wx.cloudprint.dataservice.entity.Address;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AddressService {
    @Resource
    AddressDao addressDao;


    public List<Address> getRoot()
    {
        return addressDao.findRoot();

    }


    public void add(Address address){

        addressDao.save(address);
    }
}
