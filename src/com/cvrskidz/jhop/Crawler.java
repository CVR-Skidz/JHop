/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cvrskidz.jhop;
import java.util.List;

/**
 *
 * @author cvr-skidz bcc9954 18031335
 */
public class Crawler extends Operation{
    public static final String OPNAME = "--crawl";
    private String source;
    
    public Crawler(List<String> argv) {
        super(argv, OPNAME);
        source = argv.get(0);
    }
    
    @Override
    public void exec() {
        //stub
        System.out.println("Crawling " + source);
    }
}
