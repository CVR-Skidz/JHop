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
 * @author cvr-skidz bcc9954 18031335
 */
public class Viewer extends Operation{
    public static final String OPNAME = "--show";
    private final static int PRIORITY = 2, ARGC = 1;
    private String title;
    
    public Viewer(List<String> argv) throws CommandException {
        super(argv, OPNAME);
        init();
        priority = PRIORITY;
    }
    
    @Override
    public Index exec(Index index) {
        if(title == null) return index;
        
        List<IndexEntry> results = index.getPage(title);
        IndexEntry source = null;
        System.out.println(results.size() + " results");
        
        if(results.size() > 1) {
            System.out.println("Multiple pages matched query: ");
            for(IndexEntry entry: results) System.out.println(entry.getUrl());
        }
        else if(results.isEmpty()) return index;
        else source = results.get(0);
        
        display(source, index.getOptions());
        
        return index;
    }
    
    private void display(IndexEntry source, IndexOptions options) {
        if(source == null) return;
        
        try {
            HopConnection connection = new HopConnection(source.getSource());
            connection.connect();
            Response res = connection.getResponse();
            
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
