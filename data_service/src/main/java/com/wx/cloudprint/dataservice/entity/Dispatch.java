package com.wx.cloudprint.dataservice.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Dispatch {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
            String id;

    int maxPageCount;
    float distributionStart;
    float distributionCharge;
    String descr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMaxPageCount() {
        return maxPageCount;
    }

    public void setMaxPageCount(int maxPageCount) {
        this.maxPageCount = maxPageCount;
    }

    public float getDistributionStart() {
        return distributionStart;
    }

    public void setDistributionStart(float distributionStart) {
        this.distributionStart = distributionStart;
    }

    public float getDistributionCharge() {
        return distributionCharge;
    }

    public void setDistributionCharge(float distributionCharge) {
        this.distributionCharge = distributionCharge;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }
}
