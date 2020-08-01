/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cvrskidz.jhop.exceptions;

import java.util.List;

/**
 *
 * @author callu
 */
public class CommandNotFoundException extends Exception{
    private String name;
    private List<String> argv;
    
    public CommandNotFoundException(String name, List<String> argv) {
        super("Unknwon Command: " + name + " see --help for details");
        this.name = name;
        this.argv = argv;
    }
}
