package com.cvrskidz.jhop.executables;

import com.cvrskidz.jhop.network.URLParser;
import com.cvrskidz.jhop.network.Response;
import com.cvrskidz.jhop.network.HopConnection;
import com.cvrskidz.jhop.indexes.IndexEntry;
import com.cvrskidz.jhop.indexes.Index;
import com.cvrskidz.jhop.exceptions.CommandException;
import com.cvrskidz.jhop.exceptions.ArgumentException;
import com.cvrskidz.jhop.exceptions.CrawlerException;
import com.cvrskidz.jhop.parsers.HTMLTextParser;
import com.cvrskidz.jhop.parsers.HTMLReferenceParser;
import com.cvrskidz.jhop.parsers.Parser;
import java.io.IOException;
import java.util.List;
import java.util.ArrayDeque;
import java.util.Deque;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * An Executable capable of crawling URLs from a source, extracting further
 * URLs to hop and extract text from. The result is a searchable index of the
 * contents of each crawled page.
 * <p>
 * A crawler requires the following arguments:
 * <ul>
 *  <li> Source URL: The URL to start crawling from
 *  <li> Tag Attribute: An attribute (such as id, class, etc) tags that contain text in each hop have. 
 *  <li> Tag Attribute Value: A value the specified attribute must match to be parsed.
 *  <li> Hop Limit: The maximum number of hops to crawl.
 * </ul>
 * @author cvrskidz 18031335
 */
public class Crawler extends Operation{
    public static final String OPNAME = "--crawl";
    public static final int PRIORITY = 1, ARGC = 4;
    
    //query information;
    private String source, attribute, value;
    
    // hop information
    private HopConnection sourceConnection, previousHop;
    private int currentHop, maxHops;
    private Deque<HopConnection> hops;
    
    /**
     * Construct a new Crawler from the given arguments.
     * A Crawler expects four arguments.
     * 
     * @param argv The crawler arguments.
     * @throws CommandException If the supplied arguments were invalid.
     */
    public Crawler(List<String> argv) throws CommandException{
        super(argv, OPNAME);
        init();
        priority = PRIORITY;
        previousHop = null;
        sourceConnection = null;
        hops = new ArrayDeque<>();
    }
    
    /**
     * Crawl web pages from the source of this instance. Indexing their contents.
     * 
     * @param index The index to store into.
     * @return A reference to the supplied index after crawling.
     */
    @Override
    public Index exec(Index index) {
        index.getOptions().setQuery(attribute, value);
        
        try {
            sourceConnection = new HopConnection(source);
            crawl(sourceConnection, index);
            //System.out.println(index);    // display index after crawling?
        }
        catch(IOException e) {
            setError(e);
        }
        
        return index;
    }
    
    @Override
    protected void init() throws CommandException{
        if(argv.size() != ARGC) throw new ArgumentException(ARGC, this);
        
        String hint = argv.get(3);  // hop limit buffer
        source = argv.get(0);       // source URL position
        attribute = argv.get(1);    // attribute query position
        value = argv.get(2);        // value query position

        // verify type of hop limit
        try {
            maxHops = Integer.parseInt(hint);
        }
        catch(NumberFormatException e) {
            throw new ArgumentException("Integer", hint, this);
        }
    }
    
    /**
     * Parse and index the contents of the supplied URL, and extract any further 
     * internal hops from the current page.
     * 
     * @param url The URL to crawl.
     * @param index The Index to store into.
     */
    private void crawl(HopConnection url, Index index) {
        IndexEntry query = new IndexEntry(url);
        boolean isSelfHop = previousHop == null ? false : previousHop.equals(url);

        // ensure page not already indexed and is not a reference to the previous hop
        if(isSelfHop || index.contains(query)) {
            if(!hops.isEmpty()) crawl(hops.pop(), index);
            return;
        }
        
        // status message
        System.out.println(currentHop + ": Crawling " + url.getURL());
        
        try{
            url.connect();                              // connect to host
            previousHop = url;                          // update hops if success
            Response response = url.getResponse();      // get response
            hops.addAll(parseURLs(response));           // identify next hops
            response.setContents(parseText(response));  // parse text
            
            if(!response.getContents().isEmpty())       // index if there is text
                index.index(response);
            
            if(!hops.isEmpty() && currentHop++ < maxHops) // continue crawling
                crawl(hops.pop(), index);
            
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
        // parse all references in response
        String html = res.getContents();
        Parser<Elements> parser = new HTMLReferenceParser(html, attribute, value);
        Elements references = parser.parse().output();
        Deque<HopConnection> validReferences = new ArrayDeque<>();  
        
        // ensure each reference is valid
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
    
    /**
     * Extract the text based on this instances query from the given response.
     * 
     * @param res The contents to extract text from.
     * @return The extracted text.
     */
    private String parseText(Response res) {
        String html = res.getContents();
        Parser<String> parser = new HTMLTextParser(html, attribute, value);
        return parser.parse().output();
    }
    
    /**
     * @return The tag attribute specified by this query.
     */
    public String getAttribute() {
        return attribute;
    }

    /**
     * @return The value specified by this query.
     */
    public String getValue() {
        return value;
    }

    /**
     * @return The original connection that initiated crawling.
     */
    public HopConnection getSourceConnection() {
        return sourceConnection;
    }
    
    /**
     * @return The last connection crawled
     */
    public HopConnection getLastHop() {
        return previousHop;
    }

    /**
     * @return The current connection being crawled.
     */
    public int getHop() {
        return currentHop;
    }
}
