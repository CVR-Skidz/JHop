package com.cvrskidz.jhop.exceptions;

import com.cvrskidz.jhop.executables.Operation;

public class ArgumentException extends CommandException{
    private static final String COUNT_FORMAT = "Expected %d arguments, given %d";
    private static final String TYPE_FORMAT = "Expected %s, given '%s'";
    
    public ArgumentException(int expected, Operation op) {
        super(String.format(COUNT_FORMAT, expected, op.getArgc()), op.getName());
    }
    
    public ArgumentException(String type, String arg, Operation op) {
        super(String.format(TYPE_FORMAT, type, arg), op.getName());
    }
}
