package com.cvrskidz.jhop.gui.models;

import com.cvrskidz.jhop.db.IndexLog;
import com.cvrskidz.jhop.db.indexutil.DatabaseIndexInspector;
import com.cvrskidz.jhop.exceptions.CommandException;
import com.cvrskidz.jhop.executables.Executable;
import java.util.ArrayList;

/**
 * A model to list all persistent indexes.
 *
 * @author cvrskidz 18031335
 * @see com.cvrskidz.jhop.executables.indexutil.IndexInspector
 */
public class ListModel extends Model {
    private Executable inspector;
    
    /**
     * Construct a new ListModel.
     */
    public ListModel() {
        inspector = null;
    }
    
    @Override
    public void alert() {
        if(error == null) {
            // Update observer
            for(IndexLog i : ((DatabaseIndexInspector)inspector).getResults()) {
                Model.observer.addIndexName(i.getIndexName());
            }

            if(!inspector.success()) {
                System.out.println(inspector.getError().getMessage());
            }
        }
        else {
            Model.observer.showError(error.getMessage());
        }
    }
    
    /**
     * List all persistent indexes.
     * 
     * @param args No arguments are used by this model.
     */
    @Override 
    public void update(String... args) {
        new Thread(this).start();
    }

    @Override
    public void run() {
        Model.toggleActive();
        
        // Create and run inspector
        try {
            ArrayList<String> execArgs = new ArrayList<>();
            execArgs.add(""); //empty args supplied to inspector
            
            inspector = new DatabaseIndexInspector(execArgs, Model.db); 
            inspector.exec(Model.index);
            error = inspector.getError();
        }
        catch(CommandException e) {
            error = e;
        }
        
        alert();
        Model.toggleActive();
    }
}
