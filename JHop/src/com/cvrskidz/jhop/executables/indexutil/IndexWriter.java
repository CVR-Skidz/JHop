package com.cvrskidz.jhop.executables.indexutil;

import com.cvrskidz.jhop.exceptions.CommandException;
import com.cvrskidz.jhop.indexes.Index;
import com.cvrskidz.jhop.indexes.IndexOptions;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

public class IndexWriter extends IndexOperation{
    public final static String OPNAME = "--create";
    private final static int PRIORITY = 2;
    
    /**
     * Constructs a new IndexReader. An IndexReader expects 1 argument.
     * 
     * @param argv The arguments supplied to this operation.
     * @throws CommandException  If the arguments were invalid
     */
    public IndexWriter(List<String> argv) throws CommandException {
        super(argv, OPNAME);
        priority = PRIORITY;
    }
    
    /**
     * Save the specified Index.
     * 
     * @param index The Index to write to disk.
     * @return The Index saved.
     */
    @Override
    public Index exec(Index index) {
        index.getOptions().setName(indexName);  // ensure configuration contains correct name
        if(!updateConfig(index)) return index;  // failure to update configuration
        
        // serialize index
        try {
            OutputStream file = new FileOutputStream(indexName);
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(index);
            out.flush();
            out.close();
        }
        catch (IOException e) {
            setError(e, "Error writing to file " + indexName);
        }
        
        return index;
    }
    
    /**
     * Insert the specified index into JHops configuration, or update it
     * if already present.
     * 
     * @param index The index to write.
     * @return True if the configuration was updated successfully. False otherwise. 
     */
    private boolean updateConfig(Index index) {
        Deque<IndexOptions> cache = updatedIndexes(index.getOptions()); // update
        if(cache == null) return false;
        
        // write updated indexes
        try {
            Writer file = new FileWriter(IndexOperation.PATH);
            BufferedWriter bufferedFile = new BufferedWriter(file);
            
            Iterator<IndexOptions> it = cache.iterator();
            while(it.hasNext()) {
                bufferedFile.write(it.next().toString());
                if(it.hasNext()) bufferedFile.write('\n');
            }
            
            bufferedFile.flush();
            bufferedFile.close();
            return true;
        }
        catch (IOException e) {
            setError(e, "Error writing to config " + IndexOperation.PATH);
            return false;
        }
    }
    
    /**
     * Overwrite the specified index configuration present on disk. 
     * 
     * @param options The options to update.
     * @return A Deque of all up to date IndexOptions, that have not yet been written to disk.
     */
    private Deque<IndexOptions> updatedIndexes(IndexOptions options) {
        try {
            Deque<IndexOptions> cache = IndexReader.getStoredIndexes(IndexOperation.PATH);
            cache.remove(options);  // remove if present
            cache.add(options);     // add up to date options
            return cache;
        }
        catch(IOException e) {
            setError(e, "Invalid configuration file: ");
        }
        
        return null;
    }
}
