package com.cvrskidz.jhop.cli;

import com.cvrskidz.jhop.JHop;

/**
 * A printable menu containing interactive options to 
 * interact with JHop from within the command line.
 */
public class JHopMenu{
    private JHop master;
    
    /**
     * Options in the menu.
     */
    private enum MenuOption {
        SEARCH("Search an index"), 
        CRAWL("Create an index"), 
        LIST("Display index information"), 
        LOAD("Set active index"), 
        DROP("Delete an index"),
        WIDTH("Set display width"),
        HELP("Help"),
        QUIT("Quit");
        
        private String message;
        
        private MenuOption(String m) { message = m; }
        
        @Override
        public String toString() { return message; }
    }
    
    /**
     * Construct a new menu.
     *
     * @param master An Instance of a JHop application this menu belongs to.
     */
    public JHopMenu(JHop master) {
        this.master = master;
    }
    
    /**
     * Print this menu.
     */
    public void display() {
        MenuOption[] options = MenuOption.values();
        for(int i = 0; i < options.length; ++i) {
            System.out.println((i+1) + ": " + options[i]);
        }
    }
    
    /** 
     * @return A new Guide based on the selected option.
     */
    public Guide select(int option) {
        MenuOption[] options = MenuOption.values();
        --option;   //normalize index
        
        if(option > options.length - 1 || option < 0) {
            System.out.println("Invalid Option");
            return null;
        }
        
        MenuOption userOption = options[option];
        
        // create relevant guide
        switch(userOption) {
            case SEARCH:
                return new SearchGuide(master.getInputScanner());
            case CRAWL:
                return new CreateGuide(master.getInputScanner());
            case LIST:
                return new ListGuide(master.getInputScanner());
            case LOAD:
                return new SetGuide(master.getInputScanner());
            case DROP:
                return new DeleteGuide(master.getInputScanner());
            case WIDTH:
                return new WidthGuide(master.getInputScanner());
            case QUIT:
                master.stop();
                return null;
            default:
                return new HelpGuide(master.getInputScanner());
        } 
    }
}
