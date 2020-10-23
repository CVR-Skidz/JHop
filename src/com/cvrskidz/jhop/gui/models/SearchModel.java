package com.cvrskidz.jhop.gui.models;

import com.cvrskidz.jhop.exceptions.CommandException;
import com.cvrskidz.jhop.executables.Searchable;
import com.cvrskidz.jhop.executables.Searcher;
import com.cvrskidz.jhop.gui.view.JHopView;
import com.cvrskidz.jhop.gui.view.JHopWebView;
import com.cvrskidz.jhop.indexes.IndexEntry;
import java.util.ArrayList;

/**
 * A model to search the active index.
 *
 * @author cvrskidz 18031335
 * @see com.cvrskidz.jhop.executables.Searcher
 */
public class SearchModel extends Model {
    private Searchable searcher;
    public final static int ARGC = 1;
    
    /**
     * Construct a new Model.
     */
    public SearchModel() {
        searcher = null;
    }

    @Override
    public void alert() {
        if(error != null) {
            Model.observer.showError(error.getMessage());
            return;
        }
        
        JHopWebView display = Model.observer.getDisplay();
        display.clearResults();
        if(searcher.results() == 0) return;
        
        //Populate the results view with search results
        for(IndexEntry e : ((Searcher)searcher).getResults()) {
            display.addResult(e);                
            display.getCanvas().switchTo(JHopView.RESULTS_LABEL);   //Switch to results
        }
    }
    
    /**
     * Search the active index, displaying all results. Once all results
     * are printed the user is prompted to open a page.
     * 
     * @param args This model expects only one argument, the term to search for.
     */
    @Override
    public void update(String... args) {
        if(args.length != ARGC) return;
         
        ArrayList<String> execArgs = new ArrayList<>(); //Add term to executable arguments
        execArgs.add(args[0]);
        
        try {
            //Create the searcher, and execute it on a new thread.
            searcher = new Searcher(execArgs);
            new Thread(this).start();
        }
        catch (CommandException e) {
            error = e;
            alert();
        }
    }
    
    /**
     * Execute this instances searcher as a runnable interface.
     */
    @Override
    public void run() {
        Model.toggleActive();
        
        Model.index = searcher.exec(Model.index);
        error = searcher.getError();
        alert();
        
        Model.toggleActive();
    }
}
