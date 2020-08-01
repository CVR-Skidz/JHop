/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cvrskidz.jhop;

import com.cvrskidz.jhop.exceptions.CommandMismatchException;

/**
 * TODO
 * 
 * @author cvr-skidz bcc9954 18031335
 */
public class Command implements Executable{
    // TODO: private Set target;
    
    private Operation[] operations;
    private boolean error;
    private int[] location;
    
    public Command(Operation[] operations) {
        error = false;
        this.operations = operations;
        this.location = new int[2];
    }
    
    public void safeExec() throws CommandMismatchException{
        exec();
        
        if(error) {
            String callA = operations[location[0]].getName();
            String callB = operations[location[1]].getName();
            throw new CommandMismatchException(callA, callB);
        }
    }
    
    @Override
    public void exec(){
        sort();
        
        //check for invalid combinations
        for(int i = 0; i < operations.length; ++i) {
            int prA = operations[i].getPriority();
            for(int j = i+1; j < operations.length; ++j) {
                if(prA == operations[j].getPriority()) {
                    this.location[0] = j;
                    this.location[1] = i;
                    error = true;
                    return;
                }
            }
        }
        
        //check for special commands
        int index = containsPriority(Operation.MASTER_PR);
        if( index >= 0 && operations.length > 1) {
            location[0] = index;
            location[1] = index == operations.length-1 ? index - 1 : index + 1;
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
    
    /**
     * Selection sort of this instances operations member.
     */
    private void sort() {
        int s = operations.length;
        
        for(int i = 0; i < s-1; ++i) {
            int si = i;
            for(int j = i + 1; j < s; ++j) {
                int opPr = operations[j].getPriority();
                int sopPr = operations[si].getPriority();
                if(opPr < sopPr) si = j;
            }
            
            //swap
            Operation lowPr = operations[i];
            operations[i] = operations[si];
            operations[si] = lowPr;
        }
    }
    
    private int containsPriority(int p) {
        for(int i = 0; i < operations.length; ++i) {
            if(operations[i].getPriority() == p) return i;
        }
        
        return -1;
    }
}
