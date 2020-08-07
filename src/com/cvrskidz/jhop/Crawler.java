package com.cvrskidz.jhop;
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
    public static final int MAX_HOPS = 10; 
    private static Map<String, Integer> history = new HashMap<String, Integer>();
    
    public static final String OPNAME = "--crawl";
    public static final int PRIORITY = 1;
    private String source;
    private HopConnection sourceConnection, previousHop;
    private int hops;
    
    public Crawler(List<String> argv) {
        super(argv, OPNAME);
        this.source = argv.get(0);
        this.priority = PRIORITY;
        this.previousHop = null;
        this.sourceConnection = null;
        hops = 0;
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
        System.out.println(hops + ") Crawling: " + url.getURL());
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
            Deque<HopConnection> refs = parseURLs(url.getResponse());
            for(HopConnection hop: refs) {
                if(hops++ < MAX_HOPS) crawl(hop);
                else break;
            }
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
        Elements refs = root.getElementsByAttribute("href");
        
        Deque<HopConnection> URLs = new ArrayDeque<>();  
        
        for(Element ref: refs) {
            String src = ref.attributes().get("href");
            //remove links to elements
            if(src.contains("#")) src = src.substring(0, src.indexOf("#"));
            if(src.isEmpty()) continue;
            
            System.out.println("Befor shortening: " + src);
            URLSanitiser sanitiser = new URLSanitiser(src, previousHop);
            src = sanitiser.sanitise();
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
}
