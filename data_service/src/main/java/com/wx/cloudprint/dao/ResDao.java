package com.wx.cloudprint.dao;

import com.wx.cloudprint.Entity.Res;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResDao  extends PagingAndSortingRepository<Res, String>, JpaSpecificationExecutor<Res> {
}
