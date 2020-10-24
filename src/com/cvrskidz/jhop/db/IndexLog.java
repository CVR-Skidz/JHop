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
        //used for reflection
    }

    /**
     * Construct an index log used to signify the presence of an index,
     * and categorize pages and terms.
     * 
     * @param indexName The name of the index
     * @param tag The attribute used to query tags in pages associated with this index
     * @param attribute The value of the attribute which crawlers and parsers should accept
     */
    public IndexLog(String indexName, String tag, String attribute) {
        this.indexName = indexName;
        this.tag = tag;
        this.attribute = attribute;
    }
    
    /**
     * @return The name of this index
     */
    public String getIndexName() {
        return indexName;
    }

    /**
     * @return The tag attribute used as this indexes query
     */
    public String getTag() {
        return tag;
    }

    /**
     * @return The value of the attribute used in this indexes query
     */
    public String getAttribute() {
        return attribute;
    }

    /**
     * Set the name of this index
     * 
     * @param indexName The new name of this index
     */
    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    /**
     * Set the attribute used as this indexes query
     * 
     * @param tag The attribute to query for
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * Set the value of this indexes query.
     * 
     * @param attribute The attribute value to query
     */
    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
}
