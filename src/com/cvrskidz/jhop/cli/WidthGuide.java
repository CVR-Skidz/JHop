package com.cvrskidz.jhop.cli;

import com.cvrskidz.jhop.parsers.HTMLParser;
import java.util.Scanner;

/**
 * A simple guide to change the width of the displayed text.
 * 
 * @author 18031335 cvrskidz
 */
public class WidthGuide extends Guide{
    public WidthGuide(Scanner input) {
        super(input);
    }
    
    @Override 
    public void poll() {
        String userInput = prompt("Enter new width(columns): ");
        Integer width = parseInt(userInput);
        if(width != null && width >= 0) {
            HTMLParser.WIDTH = width;
        }
    }
    
    @Override
    public void display() {
        System.out.println("New width: " + HTMLParser.WIDTH);
    }
}
