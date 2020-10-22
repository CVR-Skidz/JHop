package com.cvrskidz.jhop.gui.view;

import com.cvrskidz.jhop.JHop;
import com.cvrskidz.jhop.indexes.IndexEntry;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javafx.application.Platform;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class JHopView extends JPanel{
    public static int DEFAULT_WIDTH = 1000;
    public static int DEFAULT_HEIGHT = 800;
    
    public static final String DEFAULT_NAME = "Ready";
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
        private JFXPanel document;
        private WebEngine engine;
        private JTextField searchInput;
        private JButton searchButton;
        
        public WebPanel() {
            container = new JPanel(new BorderLayout());
            content = new JHopSwitchPanel();
            results = new JList<>(new DefaultListModel());
            document = new JFXPanel();
            searchInput = new JTextField();
            searchButton = new JButton(WebPanel.DEFAULT_LABEL);
            
            populate();
            ((JHopSwitchPanel)content).switchTo(RESULTS_LABEL);
            
            //add web engine to embedded javafx panel on javafx thread
            Platform.runLater((Runnable)()->{
                document.setScene(createWebView());
            });
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
        
        private Scene createWebView() {
            WebView webView = new WebView();
            engine = webView.getEngine();
            return new Scene(webView);
        }
    }
    
    private class SidePanel{
        private JHopSwitchPanel container;
        private JHopStackPanel visible;
        private JList<String> indexes, availablePages;
        private JPanel hidden, controls;
        private JScrollPane pageWrapper;
        private JButton add, drop, show, hide;
        private JProgressBar progress;
        
        public SidePanel() {
            container = new JHopSwitchPanel();
            visible = new JHopStackPanel();
            hidden = new JPanel(new BorderLayout());
            
            indexes = new JList<>(new DefaultListModel<>());
            availablePages = new JList<>(new DefaultListModel<>());
            controls = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            add = new JButton(ADD_LABEL);
            drop = new JButton(DROP_LABEL);
            show = new JButton(SHOW_LABEL);
            hide = new JButton(HIDE_LABEL);
            progress = new JProgressBar();
            
            populate();
        }
        
        private void populate() {
            indexes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane indexWrapper = new JScrollPane(indexes);
            pageWrapper = new JScrollPane(availablePages);
            Border title = BorderFactory.createTitledBorder(DEFAULT_NAME);
            pageWrapper.setBorder(title);
            controls.add(progress);
            controls.add(add);
            controls.add(drop);
            
            JPanel indexList = new JPanel(new BorderLayout());
            JPanel hidePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            hidePanel.add(hide);
            indexList.add(indexWrapper, BorderLayout.CENTER);
            indexList.add(controls, BorderLayout.SOUTH);
            indexList.add(hidePanel, BorderLayout.NORTH);
            
            visible.addChild(indexList);
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
    
    public void clearPages() {
        ((DefaultListModel)options.availablePages.getModel()).clear();
    }
    
    public void clearResults() {
        ((DefaultListModel)display.results.getModel()).clear();
    }
    
    public void addPage(IndexEntry page) {
        ((DefaultListModel)options.availablePages.getModel()).addElement(page);
    }
    
    public void addIndexName(String name) {
        ((DefaultListModel)options.indexes.getModel()).addElement(name);
    }
    
    public void removeIndexName(String name) {
        ((DefaultListModel)options.indexes.getModel()).removeElement(name);
    }
    
    public void setIndexName(String name) {
        Border title = BorderFactory.createTitledBorder(name);
        options.pageWrapper.setBorder(title);
    }
    
    public String getSelectedIndex() {
        return options.indexes.getSelectedValue();
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
    
    public JList getIndexList() {
        return options.indexes;
    }
    
    public JList getResultList() {
        return display.results;
    }
    
    public WebEngine getEngine() {
        return display.engine;
    }
    
    public void toggleProgress() {
        boolean mode = options.progress.isIndeterminate();
        options.progress.setIndeterminate(!mode);
    }
    
    public void runOnFX(Runnable r) {
        Platform.runLater(r);
    }
    
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", 
                JOptionPane.ERROR_MESSAGE);
    }
}
