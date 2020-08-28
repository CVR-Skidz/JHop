package com.cvrskidz.jhop.executables.indexutil;

import com.cvrskidz.jhop.exceptions.ArgumentException;
import com.cvrskidz.jhop.exceptions.CommandException;
import com.cvrskidz.jhop.executables.Operation;
import java.util.List;

/**
 * A specialized Operation to provide utility functions to interact with persistent indexes.
 * 
 * @author bcc9954 18031335 cvrskidz
 */
public abstract class IndexOperation extends Operation{
    public static final String PATH = "sets";
    protected String indexName;
    protected static final int ARGC = 1;
    
    /**
     * Constructs a new IndexOperation. An IndexOperation expects 1 argument.
     * 
     * @param argv The arguments supplied to this operation.
     * @throws CommandException  If the arguments were invalid
     */
    public IndexOperation(List<String> argv, String name) throws CommandException {
        super(argv, name);
        init();
    }
    
    /**
     * @return The name of the targeted/active index. 
     */
    public String getIndexName() {
        return indexName;
    }
    
    @Override
    protected void init() throws CommandException {
        if(argc != ARGC) throw new ArgumentException(ARGC, this);
        else indexName = argv.get(0);
    }
}
