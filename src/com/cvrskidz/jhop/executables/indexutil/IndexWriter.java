package com.cvrskidz.jhop.executables.indexutil;

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
    
    public IndexWriter(List<String> argv) {
        super(argv, OPNAME);
        this.priority = PRIORITY;
    }
    
    @Override
    public Index exec(Index index) {
        System.out.println("Writing " + indexName + ": " + index);
        
        if(!updateConfig()) return index;
        
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
    
    private boolean updateConfig() {
        Deque<IndexOptions> cache = updatedIndexes();
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
    
    private Deque<IndexOptions> updatedIndexes() {
        IndexOptions newIndex = new IndexOptions(indexName, false);
        
        try {
            Deque<IndexOptions> cache = IndexReader.getStoredIndexes(IndexOperation.PATH);
            if(cache.contains(newIndex)) cache.remove(newIndex);
            cache.add(newIndex);
            return cache;
        }
        catch(IOException e) {
            setError(e, "Invalid configuration file: ");
        }
        
        return null;
    }
}
