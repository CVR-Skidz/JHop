package com.cvrskidz.jhop.db;

import java.io.Serializable;

public class Page implements Serializable{
    private String url;
    private String src;
    private String title;
    
    public Page() {
        //Hibernate reflection
    }
    
    public Page(String url, String src, String title) {
        this.url = url;
        this.src = src;
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public String getSrc() {
        return src;
    }

    public String getTitle() {
        return title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
