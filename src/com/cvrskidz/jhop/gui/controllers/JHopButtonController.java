package com.cvrskidz.jhop.gui.controllers;

import com.cvrskidz.jhop.JHop;
import com.cvrskidz.jhop.gui.models.ListModel;
import com.cvrskidz.jhop.gui.models.Model;
import com.cvrskidz.jhop.gui.models.DeleteModel;
import com.cvrskidz.jhop.gui.models.SearchModel;
import com.cvrskidz.jhop.gui.view.JHopDetailsView;
import com.cvrskidz.jhop.gui.view.JHopOptionsDialog;
import com.cvrskidz.jhop.gui.view.JHopView;
import com.cvrskidz.jhop.gui.view.JHopViewConstants;
import com.cvrskidz.jhop.gui.view.JHopWebView;
import java.awt.event.ActionListener;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JButton;

/**
 * A JHopButtonController contains all the logic to link a JHop GUI's controls
 * to their appropriate actions. Specifically all buttons included in the default
 * JHopView are provided listeners in this class.
 * 
 * @author cvrskidz 18031335
 */
public class JHopButtonController extends JHopController{
    public JHopButtonController(JHopView view) {
        super(view);
        
        //load all available indexes on start
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
        linkHelpButton((JHopView)view);
    }
    
    /**
     * Link the "menu hide" button in a default view to an event collapsing
     * the side bar of the interface.
     * 
     * @param view The given view of the JHop GUI.
     */
    private void linkHideButton(JHopView view) {
        JHopDetailsView sideBar = view.getSideBar();
        JButton hide = sideBar.getHideControl();
        
        hide.addActionListener((ActionListener)(e) -> { 
            sideBar.collapse();
        });
    }
    
    /**
     * Link the "menu show" button in a default view to an event expanding
     * the side bar of the interface.
     * 
     * @param view The given view of the JHop GUI.
     */
    private void linkShowButton(JHopView view) {
        JHopDetailsView sideBar = view.getSideBar();
        JButton show = sideBar.getShowControl();
        
        show.addActionListener((ActionListener)(e) -> { 
            sideBar.expand();
        });
    }
    
    /**
     * Link the "add" button in a default view to an event that triggers a 
     * dialog to create a new index.
     * 
     * @param view The given view of the JHop GUI.
     */
    private void linkAddButton(JHopView view) {
        JHopDetailsView sideBar = view.getSideBar();
        JButton add = sideBar.getAddControl();
        
        ActionListener response = (ActionListener)(e)->{
            //Prevent mulitple threads accessing the model
            if(Model.isActive()) return;
            
            //populate options
            String[] optionNames = new String[] {
                "Name", "Source", "Attribute", "Value", "Hop Limit"
            };
            
            List<String> options = new ArrayList(Arrays.asList(optionNames));
            
            // launch dialog
            new JHopOptionsDialog(options, JHop.getActiveWindow());           
        };
        
        add.addActionListener(response);
    }
    
    /**
     * Link the "drop" button in a default view to an event that triggers a 
     * new delete model.
     * 
     * @param view The given view of the JHop GUI.
     */
    private void linkDropButton(JHopView view) {
        JHopDetailsView sideBar = view.getSideBar();
        JButton drop = sideBar.getDropControl();
        
        ActionListener response = (ActionListener)(e)->{
            //prevent access to model if not free
            if(Model.isActive()) return;
            String name = sideBar.getSelectedIndex();
            
            if(name != null) {
                Model delete = new DeleteModel();
                delete.update(name);
            }
        };
        
        drop.addActionListener(response);
    }
    
    /**
     * Link the "search" button in a default view to an event that triggers a 
     * search of the active index.
     * 
     * @param view The given view of the JHop GUI.
     */ 
    private void linkSearchButton(JHopView view) {
        JHopWebView searchContainer = view.getDisplay();
        JButton search = searchContainer.getSearchControl();
        
        ActionListener response = (ActionListener)(e) -> {
            // access only if model is free
            if(Model.isActive()) return;

            String term = searchContainer.getSearchBar().getText();
            Model searcher = new SearchModel();
            searcher.update(term);
        };
        
        search.addActionListener(response);
    }
    
    /**
     * Link the "help" button in a default view to an event that opens the help page.
     * 
     * @param view The given view of the JHop GUI.
     */ 
    private void linkHelpButton(JHopView view) {
        JHopDetailsView menu = view.getSideBar();
        JButton help = menu.getHelpControl();
        
        ActionListener response = (ActionListener)(event) -> {
            try {
                Desktop.getDesktop().browse(new URI(JHopViewConstants.HELP_URL));
            }
            catch (IOException | URISyntaxException e) {
                view.showError(e.getMessage());
            }
        };
        
        help.addActionListener(response);
    }
}
