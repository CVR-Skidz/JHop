package com.cvrskidz.jhop.indexes;

import java.io.IOException;
import java.util.Objects;
import java.util.StringTokenizer;

public class IndexOptions {
    public final static int MEMBER_COUNT = 2;
    private boolean encoded;
    private String name;
    
    public IndexOptions(String name, boolean encoded) {
        this.encoded = encoded;
        this.name = name;
    }

    public static IndexOptions fromString(String s) throws IOException{
        StringTokenizer tokens = new StringTokenizer(s, ",");
        if(tokens.countTokens() != IndexOptions.MEMBER_COUNT) {
            throw new IOException("Receieved invalid index");
        }
        else {
            String name = tokens.nextToken();
            boolean encoded = Boolean.valueOf(tokens.nextToken());
            return new IndexOptions(name, encoded);
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
    
    @Override
    public String toString() {
        return name + "," + encoded; 
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
