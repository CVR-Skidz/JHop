package com.cvrskidz.jhop;

import com.cvrskidz.jhop.cli.Guide;
import com.cvrskidz.jhop.cli.JHopMenu;
import com.cvrskidz.jhop.parsers.ArgumentParser;
import com.cvrskidz.jhop.executables.Command;
import com.cvrskidz.jhop.exceptions.CommandException;
import com.cvrskidz.jhop.gui.view.JHopView;
import com.cvrskidz.jhop.indexes.Index;
import java.util.Scanner;

/**
 * JHop is the main entry point for all JHop program instances. It provides the driving 
 * class to perform all executable operations supplied as command line arguments.
 * <p>
 * Alternatively if no arguments are supplied JHop will launch an interactive CLI using
 * Guide objects to allow the user to interact with the engine in an assisted manner.
 * 
 * @author cvrskidz 18031335
 * @see com.cvrskidz.jhop.cli.Guide
 */
public class JHop extends Guide{
    private boolean run;
    private JHopMenu menu;
    
    /**
     * Construct a new JHop Guide with a standard input scanner.
     * @param input A Scanner of the standard input stream
     */
    public JHop(Scanner input) {
        super(input);
        run = true;
        menu = new JHopMenu(this);
        Guide.index = new Index();  // set active index
    }
    
    /** 
     * Runs the interactive CLI loop.
     */
    @Override
    public void poll() {
        displayInfo(); // JHop info
        
        while(run) {
            display();      // print menu and active index
            Integer option; // get user option
            while((option = parseInt(prompt("\nEnter choice: "))) == null) {
                System.out.println("Invalid Input");
            }
            Guide userAction = menu.select(option);
            if(userAction != null) {    // run any valid user option
                System.out.println("");
                userAction.poll();
                userAction.display();
            }
        }
    }
    
    /**
     * Prints this instances JHopMenu and current index.
     */
    @Override
    public void display() {
        String name = Guide.index.getOptions().getName();
        
        System.out.println("");
        System.out.println("Current Index: " + name);
        System.out.println("");
        menu.display();
    }
    
    /**
     * Stop the interactive CLI.
     */
    public void stop() {
        run = false;
    }
    
    /**
     * Displays simple information about the program.
     */
    private void displayInfo() {
        System.out.println("Java Hop Search Engine");
        System.out.println("Version 0.1");
        System.out.println("Author cvrskidz 18031335");
    }
    
    public static void main(String[] args) {
        //CLI (interactive)
        if(args.length == 0) {
//            Interactable application = new JHop(new Scanner(System.in));
//            application.poll();
//            return;
        
            JHopView.loadView();
        }
        
        //CLI (non blocking)
        ArgumentParser parser = new ArgumentParser(args);   // argument parser
        Index index = new Index();                          // empty index
        
        try{
            parser.parse();                                         // parse operations
            Command command = new Command(parser.getOperations());  // create command from arguments
            command.safeExec(index);                                // execute command throwing any exceptions that occur
        }
        catch(CommandException e) {
            System.err.println(e.getMessage());                     // ensure user sees error messages
            System.out.println("Terminating safely...");             
        }
    }
}
