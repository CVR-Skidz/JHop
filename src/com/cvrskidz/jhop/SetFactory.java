/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cvrskidz.jhop;
import java.util.List;

/**
 * An executable object to provide a JHop program set functions such as  
 * reading, writing, and encrypting. 
 * <p> A SetFactory does not create a Set unless 
 * the "--create" operation is supplied.
 * 
 * @author cvrskidz bcc9954 18031335
 * @see com.cvrskidz.jhop.Operation
 */
public class SetFactory extends Operation{
    public static final String CREATESET = "--create";
    public static final String DROPSET = "--drop";
    public static final String SPECIFY = "--set";
    public static final String ENCRYPT = "--encrypt";
    
    private String setName;
    private Runnable operation;
    
    /** Constructs a SetFactory with the supplied arguments and name.
     * 
     * @param argv Operation arguments.
     * @param call Operation name.
     * @see com.cvrskidz.jhop.Operation 
     */
    public SetFactory(List<String> argv, String call) {
        super(argv, call);
        this.setName = argv.get(0);
        operation = setOperation();
    }
    
    /**
     * Returns a method reference to the correct set operation as specified by
     * this instances name. Such as "--create", "--set", "--encrypt", and "--drop".
     * 
     * @return A method reference to the correct set operation or null if this 
     * object contains an unknown operation.
     */
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
    
    /**
     * STUB: Creates a Set.
     */
    private void createOperation() {
        //stub
        System.out.println("Creating set " + setName);
    }
    
    /**
     * STUB: Deletes a Set from disk.
     */
    private void dropOperation() {
        //stub
        System.out.println("Dropping set " + setName);
    }
    
    /**
     * STUB: Reads a Set stored on disk.
     */
    private void specifyOperation() {
        //stub
        System.out.println("Using set " + setName);
    }
    
    /**
     * STUB: Encrypts a Set stored on disk.
     */
    private void encryptOperation() {
        //stub
        System.out.println("Encrypting set " + setName);
    }
}
