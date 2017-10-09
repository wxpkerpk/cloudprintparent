package com.wx.cloudprint.dataservice.service;

import com.wx.cloudprint.dataservice.dao.OrderDao;
import com.wx.cloudprint.dataservice.dao.UserDao;
import com.wx.cloudprint.dataservice.entity.Order;
import com.wx.cloudprint.dataservice.entity.User;
import com.wx.cloudprint.dataservice.utils.EntityNameUtil;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {
    @Resource
    private OrderDao orderDao;

    @Transactional
    public void add(Order order){
        orderDao.save(order);
    }
    @Autowired
    private SessionFactory sessionFactory;
    @Transactional
    public void delete(Order order){
        orderDao.delete(order);
    }
    public Order get(String id){
        return orderDao.findOne(id);
    }
    public List<Order> getOrders(Integer page,Integer rows,String type){
        String sql=String.format("from "+ EntityNameUtil.getEntityName(Order.class) +" u where u.payState='%s'",type);

        return (List<Order>) sessionFactory.getCurrentSession().createQuery(sql).setFirstResult((page-1)*rows).setMaxResults(rows).list();
    }
    public Map<String,Integer>getStatics(String userId){

        String sql=String.format("SELECT count(*) as numbers,user_id ,pay_state FROM orders WHERE user_id='%s' GROUP BY pay_state",userId);
        sessionFactory.getCurrentSession().createSQLQuery(sql).list();
        return null;


    }


}
