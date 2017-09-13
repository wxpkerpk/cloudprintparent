package com.wx.cloudprint.dataservice.entity;

import java.util.Date;
import javax.persistence.*;

@Entity(name = "res")
public class Res {
    @Id
    String md5;

    String name;
    Date time;
    Boolean isDirection;
    String host;
    String port;
    int page;

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Boolean getDirection() {
        return isDirection;
    }

    public void setDirection(Boolean direction) {
        isDirection = direction;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
