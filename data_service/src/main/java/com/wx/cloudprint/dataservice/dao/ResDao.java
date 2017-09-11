package com.wx.cloudprint.dataservice.dao;

import com.wx.cloudprint.dataservice.entity.Res;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface ResDao  extends PagingAndSortingRepository<Res, String>, JpaSpecificationExecutor<Res> {
}
