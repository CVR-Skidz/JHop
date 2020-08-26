package com.cvrskidz.jhop.cli;

import com.cvrskidz.jhop.exceptions.CommandException;
import com.cvrskidz.jhop.executables.Executable;
import com.cvrskidz.jhop.executables.indexutil.IndexDropper;
import com.cvrskidz.jhop.indexes.Index;
import java.util.ArrayList;
import java.util.Scanner;

public class DeleteGuide extends Guide {
    private Executable dropper;
    
    public DeleteGuide(Scanner input) {
        super(input);
        dropper = null;
    }
    
    @Override
    public void poll() {
        String name;
        
        do {
            name = prompt("Enter index name: ");
        } while(name.isEmpty());
        
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
    
    @Override
    public void display() {
        if(dropper == null) return;
        
        Guide.index = dropper.exec(Guide.index);
        if(!dropper.success()) {
            System.out.println(dropper.getError().getMessage());
        }
    }
}
