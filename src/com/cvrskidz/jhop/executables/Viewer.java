package com.cvrskidz.jhop.executables;

import com.cvrskidz.jhop.exceptions.ArgumentException;
import com.cvrskidz.jhop.exceptions.CommandException;
import com.cvrskidz.jhop.indexes.Index;
import com.cvrskidz.jhop.indexes.IndexEntry;
import com.cvrskidz.jhop.indexes.IndexOptions;
import com.cvrskidz.jhop.network.HopConnection;
import com.cvrskidz.jhop.network.Response;
import com.cvrskidz.jhop.parsers.HTMLTextParser;
import java.io.IOException;
import java.util.List;

/**
 * Displays the contents of a page contained in an index, making a 
 * web request to the associated host.
 * 
 * @author cvrskidz 18031335
 */
public class Viewer extends SearchOperation{
    public static final String OPNAME = "--show";
    private final static int PRIORITY = 2, ARGC = 1;
    private String title;
    
    /**
     * Constructs a new Viewer. A Viewer expects 1 argument.
     * 
     * @param argv The arguments to supply to the operation.
     * @throws CommandException If the arguments were invalid.
     */
    public Viewer(List<String> argv) throws CommandException {
        super(argv, OPNAME);
        init();
        priority = PRIORITY;
    }
    
    /**
     * Ensure the index contains a unique result for the requested page,
     * then make and parse the request to display the page contents.
     * <p>
     * Note a terminal emulator that supports line wrapping is strongly recommended.
     * 
     * @param index The index to search.
     * @return A reference to the same index after viewing.
     */
    @Override
    public Index exec(Index index) {
        if(title == null) return index;
        
        List<IndexEntry> results = index.getPage(title);
        IndexEntry source = null;
        searchResults = results.size();
        System.out.println(searchResults + " results");
        
        // requires unique page
        if(results.size() > 1) {
            System.out.println("Multiple pages matched query: ");
            for(IndexEntry entry: results) System.out.println(entry.getUrl());
        }
        else if(results.isEmpty()) return index;
        else source = results.get(0);
        
        display(source, index.getOptions());
        
        return index;
    }
    
    /**
     * Make and parse the specified request, displaying it's contents.
     * 
     * @param source The entry specifying the requested content.
     * @param options The options specifying the index query.
     */
    private void display(IndexEntry source, IndexOptions options) {
        if(source == null) return;
        
        try {
            // make request
            HopConnection connection = new HopConnection(source.getSource());
            connection.connect();
            Response res = connection.getResponse();
            
            // display text
            String html = res.getContents();
            String id = options.getAttribute();
            String val = options.getValue();
            HTMLTextParser parser = new HTMLTextParser(html, id, val);
            parser.parse();
            System.out.println(parser.output());
            
            connection.disconnect();
        }
        catch(IOException e) {
            setError(e, "Error connecting to " + source.getSource());
        }
    }
    
    @Override
    protected void init() throws CommandException{
        if(argc != ARGC) throw new ArgumentException(ARGC, this);
        else title = argv.get(0);
    }
}
