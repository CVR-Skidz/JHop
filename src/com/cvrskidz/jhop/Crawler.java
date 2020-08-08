package com.cvrskidz.jhop;
import com.cvrskidz.jhop.exceptions.InvalidArgumentException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

// TODO: Hanlde absolute URLs

/**
 *
 * @author cvr-skidz bcc9954 18031335
 */
public class Crawler extends Operation{
    //stubs
    public static final String OPNAME = "--crawl";
    public static final int PRIORITY = 1, ARGC = 4;
    
    private static Map<String, Integer> history = new HashMap<String, Integer>();
    private String source, type, id;
    private HopConnection sourceConnection, previousHop;
    private int currentHop, maxHops;
    private Deque<HopConnection> hops;
    
    public Crawler(List<String> argv) throws InvalidArgumentException{
        super(argv, OPNAME);
        if(argv.size() != ARGC) 
            throw new InvalidArgumentException("Exepected " + ARGC + " arguments but got " + argv.size());
        
        this.source = argv.get(0);
        this.type = argv.get(1);
        this.id = argv.get(2);
        
        try{
            this.maxHops = Integer.parseInt(argv.get(3));
        }
        catch(NumberFormatException e) {
            throw new InvalidArgumentException("Exepcted an integer but got \"" + argv.get(3) + "\"");
        }
        
        this.priority = PRIORITY;
        this.previousHop = null;
        this.sourceConnection = null;
        this. hops = new ArrayDeque<>();
    }
    
    @Override
    public void exec() {
        try {
            sourceConnection = new HopConnection(source);
            previousHop = sourceConnection;
            crawl(sourceConnection);
        }
        catch(IOException e) {
            setError(e);
        }
    }
    
    private void crawl(HopConnection url) {
        System.out.println(currentHop + ") Crawling: " + url.getURL());
        try{
            url.connect();
        }
        catch(IOException e) {
            System.out.println("Error connecting to url" + url.getURLNoProtocol());
            setError(e);
            return;
        }
        
        try{
            previousHop = url;
            hops.addAll(parseURLs(url.getResponse()));
            if(currentHop++ < maxHops) crawl(hops.pop());
        }
        catch(IOException e) {
            System.out.println("Error reading response from " + url.getURLNoProtocol());
            setError(e);
            return;
        }
        
        url.disconnect();
    }
    
    /**
     * Invoke a parser on the supplied HTML document to extract all values from 
     * nodes within the body element containing a "href" attribute.
     * 
     * @param html A HTML document.
     * @return The URLs contained within the body of the supplied HTML.
     */
    private Deque<HopConnection> parseURLs(String html) throws IOException{
        Document dom = Jsoup.parse(html);
        Element root = dom.body();
        Elements refs = new Elements();
        
        extractTargets(root.children(), refs);
        
        Deque<HopConnection> URLs = new ArrayDeque<>();  
        
        for(Element ref: refs) {
            String src = ref.attributes().get("href");
            //remove links to elements
            if(src.contains("#")) src = src.substring(0, src.indexOf("#"));
            if(src.isEmpty()) continue;
            
            URLSanitiser sanitiser = new URLSanitiser(src, previousHop);
            
            try {
                src = sanitiser.sanitise();
            }
            catch(IndexOutOfBoundsException e) {
                src = null;
            }
            if(src == null) continue;
            
            HopConnection hop = new HopConnection(src);
            
            //stub : Source needs to be URL of last crawled page
            String url = hop.isAbsolute() ? hop.getURL() : 
                    hop.getURL(sourceConnection.getURLNoProtocol());
            
            if(history.get(url) == null) {
                history.put(url, 1);
                URLs.add(hop);
            }
        }
        
        return URLs;
    }
    
    private Elements extractTargets(Elements nodes, Elements results) {
        for(Element e: nodes) {
            if(e.attributes().get(type).equals(id)) extractLinks(e, results);
            else extractTargets(e.children(), results);
        }
        
        return results;
    }
    
    private void extractLinks(Element node, Elements results) {
        results.addAll(node.getElementsByAttribute("href"));

        for(Element e: node.children()) extractLinks(e, results);
    }
}
