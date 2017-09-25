package com.wx.cloudprint.dataservice.dao;

import com.wx.cloudprint.dataservice.entity.Point;
import com.wx.cloudprint.dataservice.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends PagingAndSortingRepository<User, String>, JpaSpecificationExecutor<User>{
        @Query("from User u where u.shulianId=:shulianId")
        User findByShulianId(String shulianId);
}
