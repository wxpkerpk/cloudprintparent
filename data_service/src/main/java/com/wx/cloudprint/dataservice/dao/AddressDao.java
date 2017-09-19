package com.wx.cloudprint.dataservice.dao;

import com.wx.cloudprint.dataservice.entity.Address;
import com.wx.cloudprint.dataservice.entity.Point;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface AddressDao extends PagingAndSortingRepository<Address, String>, JpaSpecificationExecutor<Address> {
}
