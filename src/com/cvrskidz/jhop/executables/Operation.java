package com.cvrskidz.jhop.executables;
import com.cvrskidz.jhop.SetFactory;
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
     * Use {@link com.cvrskidz.jhop.Operation#OpFactory} instead.
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
    
    /**
     * Constructs an appropriate Operation based on the supplied operation name. 
     * Or a null reference when supplied an invalid operation.
     * 
     * @param call The operation name.
     * @param argv The operation arguments.
     * @return  A new Operation or null if supplied an invalid call.
     */
    public static Operation OpFactory(String call, List<String> argv) throws CommandException{
        if(argv.isEmpty()) throw new CommandException("Supplied no arguments", call);
        
        switch(call) {
            case Searcher.OPNAME:
                return new Searcher(argv); 
            case Viewer.OPNAME:
                return new Viewer(argv);
            case Crawler.OPNAME:
                return new Crawler(argv);
            case SetFactory.CREATESET:
            case SetFactory.DROPSET:
            case SetFactory.SPECIFY:
            case SetFactory.ENCRYPT:
                return new SetFactory(argv, call);
            default:
                return null;
        }
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
    
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("Operation: ");
        out.append(name);
        out.append("\n\tOptions:");
        out.append(argv);
        
        return out.toString();
    }
}
