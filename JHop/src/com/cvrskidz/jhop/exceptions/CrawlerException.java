package com.cvrskidz.jhop.exceptions;

import com.cvrskidz.jhop.executables.Crawler;

/**
 * Exception caused by an error preventing a Crawler from crawling additional URLs or guranteeing the integrity of 
 * already cralwed URLs.
 * 
 * @see com.cvrskidz.jhop.executables.Crawler 
 * @author bcc9954 18031335 cvr-skidz 
 */
public class CrawlerException extends CommandException{
    private Crawler op; // Crawler operation where the error occurred.
    
    /** 
     * Constructs a new CrawlerException specifying a given error message and storing the state of the Crawler
     * where the error occurred.
     *
     * @param op The crawler where the error occurred.
     * @param message The error message to supply to a CommandException.
     * @see com.cvrskidz.jhop.exceptions.CommandException
     */
    public CrawlerException(Crawler op, String message) {
        super(message, op.getName());
        this.op = op;
    }
    
    @Override
    public String getMessage() {
        StringBuilder out = new StringBuilder(super.getMessage());
        
        //Report URL
        out.append("\nError crawling ").append(op.getLastHop().getURLNoProtocol());
        //Report query
        out.append(" ").append(op.getType()).append(": ").append(op.getId());
        //Report hopNumber
        out.append("\nFailed on hop: ").append(op.getHop());
        
        return out.toString();
    }
}
