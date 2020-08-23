package com.cvrskidz.jhop.executables.indexutil;
import com.cvrskidz.jhop.exceptions.CommandException;
import java.util.List;

/**
 * An executable object to provide a JHop program set functions such as  
 * reading, writing, and encrypting. 
 * <p> A IndexOperationFactory does not create a Set unless 
 the "--create" operation is supplied.
 * 
 * @author cvrskidz bcc9954 18031335
 * @see com.cvrskidz.jhop.Operation
 */
public class IndexOperationFactory{
    private final String name;
    private final List<String> args;
    
    /** 
     * Constructs an IndexOperation with the supplied arguments and name.
     * 
     * @param argv Operation arguments.
     * @param call Operation name.
     * @see com.cvrskidz.jhop.IndexOperation 
     */
    public IndexOperationFactory(List<String> argv, String call) {
        name = call;
        args = argv;
    }
    
    /**
     * Returns a method reference to the correct set operation as specified by
     * this instances name. Such as "--create", "--set", "--encrypt", and "--drop".
     * 
     * @return A method reference to the correct set operation or null if this 
     * object contains an unknown operation.
     */
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
}
