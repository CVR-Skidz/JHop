package com.cvrskidz.jhop.gui.models;

/**
 * An interface providing the minimum two methods every model component requires
 * to adhere to the observer - observable pattern.
 */
public interface Observable {
    /**
     * Execute and update the state of this instance.
     * 
     * @param args A series of arguments required to update the model.
     */
    public void update(String... args);

    /**
     * Indicate that the observer observing this instance should update to 
     * reflect this instances new state.
     */
    public void alert();
}
