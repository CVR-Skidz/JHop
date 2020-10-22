package com.cvrskidz.jhop.gui.controllers;

import com.cvrskidz.jhop.JHop;
import com.cvrskidz.jhop.gui.models.ListModel;
import com.cvrskidz.jhop.gui.models.Model;
import com.cvrskidz.jhop.gui.models.DeleteModel;
import com.cvrskidz.jhop.gui.models.SearchModel;
import com.cvrskidz.jhop.gui.view.JHopOptionsDialog;
import com.cvrskidz.jhop.gui.view.JHopView;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JButton;

public class JHopButtonController extends JHopController{
    public JHopButtonController(JHopView view) {
        super(view);
        
        if(!Model.isActive()) {
            Model populator = new ListModel();
            populator.update();
        }
    }
    
    @Override
    public void link() {
        linkHideButton((JHopView)view);
        linkShowButton((JHopView)view);
        linkAddButton((JHopView)view);
        linkDropButton((JHopView)view);
        linkSearchButton((JHopView)view);
    }
    
    private void linkHideButton(JHopView view) {
        JButton hide = view.getHideControl();
        
        hide.addActionListener((ActionListener)(e) -> { 
            view.hideSideBar();    
        });
    }
    
    private void linkShowButton(JHopView view) {
        JButton show = view.getShowControl();
        
        show.addActionListener((ActionListener)(e) -> {
            view.showSideBar();
        });
    }
    
    private void linkAddButton(JHopView view) {
        JButton add = view.getAddControl();
        
        add.addActionListener((ActionListener)(e)->{
            //Prevent mulitple threads accessing the model
            if(Model.isActive()) return;
            
            ArrayList<String> options = new ArrayList(Arrays.asList(new String[] {
                "Name", "Source", "Attribute", "Value", "Hop Limit"
            }));
            
            new JHopOptionsDialog(options, JHop.getActiveWindow());           
        });
    }
    
    private void linkDropButton(JHopView view) {
        JButton drop = view.getDropControl();
        
        drop.addActionListener((ActionListener)(e)->{
            if(Model.isActive()) return;
            String name = view.getSelectedIndex();
            
            if(name != null) {
                Model delete = new DeleteModel();
                delete.update(name);
            }
        });
    }
    
    private void linkSearchButton(JHopView view) {
        JButton search = view.getSearchControl();
        search.addActionListener((ActionListener)(e) -> {
            if(Model.isActive()) return;

            String term = view.getSearchBar().getText();
            Model searcher = new SearchModel();
            searcher.update(term);
        });
    }
}
