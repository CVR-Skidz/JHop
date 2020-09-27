package com.cvrskidz.jhop.cli;

import com.cvrskidz.jhop.indexes.Index;
import java.util.Scanner;

/**
 * An interactive set of actions used to guide a user through JHop.
 * A Guide is responsible for approachable querying the required input
 * to create and run an Executable which would usually be supplied as arguments
 * to the JHop program from the CLI.
 *
 * @see com.cvrskidz.jhop.executables.Executable
 */
public abstract class Guide implements Interactable{
    protected Scanner in;
    protected static Index index;   // active index for all guides to operate on
    
    /**
     * Construct a new Guide.
     *
     * @param in The input scanner associated with the JHop instance.
     */
    public Guide(Scanner in) {
        this.in = in;
    }
    
    /**
     * Parses a string into an Integer without throwing an exception.
     * 
     * @param s The string to parse
     * @return The given string as an Integer or null
     */
    protected Integer parseInt(String s) {
        try {
            return Integer.parseInt(s);
        }
        catch(NumberFormatException e) {
            return null;
        }
    }

    /**
     * Prompts the user for input with the supplied message.
     *
     * @param message The message to display to the user.
     * @return The supplied user input.
     */ 
    protected String prompt(String message) {
        String userInput;
        
        do {
            System.out.print(message);
            userInput = in.nextLine();
            userInput.trim();
        } while(userInput.isEmpty());
        
        return userInput;
    }
    
    /**
     * @return The input scanner for this Guide.
     */
    public Scanner getInputScanner() {
        return in;
    }
}
