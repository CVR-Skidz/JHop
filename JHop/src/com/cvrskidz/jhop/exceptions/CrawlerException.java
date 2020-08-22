package com.cvrskidz.jhop.exceptions;

import com.cvrskidz.jhop.executables.Crawler;

public class CrawlerException extends CommandException{
    private Crawler op; 
    
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
