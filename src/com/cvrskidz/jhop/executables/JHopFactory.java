package com.cvrskidz.jhop.executables;

import com.cvrskidz.jhop.exceptions.CommandException;

/**
 * An Interface marking the implementing class produces an Operation.
 * An implementing class is required to be given all relevant information prior
 * to producing a new Operation.
 * 
 * @author cvrskidz 18031335
 * @param <E> The type of Operation to be produced.
 */
public interface JHopFactory<E extends Operation> {
    /**
     * Produce an Executable from this JHopFactory.
     * 
     * @return A new Executable.
     * @throws CommandException If there was an error producing the operation
     */
    public E produce() throws CommandException;
}
