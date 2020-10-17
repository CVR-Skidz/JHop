package com.cvrskidz.jhop.gui.view;

import javax.swing.JPanel;
import javax.swing.JComponent;
import java.awt.CardLayout;
import java.awt.Color;

public class JHopSwitchPanel extends JPanel{
    private CardLayout views;
    
    public JHopSwitchPanel() {
        super();
        views = new CardLayout();
        setLayout(views);
        setBackground(Color.WHITE);
    }
    
    public void switchTo(String name) {
        views.show(this, name);
    }
    
    public void switchView() {
        views.next(this);
    }
    
    public void addView(JComponent view, String name){
        add(view, name);
    }
}
