package com.cvrskidz.jhop.executables;
import com.cvrskidz.jhop.exceptions.ArgumentException;
import com.cvrskidz.jhop.exceptions.CommandException;
import com.cvrskidz.jhop.indexes.Index;
import com.cvrskidz.jhop.indexes.IndexEntry;
import java.util.List;
import java.util.Set;

/**
 * @author cvr-skidz bcc9954 18031335
 */
public class Searcher extends SearchOperation{
    public final static String OPNAME = "--search";
    private final static int PRIORITY = 2, ARGC = 1;
    protected String term;
    
    public Searcher(List<String> argv) throws CommandException{
        super(argv, OPNAME);
        init();
        priority = PRIORITY;
    }
    
    @Override
    public Index exec(Index index) {
        if(index == null) {
            setError(new CommandException("Index is empty", name));
        }
        else {
            Set<IndexEntry> results = index.getPagesContaining(term);
            
            if(results == null) System.out.println("No results found");
            else {
                searchResults = results.size();
                System.out.println(searchResults + " results");
                for(IndexEntry page: results) System.out.println(page);
            }
        }
        
        return index;
    }
    
    @Override
    protected void init() throws CommandException {
        if(argc != ARGC) throw new ArgumentException(ARGC, this);
        else term = argv.get(0);
    }
}
