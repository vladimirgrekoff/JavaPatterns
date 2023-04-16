package com.grekoff.market.api.core;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class PageDto<T> {
    private List<T> items;
    private int page;
    private int totalPage;

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public PageDto() {
    }


}
