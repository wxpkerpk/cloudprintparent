package com.wx.cloudprint.dataservice.dao;

import com.wx.cloudprint.dataservice.entity.Address;
import com.wx.cloudprint.dataservice.entity.Point;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface AddressDao extends PagingAndSortingRepository<Address, String>, JpaSpecificationExecutor<Address> {
    @Query("from Address u where u.is_root=true")
    List<Address> findRoot();

}
