/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cvrskidz.jhop;
import java.util.ArrayList;
import java.util.List;

/**
 * Takes arguments supplied to a JHop program and creates the appropriate 
 * Operation for each.
 * 
 * @see com.cvrskidz.jhop.Operation
 * @author cvr-skidz bcc9954 18031335
 */
public class ArgumentParser {
    private static final String OP_SYNTAX = "--[A-z]+";
    private List<Operation> operations;
    private final String commandLine;
    private String[] arguments;
    
    /**
     * Default ArgumentParser constructor. Sets commandLine to empty string.
     */
    public ArgumentParser() {
        commandLine = "";
    }
    
    /**
     * ArgumentParser constructor required to create functional Operation(s) 
     * to be executed by a JHop program. 
     * <p> For example to run a 
     * "jhop --search {term}" call this constructor needs to be called.
     * 
     * @param argv The arguments supplied by the shell to the main method.
     * 
     * @see com.cvrskidz.jhop.ArgumentParser
     * @see com.cvrskidz.jhop.Operation
     */
    public ArgumentParser(String[] argv) {
        arguments = argv;
        commandLine = stringify(arguments);
    }
    
    /**
     * Parses tokens supplied to this ArgumentParser instance into valid 
     * Operation(s), and stores said Operation(s) in the instance state.
     * <p> This method is required to be called to create Operation(s) for 
     * a JHop program after this object is instantiated.
     * 
     * @see com.cvrskidz.jhop.Operation
     */
    public void parse() {
        operations = new ArrayList<>(); 
        List<String> args = new ArrayList<>();
        String opName = null;
        
        //split operations and values
        for(String s: arguments) {
            if(isOperation(s)) {
                //add operation in buffers
                if(opName != null) { 
                    operations.add(opHelper(opName, args));
                    args.clear();
                }
                //start new operation
                opName = s; 
            }
            else args.add(s);
        }
        
        //handle tokens left in scope
        if (opName != null) operations.add(opHelper(opName, args));
    }
    
    /**
     * Returns an new appropriate Operation instance based on the call supplied.
     * <p> A clone of the arguments supplied are created to prevent multiple 
     * operations sharing a reference to the same set of arguments.
     * 
     * @param call The name of the operation.
     * @param args The arguments supplied to the operation.
     * @return A new Operation.
     * @see com.cvrskidz.jhop.Operation
     */
    private Operation opHelper(String call, List<String> args) {
        List<String> argsCopy = new ArrayList<>(args);
        return Operation.OpFactory(call, argsCopy);
    }
    
    /**
     * Checks the supplied string against a regular expression identifying an 
     * operation supplied to a JHop program.
     * <p> "--search" in "jhop --search {term}" is an operation.
     * 
     * @param s The String to match, required to be a single token 
     * with no space.
     * @return true if the token is an operation, false otherwise.
     */
    private boolean isOperation(String s) {
        return s.matches(OP_SYNTAX);
    }
    
    /**
     * "Gets" the operations parsed and stored in this objects state.
     * 
     * @return A List of Operation(s)
     * @see java.util.List
     * @see com.cvrskidz.jhop.Operation
     */
    public List<Operation> getOperations() {
        return operations;
    }

    /**
     * "Gets" the string of tokens supplied to a JHop program.
     * <p> Given "--search" and "exTerm" the command line will appear as 
     * "jhop --search exTerm"
     * 
     * @return The space-concatenated string of tokens supplied to this object. 
     */
    public String getCommandLine() {
        return commandLine;
    }
    
    /**
     * Joins all strings present in array, separated by spaces.
     * 
     * @param a The array to join.
     * @return The space separated String.
     */
    public static String stringify(String[] a) {
        StringBuilder out = new StringBuilder();
        int end = a.length;
        
        for(int i = 0; i < end; ++i) {
            out.append(a[i]);
            if(i != end - 1) out.append(" ");
        }
        
        return out.toString();
    }
    
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        
        for(Operation op: operations) {
            out.append(op.toString());
            out.append("\n");
        }
        
        return out.toString();
    }
}
