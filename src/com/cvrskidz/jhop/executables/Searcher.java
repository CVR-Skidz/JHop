package com.cvrskidz.jhop.executables;
import java.util.List;

/**
 * @author cvr-skidz bcc9954 18031335
 */
public class Searcher extends Operation{
    public final static String OPNAME = "--search";
    private final static int PRIORITY = 2;
    protected String term;
    
    public Searcher(List<String> argv) {
        super(argv, OPNAME);
        this.term = argv.get(0);
        this.priority = PRIORITY;
    }
    
    @Override
    public void exec() {
        //stub
        System.out.println("Searching for " + term);
    }
}
