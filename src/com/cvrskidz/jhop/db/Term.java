package com.cvrskidz.jhop.db;

import java.io.Serializable;

/**
 * A Term contains the Java Object mapping for rows in the TERM table of the JHop 
 * database. It contains getter(s) and setter(s) for each column of the table
 * (indexName, term, page, and frequency), their mappings can be found in Indexes.hbm.xml.
 * 
 * @author cvrskidz 18031335
 */
public class Term implements Serializable{
    private String indexName;
    private String term;
    private String page;
    private int frequency;
    
    public Term() {
        //Hibernate reflection
    }
    
    public Term(String indexName, String term, String page, int frequency) {
        this.indexName = indexName;
        this.term = term;
        this.page = page;
        this.frequency = frequency;
    }

    public String getIndexName() {
        return indexName;
    }

    public String getTerm() {
        return term;
    }

    public String getPage() {
        return page;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
