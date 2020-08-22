package com.cvrskidz.jhop.parsers;

/**
 * A Parser to convert its state between formats. The class implementing this 
 * interface has control over how it's state is parsed.
 * <p>
 * The parser needs to be given data before parsing.
 * 
 * @author cvrskidz bcc9954 18031335
 * @param <E> The type of the parsed output.
 */
public interface Parser<E> {
    /**
     * Parse the underlying state of this instance into a specified format 
     * specified by the object implementing this method.
     * 
     * @return A reference to this Parser.
     */
    public Parser<E> parse();
    
    /**
     * @return The parsed output of this instance.
     */
    public E output();
}
