package com.cvrskidz.jhop.gui.view;

import java.awt.LayoutManager;
import java.util.Collection;
import java.util.Set;
import java.util.HashSet;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class JHopStackPanel extends JPanel{
    Set<JComponent> children;
    
    public JHopStackPanel() {
        init();
        children = new HashSet<>();
    }
    
    public JHopStackPanel(Collection<JComponent> children) {
        this();        
        for(JComponent c: children) addChild(c);
    }

    private void init() {
        LayoutManager layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);
    }
    
    public boolean addChild(JComponent c) {
        if(!children.add(c)) return false;
        
        add(c);
        return true;
    }
    
    public boolean removeChild(JComponent c) {
        if(!children.remove(c)) return false;
        
        remove(c);
        return true;
    }
    
    public Set getChildren() {
        return children;
    }
}
