package com.along.android.healthmanagement.entities;

import com.orm.SugarRecord;

/**
 * Created by wilber on 4/23/17.
 */

public class Note extends SugarRecord {
    Long id,uid;
    Long date;
    String title;
    String content;
    public Note(){};

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getId() {
        return id;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

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
        this.content = content;
    }
}
