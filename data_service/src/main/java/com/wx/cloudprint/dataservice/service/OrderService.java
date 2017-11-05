package com.wx.cloudprint.dataservice.service;

import com.wx.cloudprint.dataservice.dao.OrderDao;
import com.wx.cloudprint.dataservice.entity.Order;
import com.wx.cloudprint.dataservice.utils.EntityNameUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
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
        Session session= sessionFactory.openSession();
        String sql;
        if("ALL".equals(type)){
            sql=String.format("from "+ EntityNameUtil.getEntityName(Order.class) +"",type);

        }else if("FINISH".equals(type)){
            sql = "from " + EntityNameUtil.getEntityName(Order.class) + " u where u.payState='FINISH' OR u.payState='CANCEL' ";
        }else if("REFUND".equals("type")){
            sql = "from " + EntityNameUtil.getEntityName(Order.class) + " u where u.payState='REFUNDING' OR u.payState='REFUNDED' ";

        }
        else{
            sql=String.format("from " + EntityNameUtil.getEntityName(Order.class) + " u where u.payState='%s' ",type);

        }
        List<Order>orderList= (List<Order>) session.createQuery(sql).setFirstResult((page-1)*rows).setMaxResults(rows).list();
        session.close();
        return orderList;
    }
    public Map<String,Integer>getStatics(String userId){

       Session session= sessionFactory.openSession();
        String sql=String.format("SELECT count(*) as numbers,user_id ,pay_state FROM orders WHERE user_id='%s' GROUP BY pay_state",userId);
      List result=  session.createSQLQuery(sql).list();
      Map<String,Integer>resultMap=new HashMap<>();
        session.close();
        result.forEach(x->{
            Object[]values= (Object[])x;

            String key=values[2]==null?"":values[2].toString();
            Integer value=Integer.parseInt(values[0].toString());
            resultMap.put(key,value);


        });
        return resultMap;


    }


}
