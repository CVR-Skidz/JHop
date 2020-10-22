package com.cvrskidz.jhop.db;

import java.io.Serializable;

/**
 * An IndexLog contains the Java Object mapping for rows in the DRIECTORY table
 * of the JHop database. It contains getter(s) and setter(s) for each column of the table
 * (indexName, tag, and attribute), their mappings can be found in Indexes.hbm.xml.
 * 
 * @author cvrskidz 18031335
 */
public class IndexLog implements Serializable{
    private String indexName;   // The name of the index
    private String tag;         // The tag query of the index
    private String attribute;   // The value query of the index
    
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
