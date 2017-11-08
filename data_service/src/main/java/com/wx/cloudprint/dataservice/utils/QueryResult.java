package com.wx.cloudprint.dataservice.utils;

import java.io.Serializable;
import java.util.List;

/**
 * @Create By Wx
 */
public class QueryResult<E> implements Serializable {
    private static final long serialVersionUID = -2243564171336800410L;

    private List<E> resultList;
    private Long totalCount;

    public List<E> getResultList() {
        return resultList;
    }

    public void setResultList(List<E> resultList) {
        this.resultList = resultList;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

}
