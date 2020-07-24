/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cvrskidz.jhop;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cvr-skidz bcc9954 18031335
 */
public class ArgumentParser {
    private List<Operation> operations;
    private final String commandLine;
    private String[] arguments;
    
    public ArgumentParser() {
        commandLine = null;
        operations = null;
    }
    
    public ArgumentParser(String[] argv) {
        arguments = argv;
        commandLine = stringify(arguments);
    }
    
    public void parse() {
        operations = new ArrayList<>(); 
        List<String> argv = new ArrayList<>();
        String name = null;
        
        for(String s: arguments) {
            if(isOperation(s)) {
                if(name == null) name = s;
                else {
                    List<String> arg_copy = new ArrayList<>(argv);
                    operations.add(Operation.OpFactory(name, arg_copy));
                    name = s;
                    argv.clear();
                }
            }
            else argv.add(s);
        }
        
        if (name != null) operations.add(Operation.OpFactory(name, argv));
    }
    
    private boolean isOperation(String s) {
        return s.matches("--[A-z]+");
    }
    
    public List<Operation> getOperations() {
        return operations;
    }

    public String getCommandLine() {
        return commandLine;
    }
    
    public static String stringify(String[] a) {
        StringBuilder out = new StringBuilder();
        int end = a.length;
        
        for(int i = 0; i < end; ++i) {
            out.append(a[i]);
            if(i != end - 1) out.append(" ");
        }
        
        return out.toString();
    }
}
