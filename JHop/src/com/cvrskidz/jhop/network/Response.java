package com.cvrskidz.jhop.network;

/**
 * A wrapper object that maps a response to a HopConnection that provided this
 * response. A response contains the body of a HTTP/HTTPS response as a String.
 * 
 * @author bcc9954 18031335 cvr-skidz
 */
public class Response {
    private String contents;
    private final HopConnection url;
    
    public Response(HopConnection url, String contents) {
        this.url = url;
        this.contents = contents;
    }

    /**
     * @return The contents stored in this response.
     */
    public String getContents() {
        return contents;
    }

    /**
     * @return The HopConnection stored in this response.
     */
    public HopConnection getUrl() {
        return url;
    }

    /**
     * Replace the contents currently stored in this response.
     * 
     * @param contents The new contents.
     */
    public void setContents(String contents) {
        this.contents = contents;
    }
}
