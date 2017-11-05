package com.wx.cloudprint.dataservice.dao;

import com.wx.cloudprint.dataservice.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends PagingAndSortingRepository<User, String>, JpaSpecificationExecutor<User>{
    @Query("from User u where u.shulianId = ?1")
    User findByShulianId(String shulianId);
}
