package com.cvrskidz.jhop.indexes;

import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;
import java.util.StringTokenizer;

/**
 * This class stores all supported options of an index in JHop,
 * including the name and query supplied when creating an index.
 * 
 * @author cvrskidz 18031335
 */
public class IndexOptions implements Serializable{
    public final static int MEMBER_COUNT = 3;   // number of options
    private String name, attribute, value;      // options
    
    /**
     * Construct an empty set of IndexOptions.
     */
    public IndexOptions() {
        name = "";
        attribute = "";
        value = "";
    }
    
    /**
     * Construct an empty set of named IndexOptions.
     * @param name The name of the index.
     */
    public IndexOptions(String name) {
        this.name = name;
    }
    
    /**
     * Create a complete set of IndexOptions.
     * 
     * @param name The name of the index.
     * @param attribute The specified attribute in the query.
     * @param value The specified value in the query.
     */
    public IndexOptions(String name, String attribute, String value) {
        this(name);
        setQuery(attribute, value);
    }

    /**
     * Construct a new set of IndexOptions from the given string. The string
     * is expected to contain all option values delimitated by commas in the following
     * order: <pre>{name},{attribute},{value}.</pre>
     * 
     * @param s The string to create a set of options from.
     * @return A new IndexOptions instance.
     * @throws IOException If the given string was not in the expected format.
     */
    public static IndexOptions fromString(String s) throws IOException{
        StringTokenizer tokens = new StringTokenizer(s, ",");
        if(tokens.countTokens() != IndexOptions.MEMBER_COUNT) {
            throw new IOException("Receieved invalid index");
        }
        else {
            String name = tokens.nextToken();
            String attr = tokens.nextToken();
            String val = tokens.nextToken();
            return new IndexOptions(name, attr, val);
        }
    }

    /**
     * @return The name in this set of options.
     */
    public String getName() {
        return name;
    }

    /**
     * Updates the name stored in this set of options.
     * 
     * @param name The name to update this set of options with.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * @return The attribute in this set of options.
     */
    public String getAttribute() {
        return attribute;
    }

    /**
     * Updates the query stored in this set of options.
     * 
     * @param attribute The new attribute.
     * @param value The new value.
     */
    public void setQuery(String attribute, String value) {
        this.attribute = attribute;
        this.value = value;
    }

    /**
     * @return The value in this set of options.
     */
    public String getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder(name); 
        out.append(",").append(attribute);
        out.append(",").append(value);
        return out.toString();
    }
    
    /**
     * @param o Another object to compare this instance to.
     * @return True if the other object has the same name, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if(o instanceof IndexOptions) {
            IndexOptions other = (IndexOptions) o;
            return other.getName().equals(name);
        }
        
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.name);
        return hash;
    }
}
