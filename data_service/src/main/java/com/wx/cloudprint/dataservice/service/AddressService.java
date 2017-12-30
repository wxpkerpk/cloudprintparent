package com.wx.cloudprint.dataservice.service;

import com.wx.cloudprint.dataservice.dao.AbstractDBCommonOperate;
import com.wx.cloudprint.dataservice.dao.AddressDao;
import com.wx.cloudprint.dataservice.entity.Address;
import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class AddressService {
    @Resource
    AddressDao addressDao;
    @Autowired
    AbstractDBCommonOperate abstractDBCommonOperate;


    @Autowired
    private SessionFactory sessionFactory;

    public List<Object> findAll() {
        Session session = sessionFactory.openSession();
        session.setCacheMode(CacheMode.IGNORE);
        List values = session.createSQLQuery("select * from address").list();

        session.flush();
        session.close();
        return values;

    }
    public List<Address> getRoot()
    {
        return addressDao.findRoot();

    }

    public Address get(String id) {

        return abstractDBCommonOperate.find(Address.class, id);

    }

    public void add(Address address){

        addressDao.save(address);
    }

    public void update(Address address) {

        addressDao.save(address);
    }

    public void delete(String id) {
        Session session = sessionFactory.openSession();
        session.setCacheMode(CacheMode.IGNORE);
        session.createSQLQuery("delete  from address where id=" + id).executeUpdate();

        session.flush();
        session.close();
    }
}
