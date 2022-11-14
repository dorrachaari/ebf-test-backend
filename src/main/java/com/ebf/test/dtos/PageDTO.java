package com.ebf.test.dtos;

import java.io.Serializable;
import java.util.List;

public class PageDTO<T> extends AbstractDTO implements Serializable {

    private List<T> list;

    private Integer page;

    private Integer pageSize;

    private Long total;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
