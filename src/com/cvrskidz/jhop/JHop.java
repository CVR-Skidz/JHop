/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cvrskidz.jhop;

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
            System.err.println("**ERROR** " + e.getMessage());
//            e.printStackTrace();
        }
    }
}
