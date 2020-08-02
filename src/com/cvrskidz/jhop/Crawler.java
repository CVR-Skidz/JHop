/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cvrskidz.jhop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author cvr-skidz bcc9954 18031335
 */
public class Crawler extends Operation{
    public static final String OPNAME = "--crawl";
    public static final int PRIORITY = 1;
    private String source;
    
    public Crawler(List<String> argv) {
        super(argv, OPNAME);
        this.source = argv.get(0);
        this.priority = PRIORITY;
    }
    
    @Override
    public void exec() {
        System.out.println("Crawling " + source);
        HttpURLConnection connection = makeConnection();
        String res = "";
        
        if(!error) res = readConnection(connection);
        if(!error) System.out.print(res);
    }
    
    private HttpURLConnection makeConnection() {
        try {
            URL server = new URL(source);
            HttpURLConnection connection = (HttpURLConnection) server.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            
            return connection;
        }
        catch(IOException e) {
            setError(e);
            return null;
        }
    }

    
    private String readConnection(HttpURLConnection c){
        try{
            Reader res = new InputStreamReader(c.getInputStream());
            BufferedReader bufferedRes = new BufferedReader(res);
            StringBuilder out = new StringBuilder();
            String lineBuffer = "";

            while((lineBuffer = bufferedRes.readLine()) != null) {
                out.append(lineBuffer);
                out.append('\n');
            }

            return out.toString();
        }
        catch(IOException e) {
            setError(e);
            return null;
        }
    }
}
