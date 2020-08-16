package com.cvrskidz.jhop.executables;
import com.cvrskidz.jhop.exceptions.CommandException;
import com.cvrskidz.jhop.indexes.Index;
import java.util.List;
import java.util.Set;

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
    public Index exec(Index index) {
        if(index == null) {
            setError(new CommandException("Index is empty", name));
        }
        else {
            Set<String> results = index.getPagesContaining(term);
            
            if(results == null) System.out.println("No results found");
            else {
                for(String page: results) System.out.println(page);
            }
        }
        
        return index;
    }
}
