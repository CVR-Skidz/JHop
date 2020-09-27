package com.cvrskidz.jhop.cli;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Scanner;

/**
 * A guide displaying the JHop help manual. Polling this guide opens the help 
 * manual without displaying it.
 * 
 * @author cvrskidz 18031335
 */
public class HelpGuide extends Guide{
    private final static String PATH = "help"; 
    private BufferedReader file;
    
    
    /**
     * Construct a new HelpGuide with the given input stream.
     * @param input The input scanner to read user input from.
     */
    public HelpGuide(Scanner input) {
        super(input);
    }
    
    /**
     * Open JHops help manual.
     */
    @Override
    public void poll() {
        try {
            Reader nonBuffered = new FileReader(HelpGuide.PATH);
            file = new BufferedReader(nonBuffered);
        }
        catch (IOException e) {
            System.out.println(HelpGuide.PATH + " was not found");
        }
    }
    
    /**
     * Display the contents of JHops help manual.
     */
    @Override 
    public void display() {
        if(file == null) return;
        
        try {
            String lineBuffer;
            while((lineBuffer = file.readLine()) != null) {
                System.out.println(lineBuffer);
            }
        }
        catch (IOException e) {
            System.out.println("Error reading file: " + HelpGuide.PATH);
        }
    }
}
