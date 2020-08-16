package com.cvrskidz.jhop.executables;

import com.cvrskidz.jhop.executables.indexutil.IndexOperationFactory;
import com.cvrskidz.jhop.exceptions.CommandException;
import java.util.List;

public class OperationFactory {
    private final String name;
    private final List<String> args;
    
    public OperationFactory(List<String> argv, String call) {
        name = call;
        args = argv;
    }
    
    /**
     * Constructs an appropriate Operation based on the supplied operation name. 
     * Or a null reference when supplied an invalid operation.
     * 
     * @return  A new Operation or null if supplied an invalid call.
     */
    public Operation produce() throws CommandException{
        if(args.isEmpty()) throw new CommandException("Supplied no arguments", name);
        
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
