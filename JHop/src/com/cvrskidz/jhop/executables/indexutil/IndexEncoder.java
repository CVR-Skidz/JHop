package com.cvrskidz.jhop.executables.indexutil;

import com.cvrskidz.jhop.exceptions.CommandException;
import com.cvrskidz.jhop.executables.Operation;
import com.cvrskidz.jhop.indexes.Index;
import java.util.List;

public class IndexEncoder extends IndexOperation{
    public final static String OPNAME = "--encrypt";
    private final static int PRIORITY = Operation.MASTER_PR;
    
    public IndexEncoder(List<String> argv) throws CommandException {
        super(argv, OPNAME);
        priority = PRIORITY;
    }
    
    @Override
    public Index exec(Index index) {
        //stub
        System.out.println("Encoding " + indexName);
        return index;
    }
    
    public Index Decode() {
        //stub
        System.out.println("Decoding " + indexName);
        return null;
    }
}
