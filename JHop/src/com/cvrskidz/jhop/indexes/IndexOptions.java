package com.cvrskidz.jhop.indexes;

import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;
import java.util.StringTokenizer;

public class IndexOptions implements Serializable{
    public final static int MEMBER_COUNT = 4;
    private boolean encoded;
    private String name, attribute, value;
    
    public IndexOptions() {
        encoded = false;
        name = "";
        attribute = "";
        value = "";
    }
    
    public IndexOptions(String name, boolean encoded) {
        this.encoded = encoded;
        this.name = name;
    }
    
    public IndexOptions(String name, boolean encoded, String attribute, String value) {
        this(name, encoded);
        setQuery(attribute, value);
    }

    public static IndexOptions fromString(String s) throws IOException{
        StringTokenizer tokens = new StringTokenizer(s, ",");
        if(tokens.countTokens() != IndexOptions.MEMBER_COUNT) {
            throw new IOException("Receieved invalid index");
        }
        else {
            String name = tokens.nextToken();
            String attr = tokens.nextToken();
            String val = tokens.nextToken();
            boolean encoded = Boolean.valueOf(tokens.nextToken());
            return new IndexOptions(name, encoded, attr, val);
        }
    }
    
    public boolean isEncoded() {
        return encoded;
    }

    public void setEncoded(boolean encoded) {
        this.encoded = encoded;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getAttribute() {
        return attribute;
    }

    public void setQuery(String attribute, String value) {
        this.attribute = attribute;
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder(name); 
        out.append(",").append(attribute);
        out.append(",").append(value);
        out.append(",").append(encoded);
        return out.toString();
    }
    
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
