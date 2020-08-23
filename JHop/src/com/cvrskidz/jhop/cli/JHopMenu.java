package com.cvrskidz.jhop.cli;

import java.util.Scanner;

public class JHopMenu{
    private enum MenuOption {
        SEARCH("Search an index"), 
        CRAWL("Create an index"), 
        LIST("View available indexes"), 
        LOAD("Load an index"), 
        DROP("Delete an index"),
        QUIT("Quit");
        
        private String message;
        
        private MenuOption(String m) { message = m; }
        
        @Override
        public String toString() { return message; }
    }
    
    public void display() {
        MenuOption[] options = MenuOption.values();
        for(int i = 0; i < options.length; ++i) {
            System.out.println((i+1) + ") " + options[i]);
        }
    }
    
    public void select(int option, Scanner input) {
        MenuOption userOption = MenuOption.values()[option];
        
        switch(userOption) {
            
        } 
    }
}
