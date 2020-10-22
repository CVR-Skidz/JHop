package com.cvrskidz.jhop.db;

import java.io.Serializable;

/**
 * A Page contains the Java Object mapping for rows in the PAGE table
 * of the JHop database. It contains getter(s) and setter(s) for each column of the table
 * (indexName, url, src, and title), their mappings can be found in Indexes.hbm.xml.
 * 
 * @author cvrskidz 18031335
 */
public class Page implements Serializable{
    private String indexName;
    private String url;
    private String src;
    private String title;
    
    public Page() {
        //Hibernate reflection
    }
    
    public Page(String indexName, String url, String src, String title) {
        this.indexName = indexName;
        this.url = url;
        this.src = src;
        this.title = title;
    }

    public String getIndexName() {
        return indexName;
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

    public void setIndexName(String indexName) {
        this.indexName = indexName;
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
