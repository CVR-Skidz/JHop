package com.cvrskidz.jhop.executables.indexutil;

import com.cvrskidz.jhop.executables.Operation;
import com.cvrskidz.jhop.indexes.Index;
import java.io.File;
import java.util.List;

public class IndexDropper extends IndexOperation{
    public final static String OPNAME = "--drop";
    private final static int PRIORITY = Operation.MASTER_PR;
    
    public IndexDropper(List<String> argv) {
        super(argv, OPNAME);
        this.priority = PRIORITY;
    }
    
    @Override
    public Index exec(Index index) {
        //stub
        System.out.println("Dropping " + indexName);
        File cache = new File(indexName);
        cache.delete();
        return new Index();
    }
}
