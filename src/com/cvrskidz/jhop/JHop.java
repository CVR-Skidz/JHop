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
        parser.parse();
        for(Operation op: parser.getOperations()) op.exec();
    }
}
