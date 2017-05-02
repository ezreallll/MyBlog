package com.hp.blog.entity;

public class BlogTypeDto extends DbBaseObject<Integer>{

    private String remark;



    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}