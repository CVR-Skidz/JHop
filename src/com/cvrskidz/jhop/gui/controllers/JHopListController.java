package com.cvrskidz.jhop.gui.controllers;

import com.cvrskidz.jhop.gui.models.LoadModel;
import com.cvrskidz.jhop.gui.models.Model;
import com.cvrskidz.jhop.gui.view.JHopView;
import javax.swing.JList;
import javax.swing.event.ListSelectionListener;
import com.cvrskidz.jhop.indexes.IndexEntry;

public class JHopListController extends JHopController{
    private String selectedIndex;
    
    public JHopListController(JHopView view) {
        super(view);
        selectedIndex = "";
    }
    
    private void linkIndexList() {
        JHopView view = (JHopView) this.view;
        JList<String> indexes = view.getIndexList();
        
        indexes.addListSelectionListener((ListSelectionListener)(e)-> {
            String name = view.getSelectedIndex();
            if(name != null && !name.equals(selectedIndex) && !Model.isActive()) {
                Model load = new LoadModel();
                load.update(name);
            }
        });
    }
    
    private void linkResultList() {
        JHopView view = (JHopView) this.view;
        JList<IndexEntry> results = view.getResultList();
        
        results.addListSelectionListener((ListSelectionListener)(e)->{
            view.getDisplay().switchTo(JHopView.DOC_LABEL);
            IndexEntry index = results.getSelectedValue();
            if(index != null && !index.getTitle().equals(selectedIndex)) {
                selectedIndex = index.getTitle();
                view.runOnFX((Runnable)()->{
                    view.getEngine().load(index.getSource());
                });
            }
        });
        
    }

    @Override
    public void link() {
        linkIndexList();
        linkResultList();
    }
}
