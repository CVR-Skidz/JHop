/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cvrskidz.jhop;

import java.util.Iterator;
import java.util.Stack;
import java.util.StringTokenizer;

public final class URLSanitiser {
    private static final String PROTOCOL = "http://";
    private static final String PROTOCOL_SAFE = "https://";

    private String url;
    private HopConnection src;
    
    public URLSanitiser(String url, HopConnection src) {
        this.url = url;
        this.src = src;
    }
        
    public String sanitise() throws IndexOutOfBoundsException{
        String protocol = getProtocol(url);
        String path = "", sanitizedURL = ""; 
        boolean host = hasHost(url, protocol);
        
        path = protocol.isEmpty() ? url : url.substring(protocol.length());

        //absolute paths
        if(host) { 
            if(!getHost(url, protocol).equals(src.getHost())) return null;
            path = descend(path);
        }
        else {
            //relative paths
            path = path.startsWith("/") ? src.getHost() + "/" + path : absoluteURL();
            path = descend(path);
        }
        
        sanitizedURL = protocol.isEmpty() ? PROTOCOL_SAFE + path : protocol + path;
        return sanitizedURL;
    }
    
    private String absoluteURL() {
        String prefix = src.getURLNoProtocol();
        prefix = prefix.substring(0, prefix.lastIndexOf('/'));
        return prefix + '/' + url;
    }
    
    private String getProtocol(String url) {
        if(url.startsWith(PROTOCOL)) return PROTOCOL; 
        else if(url.startsWith(PROTOCOL_SAFE)) return PROTOCOL_SAFE;
        
        return "";
    }
    
    private boolean hasHost(String url, String protocol) {
        String host = getHost(url, protocol);
        return host.matches(".+\\..+") && !host.endsWith(".html");
    }
    
    private int hostEnd(String url, String protocol) {
        return url.indexOf('/', protocol.length());
    }
    
    private String getHost(String url, String protocol) {
        int end = hostEnd(url, protocol);
        
        if(end >= 0) return url.substring(protocol.length(), end);
        else return url.substring(protocol.length());
    }
    
    private String descend(String url) {
        StringTokenizer tokens = new StringTokenizer(url, "/");
        Stack<String> pages = new Stack();
        
        while(tokens.hasMoreTokens()) {
            String token = tokens.nextToken();
            if(token.equals("..") && !pages.empty()) pages.pop();
            else pages.push(token);
        }
        
        StringBuilder out = new StringBuilder();
        Iterator<String> it = pages.iterator();
        while(it.hasNext()) {
            out.append(it.next());
            if(it.hasNext()) out.append('/');
        }
        return out.toString();
    }
}
