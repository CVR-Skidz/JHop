package com.cvrskidz.jhop.gui.controllers;

import com.cvrskidz.jhop.gui.models.LoadModel;
import com.cvrskidz.jhop.gui.models.Model;
import com.cvrskidz.jhop.gui.view.JHopDetailsView;
import com.cvrskidz.jhop.gui.view.JHopView;
import com.cvrskidz.jhop.gui.view.JHopWebView;
import javax.swing.JList;
import javax.swing.event.ListSelectionListener;
import com.cvrskidz.jhop.indexes.IndexEntry;

public class JHopListController extends JHopController{
    private String selectedIndex;
    
    public JHopListController(JHopView view) {
        super(view);
        selectedIndex = "";
    }
    
    @Override
    public void link() {
        linkIndexList((JHopView) view);
        linkResultList((JHopView) view);
    }
    
    private void linkIndexList(JHopView view) {
        JHopDetailsView sideBar = view.getSideBar();
        JList<String> indexes = sideBar.getIndexList();
        
        indexes.addListSelectionListener((ListSelectionListener)(e)-> {
            String name = sideBar.getSelectedIndex();
            if(name != null && !name.equals(selectedIndex) && !Model.isActive()) {
                Model load = new LoadModel();
                load.update(name);
            }
        });
    }
    
    private void linkResultList(JHopView view) {
        JHopWebView webView = view.getDisplay();
        JList<IndexEntry> results = webView.getResultList();
        
        results.addListSelectionListener((ListSelectionListener)(e)->{
            webView.getCanvas().switchTo(JHopView.DOC_LABEL);
            IndexEntry index = results.getSelectedValue();
            if(index != null && !index.getTitle().equals(selectedIndex)) {
                selectedIndex = index.getTitle();
                webView.runOnFX((Runnable)()->{
                    webView.getEngine().load(index.getSource());
                });
            }
        });
    }
}
