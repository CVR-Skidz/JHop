package com.cvrskidz.jhop.gui.view;

import java.awt.BorderLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * A JHopView contains all the necessary UI components for a JHop GUI. It provides
 * nothing but a view of the JHop program and should be used within an MVC pattern
 * to link the supplied controls to JHop Executables.
 * <p>
 * The view itself is made of two groups of components, the display (web view and searching)
 * as well as the side bar (details and options).
 * 
 * @author cvrskidz 18031335
 */
public class JHopView extends JPanel{
    private final JHopDetailsView options;
    private final JHopWebView display;
    
    public JHopView() {
        super();
        options = new JHopDetailsView();
        display = new JHopWebView();
        initComponents();
    }
    
    /**
     * Add the components that make up this UI into this panel.
     */
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        add(options, BorderLayout.WEST);
        add(display, BorderLayout.CENTER);
    }
  
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", 
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * @return The side bar of this JHop GUI.
     */
    public JHopDetailsView getSideBar() {
        return options;
    }

    /**
     * @return The web view of this JHop GUI.
     */
    public JHopWebView getDisplay() {
        return display;
    }
}
