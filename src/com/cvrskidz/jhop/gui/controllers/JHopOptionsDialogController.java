package com.cvrskidz.jhop.gui.controllers;

import com.cvrskidz.jhop.gui.models.CreateModel;
import com.cvrskidz.jhop.gui.models.Model;
import com.cvrskidz.jhop.gui.view.JHopOptionsDialog;
import java.awt.event.ActionListener;

public class JHopOptionsDialogController extends JHopController{
    
    public JHopOptionsDialogController(JHopOptionsDialog view) {
        super(view);
    }
    
    @Override
    public void link() {
        linkAccpetButton((JHopOptionsDialog)view);
        linkCancelButton((JHopOptionsDialog)view);
    }
    
    private void linkAccpetButton(JHopOptionsDialog view) {
        view.getAcceptControl().addActionListener((ActionListener)(e)->{   
            if(Model.isActive()) return;

            for(String name : view.getFieldNames()) view.set(name);
            view.setVisible(false);
            view.dispose();
            
            Model create = new CreateModel();
            create.update(
                view.get("Name"), view.get("Source"), 
                view.get("Attribute"), view.get("Value"), 
                view.get("Hop Limit")
            );
        });
    }
    
    private void linkCancelButton(JHopOptionsDialog view) {
        view.getCancelControl().addActionListener((ActionListener)(e)->{
            view.setVisible(false);
            view.dispose();
        });
    }
}
