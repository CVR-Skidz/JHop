package com.cvrskidz.jhop.gui.view;

import com.cvrskidz.jhop.gui.controllers.JHopButtonController;
import com.cvrskidz.jhop.indexes.IndexEntry;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class JHopView extends JPanel{
    public static int DEFAULT_WIDTH = 1000;
    public static int DEFAULT_HEIGHT = 800;
    
    public static final String RESULTS_LABEL = "results";
    public static final String DOC_LABEL = "document";
    private static final String ADD_LABEL = "Add";
    private static final String DROP_LABEL = "Drop";
    private static final String HIDE_LABEL = "Hide";
    private static final String SHOW_LABEL = "Menu";
    
    private final SidePanel options;
    private final WebPanel display;
    
    private class WebPanel {
        public static final String DEFAULT_LABEL = "Search";

        private JPanel container, content;
        private JList<IndexEntry> results;
        private JPanel document; //stub
        private JTextField searchInput;
        private JButton searchButton;
        
        public WebPanel() {
            container = new JPanel(new BorderLayout());
            content = new JHopSwitchPanel();
            results = new JList<>(new DefaultListModel());
            document = new JPanel();
            searchInput = new JTextField();
            searchButton = new JButton(WebPanel.DEFAULT_LABEL);
            
            populate();
            ((JHopSwitchPanel)content).switchTo(RESULTS_LABEL);
        }
        
        private void populate() {
            JPanel searchBar = new JPanel(new BorderLayout());
            searchBar.add(searchInput, BorderLayout.CENTER);
            searchBar.add(searchButton, BorderLayout.EAST);
            
            JScrollPane resultsWrapper = new JScrollPane(results);
            ((JHopSwitchPanel)content).addView(resultsWrapper, RESULTS_LABEL);
            ((JHopSwitchPanel)content).addView(document, DOC_LABEL);
            
            container.add(searchBar, BorderLayout.NORTH);
            container.add(content, BorderLayout.CENTER);
        }
    }
    
    private class SidePanel{
        private static final String DEFAULT_NAME = "N/A";

        private JHopSwitchPanel container;
        private JHopStackPanel visible;
        private JList<String> indexes, availablePages;
        private JLabel indexName;
        private JPanel hidden, controls;
        private JButton add, drop, show, hide;
        
        public SidePanel() {
            container = new JHopSwitchPanel();
            visible = new JHopStackPanel();
            hidden = new JPanel(new BorderLayout());
            
            indexes = new JList<>(new DefaultListModel<>());
            availablePages = new JList<>(new DefaultListModel<>());
            indexName = new JLabel(DEFAULT_NAME);
            controls = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            add = new JButton(ADD_LABEL);
            drop = new JButton(DROP_LABEL);
            show = new JButton(SHOW_LABEL);
            hide = new JButton(HIDE_LABEL);
            
            populate();
        }
        
        private void populate() {
            JScrollPane indexWrapper = new JScrollPane(indexes);
            JScrollPane pageWrapper = new JScrollPane(availablePages);
            controls.add(add);
            controls.add(drop);
            
            JPanel indexList = new JPanel(new BorderLayout());
            JPanel hidePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            hidePanel.add(hide);
            indexList.add(indexWrapper, BorderLayout.CENTER);
            indexList.add(controls, BorderLayout.SOUTH);
            indexList.add(hidePanel, BorderLayout.NORTH);
            
            visible.addChild(indexList);
            visible.addChild(indexName);
            visible.addChild(pageWrapper);
            
            JPanel hiddenWrapper = new JPanel();
            hiddenWrapper.add(show);
            hidden.add(hiddenWrapper, BorderLayout.NORTH);
            
            container.add(visible);
        }
    }
    
    public JHopView() {
        super();
        options = new SidePanel();
        display = new WebPanel();
        init();
    }
    
    private void init() {
        setLayout(new BorderLayout(10, 10));
        add(options.container, BorderLayout.WEST);
        add(display.container, BorderLayout.CENTER);
    }
    
    public JButton getAddControl() {
        return options.add;
    }
    
    public JButton getDropControl() {
        return options.drop;
    }
    
    public JButton getHideControl() {
        return options.hide;
    }
    
    public JButton getShowControl() {
        return options.show;
    }
    
    public void addIndexName(String name) {
        ((DefaultListModel)options.indexes.getModel()).addElement(name);
    }
    
    public void setIndexName(String name) {
        options.indexName.setText(name);
    }
    
    public JTextField getSearchBar() {
        return display.searchInput;
    }
    
    public JButton getSearchControl() {
        return display.searchButton;
    }
    
    public void addResult(IndexEntry result){
        ((DefaultListModel)display.results.getModel()).addElement(result);
    }
    
    public JHopSwitchPanel getDisplay() {
        return (JHopSwitchPanel)display.content;
    }
    
    public void hideSideBar() {
        options.container.remove(options.visible);
        options.container.add(options.hidden);
        updateUI();
    }
    
    public void showSideBar() {
        options.container.remove(options.hidden);
        options.container.add(options.visible);
        updateUI();
    }
    
    // stub
    public static void loadView() {
        JHopView view = new JHopView();
        JHopButtonController controller = new JHopButtonController(view);
        controller.linkButtons();
        
        JFrame root = new JFrame();
        root.getContentPane().add(view);
        root.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        root.setSize(JHopView.DEFAULT_WIDTH, JHopView.DEFAULT_HEIGHT);
        root.setVisible(true);
    }
}
