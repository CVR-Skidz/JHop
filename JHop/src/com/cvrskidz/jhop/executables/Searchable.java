package com.cvrskidz.jhop.executables;

/**
 * Provides access to the number of results processed by an Executable instance
 * during an `exec` call.
 * 
 * @author bcc9954 18031335 cvrskidz
 */
public interface Searchable extends Executable {
    /**
     * @return The number of results processed by this instance during it's last
     * execution.
     */
    public int results();
}
