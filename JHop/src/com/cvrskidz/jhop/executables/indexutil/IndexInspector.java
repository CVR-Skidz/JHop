package com.cvrskidz.jhop.executables.indexutil;

import com.cvrskidz.jhop.exceptions.ArgumentException;
import com.cvrskidz.jhop.exceptions.CommandException;
import com.cvrskidz.jhop.executables.Operation;
import com.cvrskidz.jhop.indexes.Index;
import com.cvrskidz.jhop.indexes.IndexOptions;
import java.io.IOException;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class IndexInspector extends IndexOperation{
    public final static String OPNAME = "--describe";
    private final static int PRIORITY = Operation.MASTER_PR;
    private boolean listAll;
    
    public IndexInspector(List<String> argv) throws CommandException {
        super(argv, OPNAME);
        this.priority = PRIORITY;
    }
    
    @Override
    public Index exec(Index index) {
        try {
            Deque<IndexOptions> cache = IndexReader.getStoredIndexes(IndexOperation.PATH);
            IndexOptions query = new IndexOptions(indexName);
            if(!indexName.isEmpty() && cache.contains(query)) list(indexName);
            else {
                for(IndexOptions options: cache) list(options.getName());
            }
        }
        catch(IOException e) {
            setError(e, "Error reading config file ");
        }
        return index;
    }
    
    private void list(String name) {
        List<String> args = new LinkedList();
        args.add(name);
        try {
            IndexReader reader = new IndexReader(args);
            Index result = new Index();
            result = reader.exec(result);
            System.out.println(name + ":");
            System.out.println(result);
        }
        catch(CommandException e) {
            System.out.print("Detected missing set listed in config. ");
            System.out.println("Proceeding anyway");
        }
    }
    
    @Override
    protected void init() throws CommandException {
        int expected = IndexOperation.ARGC;
        if(argv.isEmpty()) {
            listAll = true;
            indexName = "";
        }
        else if(argc != expected) throw new ArgumentException(expected, this);
        else indexName = argv.get(0);
    }
}
