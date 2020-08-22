package com.cvrskidz.jhop.network;

public class Response {
    private String contents;
    private final HopConnection url;
    
    public Response(HopConnection url, String contents) {
        this.url = url;
        this.contents = contents;
    }

    public String getContents() {
        return contents;
    }

    public HopConnection getUrl() {
        return url;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
