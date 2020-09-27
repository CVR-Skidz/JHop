package com.cvrskidz.jhop.cli;

/**
 * An interface providing the two methods every
 * interactable CLI component must implement.
 */
public interface Interactable {
    /**
     * Print the component to the standard output stream, and execute any 
     * processes used to provide the components functionality.
     */
    public void display();

    /**
     * Acquire the necessary input from the user and perform any setup.
     */
    public void poll();
}
