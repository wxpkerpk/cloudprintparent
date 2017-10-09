package com.wx.cloudprint.dataservice.dao;

import com.wx.cloudprint.dataservice.entity.Order;
import com.wx.cloudprint.dataservice.entity.Point;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao extends PagingAndSortingRepository<Order, String>, JpaSpecificationExecutor<Order>{


}
