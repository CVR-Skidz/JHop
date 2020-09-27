package com.cvrskidz.jhop.cli;

import com.cvrskidz.jhop.exceptions.CommandException;
import com.cvrskidz.jhop.executables.Executable;
import com.cvrskidz.jhop.executables.indexutil.IndexDropper;
import com.cvrskidz.jhop.indexes.Index;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * An interactable guide to acquire the relevant input from the user
 * to delete a persistent index.
 *
 * @author cvrskidz 18031335
 * @see com.cvrskidz.jhop.executables.indexutil.IndexDropper
 */
public class DeleteGuide extends Guide {
    private Executable dropper;
    
    /**
     * Construct a new DeleteGuide with the given input scanner.
     * @param input The Scanner instance associated with the parent interface.
     */
    public DeleteGuide(Scanner input) {
        super(input);
        dropper = null;
    }
    
    /**
     * Prompt the user for an index name.
     *
     * @see com.cvrskidz.jhop.executables.indexutil.IndexDropper
     */
    @Override
    public void poll() {
        String name;
        
        // get name
        do {
            name = prompt("Enter index name: ");
        } while(name.isEmpty());
        
        // clear active index before user deletes it
        if(name.equals(Guide.index.getOptions().getName())) {
            Guide.index = new Index();
        }
        
        ArrayList<String> args = new ArrayList();
        args.add(name);
        
        try {
            dropper = new IndexDropper(args);
        }
        catch (CommandException e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Drops the specified index from JHop.
     */
    @Override
    public void display() {
        if(dropper == null) return;
        
        Guide.index = dropper.exec(Guide.index);
        if(!dropper.success()) {
            System.out.println(dropper.getError().getMessage());
        }
    }
}
