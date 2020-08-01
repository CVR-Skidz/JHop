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
    
    private static final int CREATE_PR = 2;
    private static final int DROP_PR = Operation.MASTER_PR;
    private static final int SPECIFY_PR = 1;
    private static final int ENCRYPT_PR = Operation.MASTER_PR;
    
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
        this.operation = setOperation();
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
                this.priority = CREATE_PR;
                return this::createOperation;
            case DROPSET:
                this.priority = DROP_PR;
                return this::dropOperation;
            case SPECIFY:
                this.priority = SPECIFY_PR;
                return this::specifyOperation;
            case ENCRYPT:
                this.priority = ENCRYPT_PR;
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
