package com.cvrskidz.jhop.gui.models;

import com.cvrskidz.jhop.db.indexutil.DatabaseIndexReader;
import com.cvrskidz.jhop.exceptions.CommandException;
import com.cvrskidz.jhop.gui.view.JHopDetailsView;
import com.cvrskidz.jhop.indexes.IndexEntry;
import java.util.ArrayList;

public class LoadModel extends Model{
    private String name;
    
    /**
     * Loads the specified index as the active index shared by all models.
     * 
     * @param args This model expects one argument, the name of the index.
     */
    @Override
    public void update(String... args) {
        if(args.length != ARGC) return;
        name = args[0];
        
        new Thread(this).start();
    }

    @Override
    public void alert() {
        if(error != null) {
            Model.observer.showError(error.getMessage());
            return;
        }
        
        // Update observers' active indexes
        String name = Model.index.getOptions().getName();
        JHopDetailsView sideBar = Model.observer.getSideBar();
        sideBar.setIndexName(name);
        
        // Update obseervers' active index page registry
        sideBar.clearPages();
        for(IndexEntry e : Model.index.getPages().keySet()) {
            sideBar.addPage(e);
        }
    }

    /**
     * Read a index as stored in a database into an Index instance. Run on a 
     * separate thread.
     */
    @Override
    public void run() {
         // Add name to executable arguments
        ArrayList<String> execArgs = new ArrayList<>();
        execArgs.add(name);
        
        try {
            DatabaseIndexReader reader = new DatabaseIndexReader(execArgs, Model.db);
            Model.toggleActive();
            Model.index = reader.exec(Model.index);
            Model.toggleActive();
            error = reader.getError();
        }
        catch (CommandException e) {
            error = e;
        }
        
        alert();
    }
}
