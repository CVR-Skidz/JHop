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
    private JHopMenu menu;
    
    public JHop(Scanner input) {
        super(input);
        run = true;
        menu = new JHopMenu(this);
        Guide.index = new Index();
    }
    
    @Override
    public void poll() {
        displayInfo();
        
        while(run) {
            display();
            Integer option;
            while((option = parseInt(prompt("\nEnter choice: "))) == null) {
                System.out.println("Invalid Input");
            }
            Guide userAction = menu.select(option);
            if(userAction != null) {
                System.out.println("");
                userAction.poll();
                userAction.display();
            }
        }
    }
    
    @Override
    public void display() {
        String name = Guide.index.getOptions().getName();
        
        System.out.println("");
        System.out.println("Current Index: " + name);
        System.out.println("");
        menu.display();
    }
    
    public void stop() {
        run = false;
    }
    
    private void displayInfo() {
        System.out.println("Java Hop Seacrh Engine");
        System.out.println("Version 0.1");
        System.out.println("Author bcc9954, 18031335 @ cvr-skidz.github.io");
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
