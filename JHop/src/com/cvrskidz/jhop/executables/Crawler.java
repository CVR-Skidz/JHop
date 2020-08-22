package com.cvrskidz.jhop.executables;

import com.cvrskidz.jhop.parsers.HTMLTextParser;
import com.cvrskidz.jhop.parsers.HTMLReferenceParser;
import com.cvrskidz.jhop.parsers.Parser;
import com.cvrskidz.jhop.exceptions.*;
import com.cvrskidz.jhop.indexes.*;
import com.cvrskidz.jhop.network.*;
import java.io.IOException;
import java.util.List;
import java.util.ArrayDeque;
import java.util.Deque;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author cvr-skidz bcc9954 18031335
 */
public class Crawler extends Operation{
    public static final String OPNAME = "--crawl";
    public static final int PRIORITY = 1, ARGC = 4;
    
    private String source, type, id;
    private HopConnection sourceConnection, previousHop;
    private int currentHop, maxHops;
    private Deque<HopConnection> hops;
    
    public Crawler(List<String> argv) throws CommandException{
        super(argv, OPNAME);
        init();
        priority = PRIORITY;
        previousHop = null;
        sourceConnection = null;
        hops = new ArrayDeque<>();
    }
    
    @Override
    public Index exec(Index index) {
        index.getOptions().setQuery(type, id);
        
        try {
            sourceConnection = new HopConnection(source);
            crawl(sourceConnection, index);
            System.out.println(index);
        }
        catch(IOException e) {
            setError(e);
        }
        
        return index;
    }
    
    @Override
    protected void init() throws CommandException{
        if(argv.size() != ARGC) throw new ArgumentException(ARGC, this);
        
        String hint = argv.get(3);
        source = argv.get(0);
        type = argv.get(1);
        id = argv.get(2);

        try {
            maxHops = Integer.parseInt(hint);
        }
        catch(NumberFormatException e) {
            throw new ArgumentException("Integer", hint, this);
        }
    }
    
    private void crawl(HopConnection url, Index index) {
        IndexEntry query = new IndexEntry(url);
        boolean isSelfHop = previousHop == null ? false : previousHop.equals(url);

        if(isSelfHop || index.contains(query)) {
            if(!hops.isEmpty()) crawl(hops.pop(), index);
            return;
        }
        
        System.out.println(currentHop + ") Crawling: " + url.getURL());
        
        try{
            url.connect();
            previousHop = url;
            Response response = url.getResponse();
            hops.addAll(parseURLs(response));
            response.setContents(parseText(response));
            if(!response.getContents().isEmpty()) index.index(response);
            if(!hops.isEmpty() && currentHop++ < maxHops) crawl(hops.pop(), index);
            
            url.disconnect();
        }
        catch(IOException | CrawlerException e) {
            setError(e, "Error establishing connection to ");
        }
        
    }
    
    /**
     * Invoke a parser on the supplied HTML document to extract all values from 
     * nodes within the body element containing a "href" attribute.
     * 
     * @param html A HTML document.
     * @return The URLs contained within the body of the supplied HTML.
     */
    private Deque<HopConnection> parseURLs(Response res) throws IOException, CrawlerException{
        String html = res.getContents();
        Parser<Elements> parser = new HTMLReferenceParser(html, type, id);
        Elements references = parser.parse().output();
        Deque<HopConnection> validReferences = new ArrayDeque<>();  

        for(Element ref: references) {
            String src = ref.attributes().get("href");
            //parse URL to valid format
            Parser<HopConnection> refParser = new URLParser(src, previousHop);
            HopConnection hop = refParser.parse().output();
            if(hop == null) continue;
            
            validReferences.add(hop);
        }
        
        return validReferences;
    }
    
    private String parseText(Response res) {
        String html = res.getContents();
        Parser<String> parser = new HTMLTextParser(html, type, id);
        return parser.parse().output();
    }
    
    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public HopConnection getSourceConnection() {
        return sourceConnection;
    }
    
    public HopConnection getLastHop() {
        return previousHop;
    }

    public int getHop() {
        return currentHop;
    }
}
