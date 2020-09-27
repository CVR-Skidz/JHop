package com.cvrskidz.jhop.indexes;

/**
 * The Updatable interface should be implemented by any class that's functionality
 * relies on it's state being updated frequently, in a consistent manner.
 * <p>
 * This interface provides a singular method that should be used to update the 
 * state of any implementing class over other individual setters.
 * 
 * @author cvrskidz 18031335
 * @param <E> The type used to update the implementing class.
 */
public interface Updatable<E> {
    /**
     * Update this object with the given data.
     * 
     * @param state The data to update this with.
     * @return The new state of this instance.
     */
    public E update(E state);
}
