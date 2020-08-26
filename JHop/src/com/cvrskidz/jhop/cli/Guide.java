package com.cvrskidz.jhop.cli;

import com.cvrskidz.jhop.indexes.Index;
import java.util.Scanner;

public abstract class Guide implements Interactable{
    protected Scanner in;
    protected static Index index;
    
    public Guide(Scanner in) {
        this.in = in;
    }
    
    protected Integer parseInt(String s) {
        try {
            return Integer.parseInt(s);
        }
        catch(NumberFormatException e) {
            return null;
        }
    }
    
    protected String prompt(String message) {
        String userInput;
        
        do {
            System.out.print(message);
            userInput = in.nextLine();
            userInput.trim();
        } while(userInput.isEmpty());
        
        return userInput;
    }
    
    public Scanner getInputScanner() {
        return in;
    }
}
