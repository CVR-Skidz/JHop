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
public abstract class Operation implements Executable{
    private int argc;
    private List<String> argv;
    private boolean error;
    protected String name;
    
    public Operation() {
        argv = null;
        error = false;
        name = null;
    }
    
    public Operation(List<String> argv, String name) {
        super();
        this.argv = argv;
        this.argc = argv.size();
        this.name = name;
    }
    
    public static Operation OpFactory(String call, List<String> argv) {
        switch(call) {
            case Searcher.OPNAME:
                return new Searcher(argv); 
            case Viewer.OPNAME:
                return new Viewer(argv);
            case Crawler.OPNAME:
                return new Crawler(argv);
            case SetFactory.CREATESET:
            case SetFactory.DROPSET:
            case SetFactory.SPECIFY:
            case SetFactory.ENCRYPT:
                return new SetFactory(argv, call);
            default:
                return null;
        }
    }
    
    @Override
    public boolean getError(){
        return error;
    }
    
    public int getArgc() {
        return argc;
    }

    public List<String> getArgv() {
        return argv;
    }

    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("Operation: ");
        out.append(name);
        out.append("\n\tOptions:");
        out.append(argv);
        
        return out.toString();
    }
}
