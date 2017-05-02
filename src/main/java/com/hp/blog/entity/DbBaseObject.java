package com.hp.blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;


import java.io.Serializable;
import java.util.Date;


public class DbBaseObject<T> implements Serializable {

    public DbBaseObject() {
        this.init();
    }

    protected void init() {
        long now = System.currentTimeMillis();
        this.setModifyTime(new Date(now));
        this.version = now;
    }

    private T id;

    @JsonIgnore
    private long creator;

    @JsonIgnore
    private Date modifyTime;

    @JsonIgnore
    private long modifier;

    @JsonIgnore
    private long version;

    @JsonIgnore
    private int total;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public long getCreator() {
        return creator;
    }

    public void setCreator(long creator) {
        this.creator = creator;
    }

    public long getModifier() {
        return modifier;
    }

    public void setModifier(long modifier) {
        this.modifier = modifier;
    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }


    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
