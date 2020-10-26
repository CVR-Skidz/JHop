package com.cvrskidz.jhop.gui.view;

import com.cvrskidz.jhop.indexes.IndexEntry;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;

/**
 * A JHopDetailsView consists of the UI elements that display options to the user
 * and facilitate operations to interact with, but not search, indexes. 
 * <p>
 * Specifically a details view consists of a two vies, hidden and visible, that
 * can be switched between when added to a listener. Additionally a details view
 * is split in two halves, the upper showing available indexes, and the lower showing
 * details on the active index.
 * 
 * @author cvrskidz 18031335
 */
public class JHopDetailsView extends JHopSwitchPanel{
    // states
    private JHopStackPanel visible;
    private JPanel hidden;
    
    // UI controls
    private JList<String> indexes, availablePages;
    private JPanel controls;
    private JScrollPane pageWrapper;
    private JButton add, drop, show, hide, help;
    private JProgressBar progress;
    
    public JHopDetailsView() {
        super();
        initComponents();
        populateUpper();
        populateLower();
        populateHidden();
        add(visible);
    }
    
    /**
     * Create components without contents.
     */
    private void initComponents() {
        //views and info
        visible = new JHopStackPanel();
        hidden = new JPanel(new BorderLayout());
        indexes = new JList<>(new DefaultListModel<>());
        availablePages = new JList<>(new DefaultListModel<>());
        
        //buttons and index controls
        controls = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        add = new JButton(JHopViewConstants.ADD_LABEL);
        drop = new JButton(JHopViewConstants.DROP_LABEL);
        show = new JButton(JHopViewConstants.SHOW_LABEL);
        hide = new JButton(JHopViewConstants.HIDE_LABEL);
        help = new JButton(JHopViewConstants.HELP_LABEL);
        
        progress = new JProgressBar();
    }
    
    /**
     * Populate the contents of the upper details pane.
     */
    private void populateUpper() {
        //list indexes
        indexes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane indexWrapper = new JScrollPane(indexes);
        JPanel indexList = new JPanel(new BorderLayout());

        //controls to hide the current view
        JPanel hidePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        hidePanel.add(hide);
        
        //index controls
        controls.add(progress);
        controls.add(help);
        controls.add(add);
        controls.add(drop);
        
        indexList.add(indexWrapper, BorderLayout.CENTER);
        indexList.add(controls, BorderLayout.SOUTH);
        indexList.add(hidePanel, BorderLayout.NORTH);
        visible.addChild(indexList);
    }
    
    /**
     * Populate the contents of the lower details pane.
     */
    private void populateLower() {
        //active index info
        pageWrapper = new JScrollPane(availablePages);
        Border title = BorderFactory.createTitledBorder(JHopViewConstants.DEFAULT_NAME);
        pageWrapper.setBorder(title);
        visible.addChild(pageWrapper);
    }
    
    /**
     * Create the view to hide the components contained within the visible view.
     */
    private void populateHidden() {
        JPanel hiddenWrapper = new JPanel();
        hiddenWrapper.add(show);
        hidden.add(hiddenWrapper, BorderLayout.NORTH);
    }

    /**
     * @return The control used to add an index to JHop.
     */
    public JButton getAddControl() {
        return add;
    }

    /**
     * @return The control used to drop an index to JHop.
     */
    public JButton getDropControl() {
        return drop;
    }

    /**
     * @return The control used to show the details menu in JHop.
     */
    public JButton getShowControl() {
        return show;
    }

    /**
     * @return The control used to hide the details menu in JHop.
     */
    public JButton getHideControl() {
        return hide;
    }

    /**
     * @return The control used to launch help.
     */
    public JButton getHelpControl() {
        return help;
    }
    
    /**
     * Clears the list of pages in the current index.
     */
    public void clearPages() {
        ((DefaultListModel)availablePages.getModel()).clear();
    }
    
    /**
     * Add a page to the list of available pages in the current index.
     * @param page The page to add
     */
    public void addPage(IndexEntry page) {
        ((DefaultListModel)availablePages.getModel()).addElement(page);
    } 
    
    /**
     * Add an index to the list of available indexes.
     * @param name The name of the index
     */
    public void addIndex(String name) {
        ((DefaultListModel)indexes.getModel()).addElement(name);
    }
    
    /**
     * Remove an index to the list of available indexes.
     * @param name The name of the index
     */
    public void removeIndex(String name) {
        ((DefaultListModel)indexes.getModel()).removeElement(name);
    }
    
    /**
     * Set the active index name.
     * @param name The name of the active index.
     */
    public void setIndexName(String name) {
        Border title = BorderFactory.createTitledBorder(name);
        pageWrapper.setBorder(title);
    }
    
    /**
     * @return The currently selected index name.
     */
    public String getSelectedIndex() {
        return indexes.getSelectedValue();
    }
    
    /**
     * Switch to this view's hidden state.
     */
    public void collapse() {
        remove(visible);
        add(hidden);
        updateUI();
    }
    
    /**
     * Switch to this view's visible state.
     */
    public void expand() {
        remove(hidden);
        add(visible);
        updateUI();
    }
    
    /**
     * @return The list component containing all available indexes.
     */
    public JList getIndexList() {
        return indexes;
    }
    
    /**
     * Toggle the progress bar to indicate JHop's model is active.
     */
    public void toggleProgress() {
        boolean mode = progress.isIndeterminate();
        progress.setIndeterminate(!mode);
    }
}
