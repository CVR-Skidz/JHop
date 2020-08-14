package com.cvrskidz.jhop.exceptions;

public class PriorityViolationException extends CommandException{
    private static final String FORMAT = " can not be used in conjunction with";
    
    public PriorityViolationException(String valid, String invalid) {
        super(invalid + FORMAT, valid);
    }
}
