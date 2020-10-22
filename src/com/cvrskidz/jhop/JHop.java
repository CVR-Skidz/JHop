package com.cvrskidz.jhop;

import com.cvrskidz.jhop.gui.models.Model;
import com.cvrskidz.jhop.parsers.ArgumentParser;
import com.cvrskidz.jhop.executables.Command;
import com.cvrskidz.jhop.exceptions.CommandException;
import com.cvrskidz.jhop.gui.controllers.JHopButtonController;
import com.cvrskidz.jhop.gui.controllers.JHopListController;
import com.cvrskidz.jhop.gui.controllers.Controller;
import com.cvrskidz.jhop.gui.view.JHopView;
import com.cvrskidz.jhop.indexes.Index;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

/**
 * JHop is the main entry point for all JHop program instances. It provides the driving 
 * class to perform all executable operations supplied as command line arguments.
 * <p>
 * Alternatively if no arguments are supplied JHop will launch an interactive GUI.
 * 
 * @author cvrskidz 18031335
 */
public class JHop{
    private static JFrame window;
    
    public JHop() {
        //Initiate model by connecting to database
        Model.connectDB();
        
        //Initiate view
        JHopView view = new JHopView();
        Model.observer = view;
        Controller btnController = new JHopButtonController(view);
        Controller lstController = new JHopListController(view);
        btnController.link();
        lstController.link();
        
        //Display view
        window = new JFrame();
        window.getContentPane().add(view);
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Model.disconnectDB();
                System.exit(0);
            }
        });
        
        window.setSize(JHopView.DEFAULT_WIDTH, JHopView.DEFAULT_HEIGHT);
        window.setVisible(true);
    }
    
    public static JFrame getActiveWindow() {
        return window;
    }
    
    public static void main(String[] args) {
        //GUI
        if(args.length == 0) new JHop();
        
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
