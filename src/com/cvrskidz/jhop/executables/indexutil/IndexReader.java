package com.cvrskidz.jhop.executables.indexutil;

import com.cvrskidz.jhop.indexes.Index;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.List;

public class IndexReader extends IndexOperation{
    public final static String OPNAME = "--set";
    private final static int PRIORITY = 1;
    
    public IndexReader(List<String> argv) {
        super(argv, OPNAME);
        this.priority = PRIORITY;
    }
    
    @Override
    public Index exec(Index index) {
        try {
            InputStream file = new FileInputStream(indexName);
            ObjectInputStream out = new ObjectInputStream(file);
            index = (Index)out.readObject();
            out.close();
        }
        catch (IOException | ClassNotFoundException e) {
            setError(e, "Error reading from file ");
        }
        
        System.out.println(indexName + ": " + index);
        return index;
    }
}
