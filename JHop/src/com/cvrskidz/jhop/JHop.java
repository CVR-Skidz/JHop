package com.cvrskidz.jhop;

import com.cvrskidz.jhop.cli.Guide;
import com.cvrskidz.jhop.cli.Interactable;
import com.cvrskidz.jhop.cli.JHopMenu;
import com.cvrskidz.jhop.parsers.ArgumentParser;
import com.cvrskidz.jhop.executables.Command;
import com.cvrskidz.jhop.executables.Operation;
import com.cvrskidz.jhop.exceptions.CommandException;
import com.cvrskidz.jhop.indexes.Index;
import java.util.Scanner;

/**
 * @author cvr-skidz bcc9954 18031335
 */
public class JHop extends Guide{
    private boolean run;
    private Index index;
    private JHopMenu menu;
    
    public JHop(Scanner input) {
        super(input);
        run = true;
        index = new Index();
        menu = new JHopMenu();
    }
    
    @Override
    public void poll() {
        while(run) {
            display();
            Integer option;
            while((option = parseInt(prompt("> "))) == null) {
                System.out.println("Invalid Input");
            }
            menu.select(option, in);
        }
    }
    
    @Override
    public void display() {
        System.out.println("=== Welcome to JHop ===\n");
        menu.display();
        System.out.println("\nIndex: " + index.getOptions().getName());
    }
    
    public static void main(String[] args) {
        //CLI (interactive)
        if(args.length == 0) {
            Interactable application = new JHop(new Scanner(System.in));
            application.poll();
            return;
        }
        
        //CLI (non blocking)
        ArgumentParser parser = new ArgumentParser(args);
        Index index = new Index();
        
        try{
            parser.parse();
            Operation[] ops = parser.getOperations().toArray(new Operation[0]);
            Command command = new Command(ops);
            command.safeExec(index);
        }
        catch(CommandException e) {
            System.err.println(e.getMessage());
            System.out.println("Terminating safely...");
        }
    }
}
