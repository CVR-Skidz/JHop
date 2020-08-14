package com.cvrskidz.jhop;

import com.cvrskidz.jhop.parsers.ArgumentParser;
import com.cvrskidz.jhop.executables.Command;
import com.cvrskidz.jhop.executables.Operation;
import com.cvrskidz.jhop.exceptions.CommandException;

/**
 * @author cvr-skidz bcc9954 18031335
 */
public class JHop {
    public static void main(String[] args) {
        ArgumentParser parser = new ArgumentParser(args);
        try{
            parser.parse();
            Operation[] ops = parser.getOperations().toArray(new Operation[0]);
            Command command = new Command(ops);
            command.safeExec();
        }
        catch(CommandException e) {
            System.err.println(e.getMessage());
            System.out.println("Terminating safely...");
        }
    }
}
