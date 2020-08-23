package com.cvrskidz.jhop.cli;

import java.util.Scanner;

public abstract class Guide implements Interactable{
    protected Scanner in;
    
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
}
