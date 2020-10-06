package com.cvrskidz.jhop.db;

import java.io.Serializable;

public class IndexLog implements Serializable{
    private String indexName;
    private String tag;
    private String attribute;
    
    public IndexLog() {
        //Hibernate reflection
    }

    public IndexLog(String indexName, String tag, String attribute) {
        this.indexName = indexName;
        this.tag = tag;
        this.attribute = attribute;
    }
    
    public String getIndexName() {
        return indexName;
    }

    public String getTag() {
        return tag;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
}
