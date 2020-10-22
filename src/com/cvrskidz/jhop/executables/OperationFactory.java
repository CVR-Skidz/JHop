package com.cvrskidz.jhop.executables;

import com.cvrskidz.jhop.indexutil.IndexOperationFactory;
import com.cvrskidz.jhop.exceptions.CommandException;
import java.util.List;

/**
 * An OperationFactory provides an automatic way to produce the correct Operation
 * from a command line argument.
 * <p>
 * The operation factory requires a name of a operation and additional arguments
 * supplied with the call to that operation.     
 * 
 * @see Operation
 * @author cvrskidz 18031335
 */
public class OperationFactory implements JHopFactory<Operation>{
    private final String name;
    private final List<String> args;
    
    /**
     * Construct a new OperationFactory, setting the call name and arguments
     * supplied to it.
     * 
     * @param argv The arguments supplied to the operation.
     * @param call The name of the operation called.
     */
    public OperationFactory(List<String> argv, String call) {
        name = call;
        args = argv;
    }
    
    /**
     * Constructs an appropriate Operation based on the supplied operation name. 
     * Or a null reference when supplied an invalid operation.
     * 
     * @return  A new Operation or null if supplied an invalid call.
     * @throws CommandException If the associated operation could not be created.
     */
    @Override
    public Operation produce() throws CommandException{
        switch(name) {
            case Searcher.OPNAME:
                return new Searcher(args); 
            case Viewer.OPNAME:
                return new Viewer(args);
            case Crawler.OPNAME:
                return new Crawler(args);
            default:
                IndexOperationFactory utilFactory = new IndexOperationFactory(args, name);
                return utilFactory.produce();
        }
    }
}
