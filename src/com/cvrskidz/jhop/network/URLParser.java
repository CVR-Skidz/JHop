package com.cvrskidz.jhop.network;

import com.cvrskidz.jhop.parsers.Parser;
import java.io.IOException;
import java.util.Iterator;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * A URLParser parses the segments of a URL (protocol, domain, path) and 
 * ensures that in the event any required information is missing this information
 * is correctly added to the original URL so that it can be successfully used to 
 * open a HTTP/HTTPS connection.
 * 
 * @see com.cvrskidz.jhop.network.HopConnection
 * @author cvrskidz 18031335
 */
public class URLParser implements Parser<HopConnection>{
    private static final String PROTOCOL = "http://", 
            PROTOCOL_SAFE = "https://",
            ENDPOINT = ".html",
            HOST_EXP = ".+\\..+",                       // host pattern
            DELIM = "/",                                // delimeter used to seperate paths
            EXCLUDED_DELIM = "#";                       // delimeter excluded from paths

    private final String url;
    private final HopConnection src;                    // valid connection used to correct incomplete URLs
    private HopConnection out;                          // returned connection
    
    /**
     * Constructs a new URLParser with the given URl to parse, and a valid source
     * connection. 
     * 
     * @param url The URL to parse
     * @param src The source connection from which the URL was obtained from.
     */
    public URLParser(String url, HopConnection src) {
        this.url = url;
        this.src = src;
    }
    
    @Override
    public Parser<HopConnection> parse() {
        try {
            out = new HopConnection(parseURL());
        } catch(IndexOutOfBoundsException | IOException e) {
            out = null;
        }
        
        return this;
    }
    
    @Override
    public HopConnection output() {
        return out;
    }
    
    /**
     * Create a valid URL from the URL loaded into this object.
     *
     * @return A valid URL.
     */
    private String parseURL() {
        StringBuilder res = new StringBuilder(); 
        String protocol = getProtocol(url);

        // create a substring if the protocol is included
        String path = protocol.isEmpty() ? url : url.substring(protocol.length());  
        String host = getHost(url, protocol);
        boolean hasHost = validHost(host);
        
        // return no URL if the given URL points to an external host
        if(hasHost && !host.equals(src.getHost())) return null; 
        // if the URL is relative make an absolute path 
        if(!hasHost) path = absoluteOf(url);
        
        path = descend(path);                                           // shorten path if possible
        if(path.contains(EXCLUDED_DELIM)) {
            path = path.substring(0, path.indexOf(EXCLUDED_DELIM));     // remove element IDs from path
        }
        
        //append the path to protocol
        res.append(protocol.isEmpty() ? PROTOCOL_SAFE : protocol).append(path); 
        return res.toString();
    }
    
    /**
     * Validate that the supplied host contains a domain and does not specify an endpoint
     * such as a file.
     *
     * @param host the host to validate
     * @return true if the host is valid, false otherwise.
     */
    private boolean validHost(String host) {
        return host.matches(HOST_EXP) && !host.endsWith(ENDPOINT);
    }
    
    /**
     * Convert a relative path to an absolute path, adding a host specified by the connection 
     * loaded in this instance and ensuring the supplied path can be navogated to from the root
     * of this connection.
     *
     * @param url The path to turn absolute.
     * @return The absolute version of the given path.
     */
    private String absoluteOf(String url) {
        StringBuilder out = new StringBuilder();

        if(url.startsWith(DELIM)) {
            out.append(src.getHost()).append(DELIM).append(url);
        }
        else {
            String cwd = src.getURLNoProtocol();            // the path to follow from the hosts root
            cwd = cwd.substring(0, cwd.lastIndexOf(DELIM)); // find the path to the requested resource
            out.append(cwd).append(DELIM).append(url);      // append the relative path to the absolute root path
        }
    
        return out.toString();
    }
    
    /**
     * Get the protocol specified by the supplied URL.
     *
     * @param url The URL to extract the protocol from
     * @return The protocol specified in the given URL, or an empty string.
     */
    private String getProtocol(String url) {
        if(url.startsWith(PROTOCOL)) {
            return PROTOCOL;
        } 
        else if(url.startsWith(PROTOCOL_SAFE)) {
            return PROTOCOL_SAFE;
        }
        
        return "";
    }
    
    /**
     * Get the host specified by the supplied URL.
     *
     * @param url The URL to extract the host from.
     * @param protocol The protocol contained within the given URL. This should be empty if no protocol is present.
     * @return The host contained in the supplied URL.
     * @see  URLParser#getHost(String, String)
     */
    private String getHost(String url, String protocol) {
        int end = url.indexOf(DELIM, protocol.length());
        
        if(end >= 0) return url.substring(protocol.length(), end);
        else return url.substring(protocol.length());
    }
    
    /**
     * Shorten the given URL. Paths including ".." will traverse the given path to the containing route.
     *
     * @param url the URL to shorten
     * @return The shortened version of the given URL
     */
    private String descend(String url) {
        StringTokenizer tokens = new StringTokenizer(url, DELIM);
        Stack<String> pages = new Stack();
        
        while(tokens.hasMoreTokens()) {
            String token = tokens.nextToken();
            if(token.equals("..") && !pages.empty()) pages.pop();  
            else pages.push(token);
        }
        
        //rebuild url following path
        StringBuilder out = new StringBuilder();
        Iterator<String> it = pages.iterator();
        while(it.hasNext()) {
            out.append(it.next());
            if(it.hasNext()) out.append(DELIM);
        }
        return out.toString();
    }
}
