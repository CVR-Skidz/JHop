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
public class Searcher extends Operation{
    public final static String OPNAME = "--search";
    protected String term;
    
    public Searcher(List<String> argv) {
        super(argv, OPNAME);
        this.term = argv.get(0);
    }
    
    @Override
    public void exec() {
        //stub
        System.out.println("Searching for " + term);
    }
}
