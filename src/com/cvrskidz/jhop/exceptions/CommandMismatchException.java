/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cvrskidz.jhop.exceptions;

/**
 *
 * @author callu
 */
public class CommandMismatchException extends Exception{
    private String a, b;

    public CommandMismatchException(String nameOfA, String nameOfB) {
        super(nameOfB + " can not be used in conjunction with " + nameOfA + 
                " see --help for details");
        this.a = nameOfA;                
        this.b = nameOfB;                
    }
}
