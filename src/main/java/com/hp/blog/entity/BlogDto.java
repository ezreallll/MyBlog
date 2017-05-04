package com.hp.blog.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BlogDto extends DbBaseObject<Integer>{

    private String title;

    private String content;

    private String create_time;

    private int type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getCreate_time() {
        return create_time.substring(0,create_time.lastIndexOf("."));
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}