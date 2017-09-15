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
    int maxPageCount;
    float distributionStart;
    float distributionCharge;
    String desc;

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
