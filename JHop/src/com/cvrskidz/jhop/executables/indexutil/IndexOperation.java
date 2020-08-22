package com.cvrskidz.jhop.executables.indexutil;

import com.cvrskidz.jhop.exceptions.ArgumentException;
import com.cvrskidz.jhop.exceptions.CommandException;
import com.cvrskidz.jhop.executables.Operation;
import java.util.List;

public abstract class IndexOperation extends Operation{
    private static final int ARGC = 1;
    public static final String PATH = "sets";
    protected String indexName;
    
    public IndexOperation(List<String> argv, String name) throws CommandException {
        super(argv, name);
        init();
    }
    
    public String getIndexName() {
        return indexName;
    }
    
    @Override
    protected void init() throws CommandException {
        if(argc != ARGC) throw new ArgumentException(ARGC, this);
        else indexName = argv.get(0);
    }
}
