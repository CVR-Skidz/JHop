package com.cvrskidz.jhop.exceptions;

public class CommandException extends Exception{
    private String name;
    
    public CommandException(String message, String name) {
        super(message);
        this.name = name;
    }
    
    @Override
    public String getMessage() {
        return super.getMessage() + " Command: " + name;
    }
}
