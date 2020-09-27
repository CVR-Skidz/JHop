package com.cvrskidz.jhop.cli;

import com.cvrskidz.jhop.exceptions.CommandException;
import com.cvrskidz.jhop.executables.Executable;
import com.cvrskidz.jhop.executables.indexutil.IndexInspector;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * An interactable guide to list all persistent indexes.
 *
 * @author cvrskidz 18031335
 * @see com.cvrskidz.jhop.executables.indexutil.IndexInspector
 */
public class ListGuide extends Guide {
    private Executable inspector;
    
    /**
     * Construct a new ListGuide with the given input scanner.
     * @param input The Scanner instance associated with the parent interface.
     */
    public ListGuide(Scanner input) {
        super(input);
        inspector = null;
    }
    
    @Override
    public void poll() {
        // Create inspector
        try {
            ArrayList<String> args = new ArrayList<String>();
            args.add(""); //empty args supplied to inspector
            inspector = new IndexInspector(args); 
        }
        catch(CommandException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    /**
     * List all persistent indexes.
     */
    @Override 
    public void display() {
        System.out.println("Indexes: ");

        if(inspector == null) return;
        
        inspector.exec(Guide.index);
        if(!inspector.success()) {
            System.out.println(inspector.getError().getMessage());
        }
    }
}
