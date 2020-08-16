package com.cvrskidz.jhop.executables.indexutil;

import com.cvrskidz.jhop.indexes.Index;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
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
}
