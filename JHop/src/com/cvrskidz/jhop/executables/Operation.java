package com.cvrskidz.jhop.executables;
import com.cvrskidz.jhop.exceptions.CommandException;
import java.util.List;

/**
 * Provides an executable behavior to a JHop program, identifying the 
 * operation to perform as specified by the supplied 
 * name.
 * 
 * @author cvr-skidz bcc9954 18031335
 */
public abstract class Operation implements Executable{
    //The priority of commands that do not allow other commands to be used in the same call
    public final static int MASTER_PR = -1; 
    
    protected String name;
    protected int argc, priority;
    protected List<String> argv;
    protected boolean error;
    protected CommandException errorException;
    
    /**
     * Default Operation constructor.
     */
    public Operation() {
        argv = null;
        error = false;
        name = null;
        priority = -1;
    }
    
    /**
     * Constructs an Operation object. This constructor is required to be 
     * invoked to provide a functional Operation.
     * 
     * <p><strong>This constructor is not intended to be invoked manually.</strong>
     * Use {@link OperationFactory} instead.
     * 
     * @param argv Arguments supplied to the operation.
     * @param name Operation name. Must be a valid operation as specified by 
     * the JHop program (see jhop usage).
     * 
     */
    public Operation(List<String> argv, String name) {
        super();
        this.argv = argv;
        this.argc = argv.size();
        this.name = name;
    }
    
    @Override
    public CommandException getError(){
        return errorException;
    }
    
    @Override
    public boolean success(){
        return !error;
    }
    
    /**
     * "Gets" the argument count of this object. 
     * 
     * @return The number of arguments held by the operation.
     */
    public int getArgc() {
        return argc;
    }

    /**
     * "Gets" the arguments of this object. 
     * 
     * @return The arguments held by the operation.
     */
    public List<String> getArgv() {
        return argv;
    }

    /**
     * "Gets" the operation name of this object. 
     * 
     * @return The name held by the operation.
     */
    public String getName() {
        return name;
    }
    
    /**
     * "Gets" the operation priority of this object.
     * 
     * @return The priority held by the operation.
     */
    public int getPriority() {
        return priority;
    }
    
    /**
     * Sets the error of this operation to the specified exception, wrapping
     * it to a CommandException if necessary.
     * 
     * @param e The exception to set.
     * @see com.cvrskidz.jhop.exceptions.CommandException
     */
    protected void setError(Exception e) {
        this.error = true;
        
        if(e instanceof CommandException) {
            errorException = (CommandException)e;
        }
        else {
            //wrap exception
            String message = e.getMessage();
            CommandException wrapped = new CommandException(message, name);
            errorException = wrapped;
        }
    }
    
    /**
     * Sets the error of this operation to the specified exception, wrapping
     * it to a CommandException if necessary.
     * 
     * @param e The exception to set.
     * @param prefix A message to preceed the exceptions error message.
     * @see com.cvrskidz.jhop.exceptions.CommandException
     */
    protected void setError(Exception e, String prefix) {
        this.error = true;
        
        if(e instanceof CommandException) {
            errorException = (CommandException)e;
        }
        else {
            //wrap exception
            String message = prefix + e.getMessage();
            CommandException wrapped = new CommandException(message, name);
            errorException = wrapped;
        }
    }
    
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("Operation: ");
        out.append(name);
        out.append("\n\tOptions:");
        out.append(argv);
        
        return out.toString();
    }
    
    /**
     * Setup this operation, performing any necessary preparation or argument validation
     * to ensure this instance can be executed.
     * 
     * @throws CommandException If the operation can not be successfully initiated.
     */
    protected abstract void init() throws CommandException;
}
