package com.cvrskidz.jhop.executables;

import com.cvrskidz.jhop.exceptions.CommandException;
import com.cvrskidz.jhop.exceptions.PriorityViolationException;
import com.cvrskidz.jhop.indexes.Index;

/**
 * A fixed sequence of operations. A Command executes operations under a set of 
 * constraints. 
 * <p>
 * Operations executed by this object must not share the same priority with any 
 * other operation, if an operation has a special priority (-1) no other operation
 * can be executed in time with it.
 * 
 * @author cvr-skidz bcc9954 18031335
 */
public class Command implements Executable{
    
    private final Operation[] operations;
    private boolean error;
    private CommandException errorException;
    
    public Command(Operation[] operations) {
        error = false;
        this.operations = operations;
    }
    
    public Index safeExec(Index index) throws CommandException{
        index = exec(index);
        
        if(error) throw errorException;
        return index;
    }
    
    @Override
    public Index exec(Index index){
        sort();
        
        CommandException mismatch = validatePriority();
        if(mismatch == null) mismatch = validateSpecialPriority();

        if(mismatch != null) setError(mismatch);
        else {
            for(Operation o: operations) {
                index = o.exec(index);
                if(!o.success()) setError(o);
            }
        }
        
        return index;
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
        int end = operations.length;
        
        for(int start = 0; start < end-1; ++start) {
            int smallest = start;
            for(int current = start + 1; current < end; ++current) {
                int offer = operations[current].getPriority();
                int min = operations[smallest].getPriority();
                if(offer < min) smallest = current;
            }
            
            //swap
            Operation lower = operations[start];
            operations[start] = operations[smallest];
            operations[smallest] = lower;
        }
    }
    
    private CommandException validatePriority() {
        for(int i = 0; i < operations.length; ++i) {
            Operation before = operations[i];
            
            for(int j = i+1; j < operations.length; ++j) {
                Operation after = operations[j];
                int pBefore = before.getPriority();
                int pAfter = after.getPriority();
                
                if(pBefore == pAfter) {
                    String option = before.getName();
                    String violation = after.getName();
                    return new PriorityViolationException(option, violation);
                }
            }
        }
        
        return null;
    }
    
    private CommandException validateSpecialPriority() {
        int master = priorityIndex(Operation.MASTER_PR);
        boolean isLast = master == operations.length - 1;
        
        if(master >= 0 && operations.length > 1) {
            int location = isLast ? master - 1 : master + 1;
            String valid = operations[master].getName();
            String invalid = operations[location].getName();
            return new PriorityViolationException(valid, invalid);
        }
        
        return null;
    } 
    
    private int priorityIndex(int p) {
        for(int i = 0; i < operations.length; ++i) {
            if(operations[i].getPriority() == p) return i;
        }
        
        return -1;
    }
    
    private void setError(Operation o) {
        error = true;
        errorException = o.getError();
    }
    
    private void setError(CommandException e) {
        error = true;
        errorException = e;
    }
}
