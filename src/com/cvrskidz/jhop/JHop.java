/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cvrskidz.jhop;

import com.cvrskidz.jhop.exceptions.CommandMismatchException;
import com.cvrskidz.jhop.exceptions.CommandNotFoundException;
import com.cvrskidz.jhop.exceptions.InvalidArgumentException;

/**
 *
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
        catch(Exception e) {
            System.err.println("An error occured:");
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }
}
