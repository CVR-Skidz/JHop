package com.cvrskidz.jhop.gui.controllers;

import com.cvrskidz.jhop.gui.models.LoadModel;
import com.cvrskidz.jhop.gui.models.Model;
import com.cvrskidz.jhop.gui.view.JHopDetailsView;
import com.cvrskidz.jhop.gui.view.JHopView;
import com.cvrskidz.jhop.gui.view.JHopWebView;
import javax.swing.JList;
import javax.swing.event.ListSelectionListener;
import com.cvrskidz.jhop.indexes.IndexEntry;

/**
 * A JHopListController contains the logic linking all list UI components
 * to JHop's model. Specifically this controller allows the loading of indexes
 * and web pages from index and result lists.
 * 
 * @author cvrskidz 18031335
 */
public class JHopListController extends JHopController{
    // previously selected value of last list interacted with
    private String prevSelection;   
    
    public JHopListController(JHopView view) {
        super(view);
        prevSelection = "";
    }
    
    @Override
    public void link() {
        linkIndexList((JHopView) view);
        linkResultList((JHopView) view);
    }
    
    /**
     * Link the given GUI instances index list to an event that loads the selected
     * index.
     * 
     * @param view The view of the given JHop program.
     */
    private void linkIndexList(JHopView view) {
        JHopDetailsView sideBar = view.getSideBar();
        JList<String> indexes = sideBar.getIndexList();
        
        //create event listener
        ListSelectionListener response = (ListSelectionListener)(e)-> {
            //selected index 
            String name = sideBar.getSelectedIndex();
            boolean nameChanged = name != null && !name.equals(prevSelection);
            
            //only load new index if selection changed and model free
            if(nameChanged && !Model.isActive()) {
                Model load = new LoadModel();
                load.update(name);
            }
        };
        
        indexes.addListSelectionListener(response);
    }
    
    /**
     * Link the given GUI instances results list to an event that loads
     * the web pave on a JavaFX thread.
     * 
     * @param view The view of the given JHop program.
     */
    private void linkResultList(JHopView view) {
        JHopWebView webView = view.getDisplay();
        JList<IndexEntry> results = webView.getResultList();
        
        ListSelectionListener response = (ListSelectionListener)(e)->{
            webView.getCanvas().switchTo(JHopView.DOC_LABEL);
            IndexEntry page = results.getSelectedValue();
            
            // load if page is valid
            if(page != null && !page.getTitle().equals(prevSelection)) {
                prevSelection = page.getTitle();
                
                //load page
                webView.runOnFX((Runnable)()->{
                    webView.getEngine().load(page.getSource());
                });
            }
        };
        
        results.addListSelectionListener(response);
    }
}
