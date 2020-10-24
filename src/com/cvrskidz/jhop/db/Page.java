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
    
    /**
     * Construct a page consisting of an address and parent index.
     * 
     * @param indexName The index this page belongs to
     * @param url The address of this page
     * @param src The source of this page from the host
     * @param title The title of this page
     */
    public Page(String indexName, String url, String src, String title) {
        this.indexName = indexName;
        this.url = url;
        this.src = src;
        this.title = title;
    }

    /**
     * @return The index this page belongs to
     */
    public String getIndexName() {
        return indexName;
    }
    
    /**
     * @return The address of this page
     */
    public String getUrl() {
        return url;
    }

    /**
     * @return The source of this page from it's host
     */
    public String getSrc() {
        return src;
    }

    /**
     * @return The title of this page
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the index this page belongs to
     * 
     * @param indexName The name of the parent index
     */
    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }
    
    /**
     * Set the address of this page
     * 
     * @param url The given address
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Set the source of this page from it's host
     * 
     * @param src The given path
     */
    public void setSrc(String src) {
        this.src = src;
    }

    /**
     * Set the title of this page
     * 
     * @param title The given title
     */
    public void setTitle(String title) {
        this.title = title;
    }
}
