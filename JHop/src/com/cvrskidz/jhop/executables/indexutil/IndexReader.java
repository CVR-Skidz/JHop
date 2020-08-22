package com.cvrskidz.jhop.executables.indexutil;

import com.cvrskidz.jhop.exceptions.CommandException;
import com.cvrskidz.jhop.indexes.IndexOptions;
import com.cvrskidz.jhop.indexes.Index;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Reader;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.List;

public class IndexReader extends IndexOperation{
    public final static String OPNAME = "--set";
    private final static int PRIORITY = 1;
    
    public IndexReader(List<String> argv) throws CommandException {
        super(argv, OPNAME);
        priority = PRIORITY;
    }
    
    @Override
    public Index exec(Index index) {
        try {
            Deque<IndexOptions> cache = getStoredIndexes(IndexOperation.PATH);
            IndexOptions target = new IndexOptions(indexName, false);
            if(!cache.contains(target)) {
                System.out.println("Could not find index in config: " + indexName);
                return index;
            }
            
            InputStream file = new FileInputStream(indexName);
            ObjectInputStream out = new ObjectInputStream(file);
            index = (Index)out.readObject();
            out.close();
        }
        catch (IOException | ClassNotFoundException e) {
            setError(e, "Error reading from file ");
        }
        
        return index;
    }
    
    public static Deque<IndexOptions> getStoredIndexes(String path) throws IOException{
        Deque<IndexOptions> cache = new ArrayDeque();
        Reader file = new FileReader(path);
        BufferedReader bufferedFile = new BufferedReader(file);
        String optionBuffer;
        
        while((optionBuffer = bufferedFile.readLine()) != null) {
            IndexOptions options = IndexOptions.fromString(optionBuffer);
            cache.add(options);
        }
        
        return cache;
    }
}
