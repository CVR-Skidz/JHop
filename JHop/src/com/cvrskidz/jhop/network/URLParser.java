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
 * @author bcc9954 18031335 cvrskidz
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
    
    private String parseURL() {
        StringBuilder res = new StringBuilder(); 
        String protocol = getProtocol(url);
        String path = protocol.isEmpty() ? url : url.substring(protocol.length());
        String host = getHost(url, protocol);
        boolean hasHost = validHost(host);
        
        if(hasHost && !host.equals(src.getHost())) return null;
        else if(!hasHost) path = absoluteOf(url);
        
        path = descend(path);
        if(path.contains(EXCLUDED_DELIM)) {
            path = path.substring(0, path.indexOf(EXCLUDED_DELIM));
        }
        
        res.append(protocol.isEmpty() ? PROTOCOL_SAFE : protocol).append(path);
        return res.toString();
    }
    
    private boolean validHost(String host) {
        return host.matches(HOST_EXP) && !host.endsWith(ENDPOINT);
    }
    
    private String absoluteOf(String url) {
        StringBuilder out = new StringBuilder();

        if(url.startsWith(DELIM)) {
            out.append(src.getHost()).append(DELIM).append(url);
        }
        else {
            String cwd = src.getURLNoProtocol();
            cwd = cwd.substring(0, cwd.lastIndexOf(DELIM));
            out.append(cwd).append(DELIM).append(url);
        }
    
        return out.toString();
    }
    
    private String getProtocol(String url) {
        if(url.startsWith(PROTOCOL)) {
            return PROTOCOL;
        } 
        else if(url.startsWith(PROTOCOL_SAFE)) {
            return PROTOCOL_SAFE;
        }
        
        return "";
    }
    
    private String getHost(String url, String protocol) {
        int end = url.indexOf(DELIM, protocol.length());
        
        if(end >= 0) return url.substring(protocol.length(), end);
        else return url.substring(protocol.length());
    }
    
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
