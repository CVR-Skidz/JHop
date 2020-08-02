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
public class InvalidArgumentException extends Exception{
    public InvalidArgumentException() {
        super("Invalid argument \" \" see --help for more details");
    }
    
    public InvalidArgumentException(String argument) {
        super("Invalid argument " + argument + " see --help for more details");
    }
}
