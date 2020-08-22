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
    
    public IndexWriter(List<String> argv) throws CommandException {
        super(argv, OPNAME);
        priority = PRIORITY;
    }
    
    @Override
    public Index exec(Index index) {
        index.getOptions().setName(indexName);
        if(!updateConfig(index)) return index;
        
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
    
    private boolean updateConfig(Index index) {
        Deque<IndexOptions> cache = updatedIndexes(index.getOptions());
        if(cache == null) return false;
        
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
    
    private Deque<IndexOptions> updatedIndexes(IndexOptions options) {
        try {
            Deque<IndexOptions> cache = IndexReader.getStoredIndexes(IndexOperation.PATH);
            cache.remove(options);
            cache.add(options);
            return cache;
        }
        catch(IOException e) {
            setError(e, "Invalid configuration file: ");
        }
        
        return null;
    }
}
