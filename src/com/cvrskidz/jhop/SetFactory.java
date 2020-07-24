/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cvrskidz.jhop;
import java.util.List;

/**
 *
 * @author callu
 */
public class SetFactory extends Operation{
    public static final String CREATESET = "--create";
    public static final String DROPSET = "--drop";
    public static final String SPECIFY = "--set";
    public static final String ENCRYPT = "--encrypt";
    
    private String setName;
    private Runnable operation;
    
    public SetFactory(List<String> argv, String call) {
        super(argv, call);
        this.setName = argv.get(0);
        operation = setOperation();
    }
    
    private Runnable setOperation() {
        switch(name) {
            case CREATESET:
                return this::createOperation;
            case DROPSET:
                return this::dropOperation;
            case SPECIFY:
                return this::specifyOperation;
            case ENCRYPT:
                return this::encryptOperation;
            default:
                return null;
        }
    }
    
    @Override
    public void exec() {
        operation.run();
    }
    
    private void createOperation() {
        //stub
        System.out.println("Creating set " + setName);
    }
    
    private void dropOperation() {
        //stub
        System.out.println("Dropping set " + setName);
    }
    
    private void specifyOperation() {
        //stub
        System.out.println("Using set " + setName);
    }
    
    private void encryptOperation() {
        //stub
        System.out.println("Encrypting set " + setName);
    }
}
