package com.cvrskidz.jhop.cli;

import com.cvrskidz.jhop.exceptions.CommandException;
import com.cvrskidz.jhop.executables.Executable;
import com.cvrskidz.jhop.executables.indexutil.IndexReader;
import java.util.ArrayList;
import java.util.Scanner;

public class SetGuide extends Guide {
    private Executable reader;
    
    public SetGuide(Scanner input) {
        super(input);
        reader = null;
    }
    
    @Override
    public void poll() {
        String name;
        
        do {
            name = prompt("Enter index name: ");
        } while(name.isEmpty());
        
        ArrayList<String> args = new ArrayList();
        args.add(name);
        
        try {
            reader = new IndexReader(args);
        }
        catch (CommandException e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Override 
    public void display() {
        if(reader == null) return;
        
        Guide.index = reader.exec(Guide.index);
        if(!reader.success()) {
            System.out.println(reader.getError().getMessage());
        }
    }
}
