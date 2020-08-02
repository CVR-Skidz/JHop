/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cvrskidz.jhop;

/**
 * An object that can be executed. A user of this interface specifies the exact
 * functionality of the `exec` method. 
 * <p> Any object executing the instance implementing this interface can 
 * guarantee the behavior is invoked as intended by the class in question.
 * <p> Any object executing the instance implementing this interface can retrieve
 * an error message after calling `exec` without raising an exception.
 * 
 * @author cvr-skidz bcc9954 18031335
 */
public interface Executable {
    /**
     * Execute the intended functionality of this instance.
     */
    public void exec();
    
    /**
     * Retrieve any errors that occurred whilst executing this instance.
     * @return An Exception if an error occurred, null otherwise.
     */
    public Exception getError();
    
    public boolean success();
}
