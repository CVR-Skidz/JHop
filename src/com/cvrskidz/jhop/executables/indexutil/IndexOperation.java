package com.cvrskidz.jhop.executables.indexutil;

import com.cvrskidz.jhop.executables.Operation;
import java.util.List;

public abstract class IndexOperation extends Operation{
    protected String indexName;
    
    public IndexOperation(List<String> argv, String name) {
        super(argv, name);
        if(!argv.isEmpty()) indexName = argv.get(0);
    }
    
    public String getIndexName() {
        return indexName;
    }
}
