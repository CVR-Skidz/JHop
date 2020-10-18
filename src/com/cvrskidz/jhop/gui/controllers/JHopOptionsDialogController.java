package com.cvrskidz.jhop.gui.controllers;

import com.cvrskidz.jhop.gui.view.JHopOptionsDialog;
import java.awt.event.ActionListener;

public class JHopOptionsDialogController {
    private JHopOptionsDialog view;
    
    public JHopOptionsDialogController(JHopOptionsDialog view) {
        this.view = view;
        
        linkAccpetButton();
        linkCancelButton();
    }
    
    private void linkAccpetButton() {
        view.getAcceptControl().addActionListener((ActionListener)(e)->{
            //TODO: add values into map here
            view.setVisible(false);
            view.dispose();
        });
    }
    
    private void linkCancelButton() {
        view.getCancelControl().addActionListener((ActionListener)(e)->{
            view.setVisible(false);
            view.dispose();
        });
    }
}
