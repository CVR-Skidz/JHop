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
public class Viewer extends Searcher{
    public static final String OPNAME = "--show";
    private final static int PRIORITY = 2;
    
    public Viewer(List<String> argv) {
        super(argv);
        this.name = OPNAME;
        this.priority = PRIORITY;
    }
    
    @Override
    public void exec() {
        super.exec();
        //stub
        System.out.println("Description of " + term);
    }
}
