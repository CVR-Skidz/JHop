package com.cvrskidz.jhop.gui.controllers;

import com.cvrskidz.jhop.gui.view.JHopOptionsDialog;
import com.cvrskidz.jhop.gui.view.JHopView;
import java.awt.Container;

/**
 * A JHopController contains all the shared state and behavior of JHopContoller
 * children, and can be used to easily extend new Controllers, or add new
 * functions to all existing controllers.
 * 
 * @author cvrskidz 18031335
 */
public abstract class JHopController implements Controller{
    protected Container view;
    
    public JHopController(JHopView view) {
        this.view = view;
    }
    
    public JHopController(JHopOptionsDialog view) {
        this.view = view;
    }
}
