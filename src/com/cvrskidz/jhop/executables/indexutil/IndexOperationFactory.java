package com.cvrskidz.jhop.executables.indexutil;
import com.cvrskidz.jhop.exceptions.CommandException;
import java.util.List;
import com.cvrskidz.jhop.executables.JHopFactory;

/**
 * An IndexOperationFactory produces the appropriate IndexOperation from
 * given command line arguments. 
 * <p>
 * An IndexOperationFactory requires a call (option name) and any arguments passed
 * to this call.
 * 
 * @author cvrskidz 18031335
 * @see IndexOperation
 */
public class IndexOperationFactory implements JHopFactory<IndexOperation> {
    private final String name;
    private final List<String> args;
    
    /** 
     * Constructs an IndexOperation with the supplied arguments and name.
     * 
     * @param argv Operation arguments.
     * @param call Operation name.
     * @see com.cvrskidz.jhop.executables.indexutil.IndexOperation 
     */
    public IndexOperationFactory(List<String> argv, String call) {
        name = call;
        args = argv;
    }
    
    /**
     * Returns a method reference to the correct index operation as specified by
     * this instances name. Such as "--create", "--set", and "--drop".
     * 
     * @return A reference to the correct IndexOperation or null if this 
     * object contains an unknown operation.
     * @throws CommandException If the associated operation could not be created.
     * @see IndexOperation
     */
    @Override
    public IndexOperation produce() throws CommandException {
        switch(name) {
            case IndexDropper.OPNAME:
                return new IndexDropper(args);
            case IndexReader.OPNAME:
                return new IndexReader(args);
            case IndexWriter.OPNAME:
                return new IndexWriter(args);
            case IndexInspector.OPNAME:
                return new IndexInspector(args);
            default:
                return null;
        }
    }
    
    public String getOperationName() {
        return name;
    }

    public List<String> getArgs() {
        return args;
    }
}
