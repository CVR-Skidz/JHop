package com.cvrskidz.jhop.gui.view;

import java.awt.LayoutManager;
import java.util.Collection;
import java.util.Set;
import java.util.HashSet;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * A JHopStackPanel provides a panel that "stacks" components
 * vertically. The order components are added determines which
 * components are placed at the top of the panel. Components are 
 * placed below one another. <strong> This panel does not allow 
 * duplicate components</strong> 
 * 
 * @author cvrskidz 18031335
 */
public class JHopStackPanel extends JPanel{
    Set<JComponent> children;
    
    public JHopStackPanel() {
        init();
        children = new HashSet<>();
    }
    
    /**
     * Construct a JHopStackPanel containing all the given children
     * 
     * @param children A collection of components to add to the panel
     */
    public JHopStackPanel(Collection<JComponent> children) {
        this();        
        for(JComponent c: children) addChild(c);
    }

    /**
     * Prepare this panel to add children
     */
    private void init() {
        LayoutManager layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);
    }
    
    /**
     * Add a child to this panel.
     * 
     * @param c the component to add
     * @return True if the component was added to the panel.
     */
    public boolean addChild(JComponent c) {
        if(!children.add(c)) return false;
        
        add(c);
        return true;
    }
    
    /**
     * Remove the given component from this panel
     * 
     * @param c The component to remove
     * @return True if the component was removed
     */
    public boolean removeChild(JComponent c) {
        if(!children.remove(c)) return false;
        
        remove(c);
        return true;
    }
    
    /**
     * @return A set containing all children components
     * added to this panel
     */
    public Set getChildren() {
        return children;
    }
}
