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
    private Exception errorException;
    
    public Command(Operation[] operations) {
        error = false;
        this.operations = operations;
    }
    
    public void safeExec() throws Exception{
        exec();
        
        if(error) throw errorException;
    }
    
    @Override
    public void exec(){
        sort();
        
        CommandMismatchException mismatch = validatePriority();
        if(mismatch != null) {
            setError(mismatch);
            return;
        }
        
        mismatch = validateSpecialPriority();
        if(mismatch != null) {
            setError(mismatch);
            return;
        }
        
        for(Operation o: operations) {
            o.exec();
            if(!o.success()) {
                setError(o);
                return;
            }
        }
    }
    
    @Override
    public Exception getError() {
        return errorException;
    }
    
    @Override
    public boolean success(){
        return !error;
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
    
    private CommandMismatchException validatePriority() {
        for(int i = 0; i < operations.length; ++i) {
            Operation a = operations[i];
            for(int j = i+1; j < operations.length; ++j) {
                Operation b = operations[j];
                if(a.getPriority() == b.getPriority()) {
                    return new CommandMismatchException(a.getName(), b.getName());
                }
            }
        }
        
        return null;
    }
    
    private CommandMismatchException validateSpecialPriority() {
        int indexA = containsPriority(Operation.MASTER_PR);
        if( indexA >= 0 && operations.length > 1) {
            int indexB = indexA == operations.length-1 ? indexA - 1 : indexA + 1;

            String a = operations[indexA].getName();
            String b = operations[indexB].getName();
            return new CommandMismatchException(a, b);
        }
        
        return null;
    } 
    
    private int containsPriority(int p) {
        for(int i = 0; i < operations.length; ++i) {
            if(operations[i].getPriority() == p) return i;
        }
        
        return -1;
    }
    
    private void setError(Operation o) {
        error = true;
        errorException = o.getError();
    }
    
    private void setError(Exception e) {
        error = true;
        errorException = e;
    }
}
