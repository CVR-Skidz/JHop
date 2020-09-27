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

/**
 * Lists and displays the detail of an Index.
 * 
 * @author cvrskidz 18031335
 */
public class IndexInspector extends IndexOperation{
    public final static String OPNAME = "--describe";
    private final static int PRIORITY = Operation.MASTER_PR;
    
    /**
     * Constructs a new IndexInspector. An IndexInspector expects 1 argument.
     * 
     * @param argv The arguments supplied to this operation.
     * @throws CommandException  If the arguments were invalid
     */
    public IndexInspector(List<String> argv) throws CommandException {
        super(argv, OPNAME);
        this.priority = PRIORITY;
    }
    
    /**
     * List the details of the specified Index. If the index is empty or new
     * this lists all persistent indexes.
     * 
     * @param index The Index to describe.
     * @return A reference to the described index.
     */
    @Override
    public Index exec(Index index) {
        try {
            Deque<IndexOptions> cache = IndexReader.getStoredIndexes(IndexOperation.PATH);
            IndexOptions query = new IndexOptions(indexName);
            
            if(!indexName.isEmpty() && cache.contains(query)) {
                list(indexName);    // list single index
            }
            else {                  //list all indexes
                for(IndexOptions options: cache) list(options.getName());
            }
        }
        catch(IOException e) {
            setError(e, "Error reading config file ");
        }
        return index;
    }
    
    /**
     * List the details of the specified index.
     * 
     * @param name The name of the index.
     */
    private void list(String name) {
        List<String> args = new LinkedList();
        args.add(name);
        try {
            IndexReader reader = new IndexReader(args);
            Index result = new Index();
            result = reader.exec(result);               // read index from disk
            System.out.println("\n" + name + ":");      // header
            System.out.println(result);                 // index details
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
            indexName = "";
        }
        else if(argc != expected) throw new ArgumentException(expected, this);
        else indexName = argv.get(0);
    }
}
