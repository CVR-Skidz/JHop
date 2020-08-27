package com.cvrskidz.jhop.network;

import java.io.BufferedReader;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Objects;

/**
 * A wrapper around @link{java.net.HttpURLConnection} taht provides convenient methods to request responses and 
 * format the destination URL of a request.
 *
 * @author bcc9954 18031335 cvr-skidz 
 */
public class HopConnection{
    private final URL url;                      // URL of request
    private final HttpURLConnection connection; // Connection to host
    
    public HopConnection(String src) throws IOException {
        url = new URL(src);
        connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
    }
    
    /**
     * Read the response from a connected host.
     *
     * @return A response object containing the response of this HTTP request.
     * @throws IOException Upon receiving an error reading the input stream of a connection.
     */
    public Response getResponse() throws IOException {
        Reader res = new InputStreamReader(connection.getInputStream());
        BufferedReader bufferedRes = new BufferedReader(res);
        StringBuilder out = new StringBuilder();
        String lineBuffer;
        
        while((lineBuffer = bufferedRes.readLine()) != null) {
            out.append(lineBuffer).append('\n');
        }
        
        return new Response(this, out.toString());
    } 
    
    /** 
     * Disconnect this isntance from any host it is currently connected to.
     */
    public void disconnect() {
        connection.disconnect();
    }
    
    /**
     * @return True if the request is set to an absolute URL, false otherwise.
     */
    public boolean isAbsolute() {
        return !url.getHost().isEmpty();
    }
    
    /**
     * @return True if the request specifies a protocol, false otherwise.
     */
    private boolean hasProtocol() {
        return !url.getProtocol().isEmpty();
    }
    
    /**
     * @return The URL specified by this connection.
     */
    public String getURL() {
        return getURL(url.getHost());
    }
    
    /**
     * @return The URL specified by this connection without a protocol.
     */
    public String getURLNoProtocol(){
        return url.getHost() + url.getPath();
    }
    
    /**
     * @param host Tho host to append the URL specified by this connection to.
     * @return The URL specified by this connection appended to the given host name.
     */
    public String getURL(String host) {
        StringBuilder out = new StringBuilder();
        
        if(hasProtocol()) out.append(url.getProtocol());
        else out.append("http");
        
        out.append("://").append(host).append(url.getPath());
        return out.toString();
    }
    
    /**
     * @return The host specified by the URL of this connection.
     */
    public String getHost() {
        return url.getHost();
    }
    
    /**
     * @return The protocol specified by the URL of this connection.
     */
    public String getProtocol() {
        return url.getProtocol();
    }
    
    /** 
     * Connect this isntance to the host specified by the URL in this connection.
     * @throws IOException If there was an error sending the request to the desitantion.
     */
    public void connect() throws IOException {
        connection.connect();
    }
    
    @Override
    public boolean equals(Object o) {
        if(o instanceof HopConnection) {
            HopConnection other = (HopConnection)o;
            return other.getURL().equals(getURL());
        }
        
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.url);
        return hash;
    }
    
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("Protocol: ");
        out.append(url.getProtocol());
        out.append("\nHost: ").append(url.getHost());
        out.append("\nPath: ").append(url.getPath());
        out.append("\nAbsolute: ").append(isAbsolute());
        return out.toString();
    }
}
