package com.cvrskidz.jhop.gui.controllers;

import com.cvrskidz.jhop.JHop;
import com.cvrskidz.jhop.gui.models.ListModel;
import com.cvrskidz.jhop.gui.models.Model;
import com.cvrskidz.jhop.gui.models.DeleteModel;
import com.cvrskidz.jhop.gui.models.SearchModel;
import com.cvrskidz.jhop.gui.view.JHopDetailsView;
import com.cvrskidz.jhop.gui.view.JHopOptionsDialog;
import com.cvrskidz.jhop.gui.view.JHopView;
import com.cvrskidz.jhop.gui.view.JHopWebView;
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
        JHopDetailsView sideBar = view.getSideBar();
        JButton hide = sideBar.getHideControl();
        
        hide.addActionListener((ActionListener)(e) -> { 
            sideBar.collapse();
        });
    }
    
    private void linkShowButton(JHopView view) {
        JHopDetailsView sideBar = view.getSideBar();
        JButton show = sideBar.getShowControl();
        
        show.addActionListener((ActionListener)(e) -> { 
            sideBar.expand();
        });
    }
    
    private void linkAddButton(JHopView view) {
        JHopDetailsView sideBar = view.getSideBar();
        JButton add = sideBar.getAddControl();
        
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
        JHopDetailsView sideBar = view.getSideBar();
        JButton drop = sideBar.getDropControl();
        
        drop.addActionListener((ActionListener)(e)->{
            if(Model.isActive()) return;
            String name = sideBar.getSelectedIndex();
            
            if(name != null) {
                Model delete = new DeleteModel();
                delete.update(name);
            }
        });
    }
    
    private void linkSearchButton(JHopView view) {
        JHopWebView searchContainer = view.getDisplay();
        JButton search = searchContainer.getSearchControl();
        
        search.addActionListener((ActionListener)(e) -> {
            if(Model.isActive()) return;

            String term = searchContainer.getSearchBar().getText();
            Model searcher = new SearchModel();
            searcher.update(term);
        });
    }
}
