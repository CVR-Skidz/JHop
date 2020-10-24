package com.cvrskidz.jhop.gui.view;

import javax.swing.JPanel;
import javax.swing.JComponent;
import java.awt.CardLayout;
import java.awt.Color;

/**
 * A JHopSwitchPanel encapsulates a Card layout and allows
 * this panel to switch between visible components.
 * 
 * @author cvrskidz 18031335
 */  
public class JHopSwitchPanel extends JPanel{
    private CardLayout views;
    
    public JHopSwitchPanel() {
        super();
        views = new CardLayout();
        setLayout(views);
        setBackground(Color.WHITE);
    }
    
    /**
     * Switch to the specified view
     * 
     * @param name The name of the view
     */
    public void switchTo(String name) {
        views.show(this, name);
    }
    
    /**
     * Switch to the next view
     */
    public void switchView() {
        views.next(this);
    }
    
    /**
     * Add a view to this panel
     * 
     * @param view The component to act as a the new view
     * @param name The desired name of the new view
     */
    public void addView(JComponent view, String name){
        add(view, name);
    }
}
