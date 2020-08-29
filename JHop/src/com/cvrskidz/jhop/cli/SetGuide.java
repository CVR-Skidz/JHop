package com.cvrskidz.jhop.cli;

import com.cvrskidz.jhop.exceptions.CommandException;
import com.cvrskidz.jhop.executables.Executable;
import com.cvrskidz.jhop.executables.indexutil.IndexReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * An interactable guide to load an index into the active index. 
 *
 * @author bcc9954 18031335 cvrskidz
 * @see com.cvrskidz.jhop.executables.indexutil.IndexReader
 */
public class SetGuide extends Guide {
    private Executable reader;
    
    /**
     * Construct a new DeleteGuide with the given input scanner.
     * @param input The Scanner instance associated with the parent interface.
     */
    public SetGuide(Scanner input) {
        super(input);
        reader = null;
    }
    
    /**
     * Prompt the user for the index to load.
     */
    @Override
    public void poll() {
        String name;
        
        // get name of index
        do {
            name = prompt("Enter index name: ");
        } while(name.isEmpty());
        
        // create command line arguments
        ArrayList<String> args = new ArrayList();
        args.add(name);
        
        try {
            reader = new IndexReader(args);
        }
        catch (CommandException e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Load the index from disk.
     */
    @Override 
    public void display() {
        if(reader == null) return;
        
        Guide.index = reader.exec(Guide.index);
        if(!reader.success()) {
            System.out.println(reader.getError().getMessage());
        }
    }
}
