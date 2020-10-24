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
    
    /**
     * Construct a Term to store in JHop's database.
     * 
     * @param indexName The index the term belongs to
     * @param term The term itself
     * @param page The page it was indexed in
     * @param frequency The amount of times it appeared
     */
    public Term(String indexName, String term, String page, int frequency) {
        this.indexName = indexName;
        this.term = term;
        this.page = page;
        this.frequency = frequency;
    }

    /**
     * @return The index this term belongs to.
     */
    public String getIndexName() {
        return indexName;
    }

    /**
     * @return The term stored by this object.
     */
    public String getTerm() {
        return term;
    }

    /**
     * @return The page this term was indexed in
     */
    public String getPage() {
        return page;
    }

    /**
     * @return The amount of times this term was indexed
     */
    public int getFrequency() {
        return frequency;
    }

    /**
     * Set the index this term belongs to
     * 
     * @param indexName The name of the index
     */
    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    /**
     * Set the term this object stores
     * 
     * @param term The term itself
     */
    public void setTerm(String term) {
        this.term = term;
    }

    /**
     * Set the page this term was indexed in
     * 
     * @param page The pages source URL
     */
    public void setPage(String page) {
        this.page = page;
    }

    /**
     * Set the frequency of this term.
     * 
     * @param frequency The frequency of this term.
     */
    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
