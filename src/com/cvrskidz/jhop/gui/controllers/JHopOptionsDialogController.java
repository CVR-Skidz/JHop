package com.cvrskidz.jhop.gui.controllers;

import com.cvrskidz.jhop.gui.models.CreateModel;
import com.cvrskidz.jhop.gui.models.Model;
import com.cvrskidz.jhop.gui.view.JHopOptionsDialog;
import java.awt.event.ActionListener;

/**
 * A JHopOptionsDialogController is a specific controller that links
 * a JHopOptionsDialog's controls to a create model. Thus the dialog's
 * options are used to create a new index.
 * 
 * @author cvrskidz 18031335
 */
public class JHopOptionsDialogController extends JHopController{
    
    public JHopOptionsDialogController(JHopOptionsDialog view) {
        super(view);
    }
    
    @Override
    public void link() {
        linkAccpetButton((JHopOptionsDialog)view);
        linkCancelButton((JHopOptionsDialog)view);
    }
    
    /**
     * Link the accept button of the given dialog to a new event,
     * creating a new index.
     * 
     * @param view The view of the options dialog.
     */
    private void linkAccpetButton(JHopOptionsDialog view) {
        //create response
        ActionListener response = (ActionListener)(e)->{   
            //only run if model is not active
            if(Model.isActive()) return;
            
            //close window
            for(String name : view.getFieldNames()) view.set(name);
            view.setVisible(false); 
            view.dispose();
            
            //create index
            Model create = new CreateModel();
            create.update(
                view.get("Name"), view.get("Source"), 
                view.get("Attribute"), view.get("Value"), 
                view.get("Hop Limit")
            );
        };
        
        view.getAcceptControl().addActionListener(response);
    }
    
    /**
     * Link the cancel button of the given dialog to a new event, discarding
     * the users input.
     * 
     * @param view The view of the options dialog
     */
    private void linkCancelButton(JHopOptionsDialog view) {
        ActionListener response = (ActionListener)(e)->{
            //close window
            view.setVisible(false);
            view.dispose();
        };
        
        view.getCancelControl().addActionListener(response);
    }
}
