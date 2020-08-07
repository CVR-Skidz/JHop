/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cvrskidz.jhop;

import java.io.BufferedReader;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class HopConnection{
    private URL url;
    private HttpURLConnection connection;
    
    public HopConnection(String src) throws IOException {
        url = new URL(src);
        connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
    }
    
    public void connect() throws IOException {
        connection.connect();
    }
    
    public String getResponse() throws IOException {
        Reader res = new InputStreamReader(connection.getInputStream());
        BufferedReader bufferedRes = new BufferedReader(res);
        
        StringBuilder out = new StringBuilder();
        String lineBuffer  = "";
        
        while((lineBuffer = bufferedRes.readLine()) != null) {
            out.append(lineBuffer);
            out.append('\n');
        }
        
        return out.toString();
    } 
    
    public void disconnect() {
        connection.disconnect();
    }
    
    public boolean isAbsolute() {
        return !url.getHost().isEmpty();
    }
    
    private boolean hasProtocol() {
        return !url.getProtocol().isEmpty();
    }
    
    public String getURL() {
        return getURL(url.getHost());
    }
    
    public String getURLNoProtocol(){
        return url.getHost() + url.getPath();
    }
    
    public String getURL(String host) {
        StringBuilder out = new StringBuilder();
        
        if(hasProtocol()) out.append(url.getProtocol());
        else out.append("http");
        
        out.append("://").append(host).append(url.getPath());
        return out.toString();
    }
    
    public String toString() {
        StringBuilder out = new StringBuilder("Protocol: ");
        out.append(url.getProtocol());
        out.append("\nHost: ").append(url.getHost());
        out.append("\nPath: ").append(url.getPath());
        out.append("\nAbsolute: ").append(isAbsolute());
        return out.toString();
    }
}
