package com.cvrskidz.jhop.executables;

import com.cvrskidz.jhop.exceptions.CommandException;
import com.cvrskidz.jhop.exceptions.PriorityViolationException;
import com.cvrskidz.jhop.indexes.Index;
import java.util.List;

/**
 * A fixed sequence of operations. A Command executes operations under a set of 
 * constraints. 
 * <p>
 * Operations executed by this object must not share the same priority with any 
 * other operation, if an operation has a special priority (-1) no other operation
 * can be executed in time with it.
 * 
 * @author cvrskidz 18031335
 */
public class Command implements Executable{
    
    private final Operation[] operations;       // operations to perform
    private boolean error;                      // status of operations
    private CommandException errorException;    // stored error
    
    /**
     * Constructs a new Command object with the given operations to execute.
     * 
     * @param operations The operations this instance will execute.
     */
    public Command(Operation[] operations) {
        error = false;
        this.operations = operations;
    }
    
    /**
     * Constructs a new Command object with the given operations to execute.
     * 
     * @param operations The operations this instance will execute.
     */
    public Command(List<Operation> operations) {
        error = false;
        this.operations = operations.toArray(new Operation[0]); // convert to array
    }

    /**
     * Execute this instances operations, ensuring any exception 
     * is thrown back to the calling method.
     * 
     * @param index The index to perform operations on.
     * @return The same index after performing this instances operations.
     * @throws CommandException If an error occurred performing an operation.
     */
    public Index safeExec(Index index) throws CommandException{
        index = exec(index);
        
        if(error) throw errorException;
        return index;
    }
    
    /**
     * Execute all operations stored in this instance.
     * 
     * @param index The index to perform operations on.
     * @return The same index after performing this instances operations.
     */
    @Override
    public Index exec(Index index){
        sort();
        
        // ensure operations can be executed in order
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
    
    /**
     * Ensure all operations in this instance can be executed in order.
     * 
     * @return False if an operation can not be executed with the other 
     * operations specified in this instance.
     */
    private CommandException validatePriority() {
        for(int i = 0; i < operations.length; ++i) {
            Operation before = operations[i];
            
            // check proceeding priotities
            for(int j = i+1; j < operations.length; ++j) {
                Operation after = operations[j];
                int pBefore = before.getPriority();
                int pAfter = after.getPriority();
                
                // error if both operations have the same priority
                if(pBefore == pAfter) {     
                    String option = before.getName();
                    String violation = after.getName();
                    return new PriorityViolationException(option, violation);
                }
            }
        }
        
        return null;
    }
    
    /**
     * Ensures that if an operation indicates a master priority that no other
     * operations are also supplied. 
     * 
     * @return True if this instances operations can be run in order, false otherwise.
     */
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
    
    /**
     * Returns the first index of an operation of the specified priority.
     * 
     * @param p The priority to check.
     * @return The index of the first matching operation, or -1.
     */
    private int priorityIndex(int p) {
        for(int i = 0; i < operations.length; ++i) {
            if(operations[i].getPriority() == p) return i;
        }
        
        return -1;
    }
    
    /**
     * Sets this instances error to the error specified by the given operation.
     * @param o The operation containing an error.
     */
    private void setError(Operation o) {
        error = true;
        errorException = o.getError();
    }
    
    /**
     * Sets this instances error to the specified exception.
     * @param e The error to set.
     */
    private void setError(CommandException e) {
        error = true;
        errorException = e;
    }
}
