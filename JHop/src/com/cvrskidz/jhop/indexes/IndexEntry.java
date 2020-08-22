package com.cvrskidz.jhop.indexes;

import com.cvrskidz.jhop.network.HopConnection;
import java.io.Serializable;
import java.util.Objects;

public class IndexEntry implements Serializable{
    private final String url, title, src;
    
    public IndexEntry(HopConnection url) {
        this.url = url.getURLNoProtocol();
        src = url.getURL();
        title = titleFromUrl();
    }
    
    private String titleFromUrl() {
        boolean hasEndPoint = getUrl().contains("/");
        boolean hasType = getUrl().contains(".");
        
        if(hasEndPoint && hasType) {
            int endPoint = getUrl().lastIndexOf("/");
            int type = getUrl().lastIndexOf(".");
            if(type > endPoint) return url.substring(endPoint + 1, type);
            else if(!url.endsWith("/")) return url.substring(endPoint + 1);
        }
        
        return getUrl();
    } 

    public String getUrl() {
        return url;
    }
    
    public String getSource() {
        return src;
    }

    public String getTitle() {
        return title;
    }
    
    @Override
    public String toString() {
        return title;
    }
    
    @Override
    public boolean equals(Object o) {
        if(o instanceof IndexEntry) {
            IndexEntry other = (IndexEntry) o;
            return other.getUrl().equals(url);
        }
        
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.url);
        return hash;
    }
}
