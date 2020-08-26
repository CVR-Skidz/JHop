package com.cvrskidz.jhop.cli;

import com.cvrskidz.jhop.exceptions.CommandException;
import com.cvrskidz.jhop.executables.Searchable;
import com.cvrskidz.jhop.executables.Searcher;
import com.cvrskidz.jhop.executables.Viewer;
import java.util.ArrayList;
import java.util.Scanner;

public class SearchGuide extends Guide {
    private Searchable searcher, viewer;
    
    public SearchGuide(Scanner input) {
        super(input);
        searcher = null;
        viewer = null;
    }
     
    @Override
    public void poll() {
        String term;
        
        do {
            term = prompt("Enter search term: ");
        } while(term.isEmpty());
        
        ArrayList<String> args = new ArrayList();
        args.add(term);
        
        try {
            searcher = new Searcher(args);
        }
        catch(CommandException e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Override
    public void display() {
        if(searcher == null) return;
         
        Guide.index = searcher.exec(Guide.index);
        if(!searcher.success()) {
            System.out.println(searcher.getError().getMessage());
            return;
        }
        
        if(searcher.results() > 0) {
            do {
                String name = prompt("Enter page to open: ");
                if(!initViewer(name)) return;
                viewer.exec(Guide.index);
            } while(viewer.results() != 1);
        }
    }
    
    private boolean initViewer(String name) {
        ArrayList<String> args = new ArrayList();
        args.add(name);
        try {
            viewer = new Viewer(args);
            return true;
        }
        catch(CommandException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
