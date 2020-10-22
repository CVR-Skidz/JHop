package com.cvrskidz.jhop.gui.models;

import com.cvrskidz.jhop.db.IndexConnection;
import com.cvrskidz.jhop.gui.view.JHopView;
import com.cvrskidz.jhop.indexes.Index;

/**
 * A model is an interface to the underlying JHOP logic responsible for 
 * executing specific JHop executable(s) in a way that allows the JHopView to 
 * reflect the results in a GUI. 
 * <p>
 * All models culminate in the model as viewed by an MVC architecture. 
 * That is to say the "Model" is made of many Model instances that share a common
 * state. As such the Model class, and not children of it, can be used to execute
 * static methods to manipulate the state safely between all model instances.
 * <p>
 * A Model implements runnable, indicating a Model can run on a separate thread. 
 * However this has a caveat, Model(s) are not synchronized and do not provide locks
 * on how they should control concurrent access as a Model is intended to only be 
 * run one at a time. A Model is used to run parallel to an observer not other models
 * and models should not concurrently access the shared model state. To ensure
 * one does not start multiple models concurrently check the active flag before calling update.
 * 
 * @see com.cvrskidz.jhop.executables.Executable
 */
public abstract class Model implements Observable, Runnable{
    public int ARGC = 1;                     // the default amount of arguments a model needs to update itself
    protected static Index index;           // active index for all guides to operate on
    protected static IndexConnection db;    // active databse connection
    public static JHopView observer;        // view observing models
    protected static boolean active;        // flag to indicate wether a model is running concurrently
    protected Exception error;         // any error during execution
    
    /**
     * Construct a new Model.
     */
    public Model() {
        active = false;
        error = null;
    }
    
    /**
     * @return The active database connection shared between models.
     */
    public static IndexConnection getDBConnection() {
        return db;
    }
    
    /**
     * @return The active index shared between models.
     */
    public static Index getIndex() {
        return index;
    }
    
    /**
     * Connect to the database if not already connected.
     */
    public static void connectDB() {
        if(db == null) db = new IndexConnection();
    }
    
    /**
     * Disconnect to the database if already connected.
     */
    public static void disconnectDB() {
        if(db != null) {
            db.getSession().close();
            db = null;
        }
    }
    
    /**
     * @return If the current model is active or not.
     */
    public static boolean isActive() {
        return active;
    }
    
    /**
     * Toggle the activity flag of all models, and indicate to the models 
     * observer to toggle any progress indicators.
     */
    protected static void toggleActive() {
        Model.observer.toggleProgress();
        active = !active;
    }
    
    /*
     * @return If the model encountered an error during it's last update the error
     * message is returned, otherwise an empty sting is returned.
     */
    public String error() {
        return error == null ? "" : error.getMessage();
    }
}
