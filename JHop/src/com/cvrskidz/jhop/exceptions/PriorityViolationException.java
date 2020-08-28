package com.cvrskidz.jhop.exceptions;

/**
 * Exception warning two incompatible commands have been used in conjunction with one another. 
 * 
 * @author bcc9954 18031335 cvr-skidz 
 */
public class PriorityViolationException extends CommandException{
    private static final String FORMAT = " can not be used in conjunction with";

    /**
     * Constructs a new PriorityViolationException specifying the valid command prevented by the invalid command.
     * 
     * @param valid The name of the valid command prevented from being executed.
     * @param invalid The name of the invalid command supplied.
     */    
    public PriorityViolationException(String valid, String invalid) {
        super(invalid + FORMAT, valid);
    }
}
