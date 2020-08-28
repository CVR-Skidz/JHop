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

/**
 * Read and load a persistent Index into the active JHop Index.
 * 
 * @author bcc9954 18031335 cvrskidz
 */
public class IndexReader extends IndexOperation{
    public final static String OPNAME = "--set";
    private final static int PRIORITY = 1;
    
    /**
     * Constructs a new IndexReader. An IndexReader expects 1 argument.
     * 
     * @param argv The arguments supplied to this operation.
     * @throws CommandException  If the arguments were invalid
     */
    public IndexReader(List<String> argv) throws CommandException {
        super(argv, OPNAME);
        priority = PRIORITY;
    }
    
    /**
     * Loads the specified Index.
     * 
     * @param index The current Index.
     * @return The loaded Index.
     */
    @Override
    public Index exec(Index index) {
        try {
            // check index exists
            Deque<IndexOptions> cache = getStoredIndexes(IndexOperation.PATH);
            IndexOptions target = new IndexOptions(indexName);
            if(!cache.contains(target)) {
                System.out.println("Could not find index in config: " + indexName);
                return index;
            }
            
            // deserialize
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
    
    /**
     * Read all persistent indexes stored in JHops configuration.
     * 
     * @param path The path of the configuration file.
     * @return A set of all IndexOptions stored in JHops configuration file.
     * @throws IOException If the configuration file could not be read.
     */
    public static Deque<IndexOptions> getStoredIndexes(String path) throws IOException{
        Deque<IndexOptions> cache = new ArrayDeque();
        Reader file = new FileReader(path);
        BufferedReader bufferedFile = new BufferedReader(file);
        String optionBuffer;    //buffer to store index at current line in config
        
        // read each index in config
        while((optionBuffer = bufferedFile.readLine()) != null) {
            IndexOptions options = IndexOptions.fromString(optionBuffer);
            cache.add(options);
        }
        
        return cache;
    }
}
