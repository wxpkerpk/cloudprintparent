package com.wx.cloudprint.dataservice.dao;

import com.wx.cloudprint.dataservice.entity.Point;
import com.wx.cloudprint.dataservice.entity.Res;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PointDao extends PagingAndSortingRepository<Point, String>, JpaSpecificationExecutor<Point>{
        @Query("from Point u where u.addressId=:addressId")
        Point findByAddressId(@Param(value = "addressId") String addressId);
}
