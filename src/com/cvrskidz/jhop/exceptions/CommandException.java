package com.cvrskidz.jhop.exceptions;

/**
 * A general Exception caused when creating or executing a JHop command.
 * 
 * @author cvrskidz 18031335
 */
public class CommandException extends Exception{
    private String name; // name of the command.
    
    /** 
     * Construct a new CommandExcpetion specifying an error message and the name of the command where the error occurred.
     *
     * @param message The error message to display.
     * @param name The name of the command where the error occurred.
     */
    public CommandException(String message, String name) {
        super(message);
        this.name = name;
    }
    
    @Override
    public String getMessage() {
        return super.getMessage() + " Command: " + name;
    }
}
