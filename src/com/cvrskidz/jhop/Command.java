/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cvrskidz.jhop;

/**
 *
 * @author cvr-skidz bcc9954 18031335
 */
public class Command implements Executable{
    // TODO: private Set target;
    
    private Operation[] operations;
    private boolean error;
    
    public Command() {
        error = false;
        // TODO: target = null;
    }
    
    @Override
    public void exec() {
        if(operations.length == 0) {
            error = true;
            return;
        }
                   
        for(Operation o: operations) {
            o.exec();
        }
    }
    
    @Override
    public boolean getError() {
        return error;
    }
}
