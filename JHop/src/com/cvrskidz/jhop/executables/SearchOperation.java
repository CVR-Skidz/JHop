package com.cvrskidz.jhop.executables;

import java.util.List;

public abstract class SearchOperation extends Operation implements Searchable{
    protected int searchResults;
    
    public SearchOperation(List<String> argv, String name) {
        super(argv, name);
        searchResults = 0;
    }
    
    @Override
    public int results() {
        return searchResults;
    }
}
