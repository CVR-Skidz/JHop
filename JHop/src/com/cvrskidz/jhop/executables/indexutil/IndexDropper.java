package com.cvrskidz.jhop.executables.indexutil;

import com.cvrskidz.jhop.exceptions.CommandException;
import com.cvrskidz.jhop.executables.Operation;
import com.cvrskidz.jhop.indexes.Index;
import com.cvrskidz.jhop.indexes.IndexOptions;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

/**
 * An IndexOperation that deletes a persistent index.
 * 
 * @author bcc9954 18031335 cvrskidz
 */
public class IndexDropper extends IndexOperation{
    public final static String OPNAME = "--drop";
    private final static int PRIORITY = Operation.MASTER_PR;
    
    /**
     * Constructs a new IndexDropper. An IndexDropper expects 1 argument.
     * 
     * @param argv The arguments supplied to this operation.
     * @throws CommandException  If the arguments were invalid
     */
    public IndexDropper(List<String> argv) throws CommandException {
        super(argv, OPNAME);
        this.priority = PRIORITY;
    }
    
    /**
     * Delete the specified Index.
     * 
     * @param index The Index to delete.
     * @return A new Index.
     */
    @Override
    public Index exec(Index index) {
        Deque<IndexOptions> indexes = updatedIndexes();
        
        if(indexes != null) updateIndexes(indexes);
          
        File cache = new File(indexName);
        cache.delete();
        return new Index();
    }
    
    /**
     * Remove the Index specified by this instance from JHops configuration.
     * 
     * @return A Deque of all the Index Options stored in JHops configuration
     * after removal.
     */
    private Deque<IndexOptions> updatedIndexes() {
        Deque<IndexOptions> indexes;
        
        try {
            indexes = IndexReader.getStoredIndexes(IndexOperation.PATH);
            IndexOptions query = new IndexOptions(indexName);
            indexes.remove(query);
            return indexes;
        }
        catch (IOException e) {
            setError(e, "Invalid configuration file: ");
            return null;
        }
    }
    
    /**
     * Write the supplied set of indexes to JHops configuration.
     * 
     * @param indexes The indexes JHop should store.
     */
    private void updateIndexes(Deque<IndexOptions> indexes) {
        try {
            Writer file = new FileWriter(IndexOperation.PATH);
            BufferedWriter bufferedFile = new BufferedWriter(file);
            Iterator<IndexOptions> it = indexes.iterator();
            
            while(it.hasNext()) {
                bufferedFile.write(it.next().toString());
                if(it.hasNext()) bufferedFile.write('\n');
            }
            
            bufferedFile.flush();
            bufferedFile.close();
        }
        catch(IOException e) {
            setError(e, "Invalid configuration file: ");
        }
    }
}
