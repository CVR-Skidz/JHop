package com.cvrskidz.jhop.gui.view;

import com.cvrskidz.jhop.indexes.IndexEntry;
import java.awt.BorderLayout;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * A JHopWebView contains the UI elements to display and search web pages.
 * All calls to the web engine used to display web engines must be run on this
 * instances JavaFX thread.
 * 
 * @author cvrskidz 18031335
 */
public class JHopWebView extends JPanel{
    private JHopSwitchPanel content;
    private JList<IndexEntry> results;
    private JLabel status;
    private JFXPanel document;
    private WebEngine engine;
    private JTextField searchInput;
    private JButton searchButton;

    public JHopWebView() {
        super(new BorderLayout());
        initComponents();
        populateSearch();
        populateCanvas();

        //start on results
        content.switchTo(JHopViewConstants.RESULTS_LABEL);
    }

    /**
     * Create all components used within this view without contents.
     */
    private void initComponents() {
        content = new JHopSwitchPanel();
        results = new JList<>(new DefaultListModel());
        document = new JFXPanel();
        searchInput = new JTextField();
        searchButton = new JButton(JHopViewConstants.SEARCH_LABEL);
        status = new JLabel(JHopViewConstants.DEFAULT_NAME);
    }

    /**
     * Fill the default contents of the main canvas of this instance.
     */
    private void populateCanvas() {
        JScrollPane resultsWrapper = new JScrollPane(results);
        content.addView(resultsWrapper, JHopViewConstants.RESULTS_LABEL);
        content.addView(document, JHopViewConstants.DOC_LABEL);

        add(content, BorderLayout.CENTER);
        add(status, BorderLayout.SOUTH);
        //creates web engine one JavaFX thread
        Platform.runLater((Runnable)()->{
            document.setScene(createWebView());
        });
    }

    /**
     * Fill the default contents of this instances search controls.
     */
    private void populateSearch() {
        JPanel searchBar = new JPanel(new BorderLayout());
        searchBar.add(searchInput, BorderLayout.CENTER);
        searchBar.add(searchButton, BorderLayout.EAST);

        add(searchBar, BorderLayout.NORTH);
    }

    /**
     * Creates a web view capable of displaying web pages.
     * @return A scene containing the JavaFX components used as a web browser.
     */
    private Scene createWebView() {
        WebView webView = new WebView();
        engine = webView.getEngine();
        return new Scene(webView);
    }

    /**
     * @return The writable components of this instances search bar.
     */
    public JTextField getSearchBar() {
        return searchInput;
    }

    /**
     * @return The button used to trigger a search.
     */
    public JButton getSearchControl() {
        return searchButton;
    }
    
    /**
     * @return The list used to display search results.
     */
    public JList getResultList() {
        return results;
    }
    
    /**
     * @return The engine used to render web pages.
     */
    public WebEngine getEngine() {
        return engine;
    }
    
    /**
     * Runs the given process on the JavaFX thread associated with this instances
     * web engine.
     * 
     * @param r The series of instructions to run.
     */
    public void runOnFX(Runnable r) {
        Platform.runLater(r);
    }
    
    /**
     * Add a result to the results view of this instance.
     * 
     * @param result The result to add.
     */
    public void addResult(IndexEntry result){
        ((DefaultListModel)results.getModel()).addElement(result);
    }
    
    /**
     * Clear the results inside this view.
     */
    public void clearResults() {
        ((DefaultListModel)results.getModel()).clear();
    }
    
    /**
     * @return The main canvas of this instance.
     */
    public JHopSwitchPanel getCanvas() {
        return content;
    }
    
    /**
     * Displays the number of results returned by a search.
     * 
     * @param n The number of results.
     */
    public void setStatus(Integer n) {
        status.setText(n + " results");
    }
}
