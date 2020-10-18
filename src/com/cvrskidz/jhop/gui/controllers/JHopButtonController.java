package com.cvrskidz.jhop.gui.controllers;

import com.cvrskidz.jhop.JHop;
import com.cvrskidz.jhop.db.IndexLog;
import com.cvrskidz.jhop.exceptions.CommandException;
import com.cvrskidz.jhop.db.indexutil.DatabaseIndexInspector;
import com.cvrskidz.jhop.gui.view.JHopOptionsDialog;
import com.cvrskidz.jhop.gui.view.JHopView;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;

public class JHopButtonController {
    private JHopView view;
    
    public JHopButtonController(JHopView v) {
        view = v;
        
        try {
            populateIndexes();
        }
        catch (CommandException e) {
            //stub
        }
    }
    
    public JHopButtonController linkButtons() {
        linkHideButton();
        linkShowButton();
        linkAddButton();
        return this;
    }
    
    private void linkHideButton() {
        JButton hide = view.getHideControl();
        
        hide.addActionListener((ActionListener)(e) -> { 
            view.hideSideBar();    
        });
    }
    
    private void linkShowButton() {
        JButton show = view.getShowControl();
        
        show.addActionListener((ActionListener)(e) -> {
            view.showSideBar();
        });
    }
    
    private void linkAddButton() {
        JButton add = view.getAddControl();
        
        add.addActionListener((ActionListener)(e)->{
            ArrayList options = new ArrayList();
            options.add("Name");
            options.add("Source");
            options.add("Attribute");
            options.add("Value");
            options.add("Hop Limit");
            new JHopOptionsDialog(options, JHop.getActiveWindow());
        });
    }
    
    private void populateIndexes() throws CommandException{
        ArrayList<String> args = new ArrayList();   
        args.add("");   //empty arguments
        DatabaseIndexInspector operation = new DatabaseIndexInspector(args);
        operation.exec(null);
        
        for(IndexLog i : operation.getResults()) view.addIndexName(i.getIndexName());
    }
}
