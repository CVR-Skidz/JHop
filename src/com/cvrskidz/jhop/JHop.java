package com.cvrskidz.jhop;

import com.cvrskidz.jhop.parsers.ArgumentParser;
import com.cvrskidz.jhop.executables.Command;
import com.cvrskidz.jhop.executables.Operation;
import com.cvrskidz.jhop.exceptions.CommandException;
import com.cvrskidz.jhop.indexes.Index;

/**
 * @author cvr-skidz bcc9954 18031335
 */
public class JHop {
    public static void main(String[] args) {
        ArgumentParser parser = new ArgumentParser(args);
        Index index = new Index();
        
        try{
            parser.parse();
            Operation[] ops = parser.getOperations().toArray(new Operation[0]);
            Command command = new Command(ops);
            index = command.safeExec(index);
        }
        catch(CommandException e) {
            System.err.println(e.getMessage());
            System.out.println("Terminating safely...");
        }
    }
}
