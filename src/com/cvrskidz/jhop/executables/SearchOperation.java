package com.cvrskidz.jhop.executables;

import java.util.List;

/**
 * A specialization of an Operation that ensures the number of results processed
 * during a call to `exec` can be accessed by other objects.
 * 
 * @author cvrskidz 18031335
 */
public abstract class SearchOperation extends Operation implements Searchable{
    protected int searchResults;
    
    /**
     * @see Operation#Operation(List, String) 
     */
    public SearchOperation(List<String> argv, String name) {
        super(argv, name);
        searchResults = 0;
    }
    
    @Override
    public int results() {
        return searchResults;
    }
}
