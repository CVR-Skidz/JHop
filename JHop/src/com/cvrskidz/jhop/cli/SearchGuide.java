package com.cvrskidz.jhop.cli;

import com.cvrskidz.jhop.exceptions.CommandException;
import com.cvrskidz.jhop.executables.Searchable;
import com.cvrskidz.jhop.executables.Searcher;
import com.cvrskidz.jhop.executables.Viewer;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * An interactable guide to accquire the relevant input from the user
 * to search the active index. Upon displaying this component the 
 * results will be printed to the standard output stream, and the user
 * can then select a page to open. The selected page will then be 
 * displayed as well.
 *
 * @author bcc9954 18031335 cvrskidz
 * @see com.cvrskidz.jhop.executables.Searcher
 */
public class SearchGuide extends Guide {
    private Searchable searcher, viewer;
    
    /**
     * Construct a new SearchGuide with the given input scanner.
     * @param input The Scanner instance associated with the parent interface.
     */
    public SearchGuide(Scanner input) {
        super(input);
        searcher = null;
        viewer = null;
    }

    /**
     * Prompt the user for a search term
     */ 
    @Override
    public void poll() {
        String term;
        
        // get term
        do {
            term = prompt("Enter search term: ");
        } while(term.isEmpty());
        
        // create command line arguments
        ArrayList<String> args = new ArrayList();
        args.add(term);
        
        try {
            searcher = new Searcher(args);
        }
        catch(CommandException e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Search the active index, displaying all results. Once all results
     * are printed the user is prompted to open a page.
     */
    @Override
    public void display() {
        if(searcher == null) return;
         
        Guide.index = searcher.exec(Guide.index);
        if(!searcher.success()) {
            System.out.println(searcher.getError().getMessage());
            return;
        }
        
        // has results?
        if(searcher.results() > 0) {
            do {
                String name = prompt("Enter page to open: ");
                if(!initViewer(name)) return;
                viewer.exec(Guide.index);
            } while(viewer.results() != 1);
        }
    }
    
    /**
     * Creates a new Viewer, concatenating the required options into a list of arguments.
     *
     * @see com.cvrskidz.jhop.exxecutables.Viewer
     */
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
