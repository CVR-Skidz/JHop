package com.cvrskidz.jhop.parsers;

import com.cvrskidz.jhop.executables.Operation;
import com.cvrskidz.jhop.exceptions.CommandException;
import com.cvrskidz.jhop.executables.OperationFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Takes arguments supplied to a JHop program and creates the appropriate 
 * Operation for each.
 * 
 * @see com.cvrskidz.jhop.executables.Operation
 * @author cvrskidz 18031335
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
     * @see com.cvrskidz.jhop.parsers.ArgumentParser
     * @see com.cvrskidz.jhop.executables.Operation
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
     * @see com.cvrskidz.jhop.executables.Operation
     * @throws com.cvrskidz.jhop.exceptions.CommandException
     */
    public void parse() throws CommandException{
        operations = new ArrayList<>(); 
        List<String> args = new ArrayList<>();
        String operationName = null;
        
        //split operations and values
        for(String s: arguments) {
            if(isOperation(s)) {
                //add operation in buffers
                if(operationName != null) { 
                    operations.add(getInstance(operationName, args));
                    args.clear();
                }
                //start new operation
                operationName = s; 
            }
            else args.add(s);
        }
        
        //handle tokens left in scope
        if (operationName != null) operations.add(getInstance(operationName, args));
    }
    
    /**
     * Returns an new appropriate Operation instance based on the call supplied.
     * <p> A clone of the arguments supplied are created to prevent multiple 
     * operations sharing a reference to the same set of arguments.
     * 
     * @param call The name of the operation.
     * @param args The arguments supplied to the operation.
     * @return A new Operation.
     * @see com.cvrskidz.jhop.executables.Operation
     */
    private Operation getInstance(String call, List<String> args) throws CommandException{
        List<String> argsCopy = new ArrayList(args);
        OperationFactory factory = new OperationFactory(argsCopy, call);
        Operation op = factory.produce();
        
        if(op == null) throw new CommandException("Unkown", call);
        
        return op;
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
     * @see com.cvrskidz.jhop.executables.Operation
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
     * Joins all strings present in an array, separated by spaces.
     * 
     * @param a The array to join.
     * @return The space separated String.
     */
    private String stringify(String[] a) {
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
        
        Iterator<Operation> it = operations.iterator();
        while(it.hasNext()) {
            Operation current = it.next();
            out.append(current.toString());
            if(it.hasNext()) out.append("\n");
        }
        
        return out.toString();
    }
}
