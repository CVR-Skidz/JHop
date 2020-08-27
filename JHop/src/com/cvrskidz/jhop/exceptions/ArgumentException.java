package com.cvrskidz.jhop.exceptions;

import com.cvrskidz.jhop.executables.Operation;

/**
 * Exception caused by an invalid argument supplied to a JHop command. This could involve 
 * supplying an incorrect argument, too many arguments, or too little arguments. The exact violation
 * caused by the argument is specified by the exceptions message.
 *
 * @see com.cvrskidz.jhop.executables.Command
 * @author bcc9954 18031335 cvr-skidz 
 */
public class ArgumentException extends CommandException{
    // format of error message when an argument is missing or additional arguments are supplied
    private static final String COUNT_FORMAT = "Expected %d arguments, given %d";    
    // format of error message when an incorrect argument are supplied
    private static final String TYPE_FORMAT = "Expected %s, given '%s'";
    
    /**
     * Constructs an ArgumentException, specifying the supplied number of arguments is invalid.
     * 
     * @param expected the expected amount of arguments.
     * @param op The command where the error occurred.
     * @return A new ArgumentException
     * @see ArgumentException
     */
    public ArgumentException(int expected, Operation op) {
        super(String.format(COUNT_FORMAT, expected, op.getArgc()), op.getName());
    }
    
    /**
     * Constructs an ArgumentException, specifying the supplied argument is invalid.
     * 
     * @param type The expected type of the argument.
     * @param arg The violating argument.
     * @param op The command where the error occurred.
     * @return A new ArgumentException
     * @see ArgumentException
     */
    public ArgumentException(String type, String arg, Operation op) {
        super(String.format(TYPE_FORMAT, type, arg), op.getName());
    }
}
