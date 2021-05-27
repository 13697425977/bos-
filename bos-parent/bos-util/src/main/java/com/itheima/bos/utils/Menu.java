package com.itheima.bos.utils;

import java.io.Serializable;

public class Menu implements Serializable {

    private String id;
    private String pId;
    private String name;
    private String page;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpId() {
        return pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id='" + id + '\'' +
                ", pId='" + pId + '\'' +
                ", name='" + name + '\'' +
                ", page='" + page + '\'' +
                '}';
    }
}
