package com.cvrskidz.jhop.executables;

import com.cvrskidz.jhop.indexes.Index;

/**
 * An object that can be executed. A user of this interface specifies the exact
 * functionality of the `exec` method. 
 * <p> Any object executing the instance implementing this interface can 
 * guarantee the behavior is invoked as intended by the class in question.
 * <p> Any object executing the instance implementing this interface can retrieve
 * an error message after calling `exec` without raising an exception.
 * 
 * @author cvrskidz 18031335
 */
public interface Executable {
    /**
     * Execute the intended functionality of this instance.
     * 
     * @param index The index to execute on.
     * @return The same index after executing this instance.
     */
    public Index exec(Index index);
    
    /**
     * Retrieve any errors that occurred whilst executing this instance.
     * @return An Exception if an error occurred, null otherwise.
     */
    public Exception getError();
    
    /**
     * @return True if an error occurred. False otherwise.
     */
    public boolean success();
}
