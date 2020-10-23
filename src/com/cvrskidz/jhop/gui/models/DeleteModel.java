package com.cvrskidz.jhop.gui.models;

import com.cvrskidz.jhop.db.indexutil.DatabaseIndexDropper;
import com.cvrskidz.jhop.exceptions.CommandException;
import com.cvrskidz.jhop.executables.Executable;
import com.cvrskidz.jhop.gui.view.JHopDetailsView;
import com.cvrskidz.jhop.gui.view.JHopView;
import com.cvrskidz.jhop.indexes.Index;
import java.util.ArrayList;

/**
 * A model to delete a persistent index.
 *
 * @author cvrskidz 18031335
 * @see com.cvrskidz.jhop.executables.indexutil.IndexDropper
 */
public class DeleteModel extends Model {
    private Executable dropper;
    private String name;
    private boolean replace;
    
    /**
     * Construct a new DeleteModel.
     */
    public DeleteModel() {
        dropper = null;
        name = null;
        replace = false;
    }
    
    /**
     * Prompt the user for an index name.
     *
     * @see com.cvrskidz.jhop.executables.indexutil.IndexDropper
     */
    @Override
    public void alert() {
        if(error != null) 
        {
            Model.observer.showError(error.getMessage());
        }
        else {
            JHopDetailsView sideBar = Model.observer.getSideBar();
            sideBar.removeIndex(name);
            if(replace) {
                sideBar.setIndexName(JHopView.DEFAULT_NAME);
                sideBar.clearPages();
            }
        }

    }
    
    /**
     * Drops the specified index from JHop.
     * 
     * @param args This model expects only one argument, the name of the 
     * index to drop.
     */
    @Override
    public void update(String... args) {
        if(args.length != ARGC) return;
        name = args[0];
        
        // clear active index before user deletes it
        if(Model.index != null && name.equals(Model.index.getOptions().getName())) {
            Model.index = new Index();
            replace = true;
        }

        new Thread(this).start();
    }

    @Override
    public void run() {
        // Add database to drop to the executable arguments
        ArrayList<String> execArgs = new ArrayList();
        execArgs.add(name);
        Model.toggleActive();
        
        try {
            dropper = new DatabaseIndexDropper(execArgs, Model.db);
            Model.index = dropper.exec(Model.index);
            error = dropper.getError();
        }
        catch (CommandException e) {
            error = e;
        }
        
        alert();
        Model.toggleActive();
    }
}
