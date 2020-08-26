package com.cvrskidz.jhop.cli;

import com.cvrskidz.jhop.exceptions.CommandException;
import com.cvrskidz.jhop.executables.Executable;
import com.cvrskidz.jhop.executables.indexutil.IndexInspector;
import java.util.ArrayList;
import java.util.Scanner;

public class ListGuide extends Guide {
    private Executable inspector;
    
    public ListGuide(Scanner input) {
        super(input);
        inspector = null;
    }
    
    @Override
    public void poll() {
        try {
            ArrayList<String> args = new ArrayList<String>();
            args.add(""); //empty args supplied to inspector
            inspector = new IndexInspector(args); 
        }
        catch(CommandException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
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
