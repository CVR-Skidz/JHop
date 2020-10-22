package com.cvrskidz.jhop.tests;

import com.cvrskidz.jhop.db.indexutil.DatabaseHelper;
import com.cvrskidz.jhop.db.indexutil.DatabaseIndexDropper;
import com.cvrskidz.jhop.db.indexutil.DatabaseIndexReader;
import com.cvrskidz.jhop.db.indexutil.DatabaseIndexWriter;
import com.cvrskidz.jhop.exceptions.CommandException;
import com.cvrskidz.jhop.executables.Crawler;
import com.cvrskidz.jhop.executables.Executable;
import com.cvrskidz.jhop.gui.models.Model;
import com.cvrskidz.jhop.indexes.Index;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 * JHopTest contains a series of unit tests to test JHop against. All modifications
 * made to persistent data storage is reversed after completion of these tests.
 * 
 * @author cvrskidz 18031335
 */
public class JHopTest {
    //Dummy index
    private final static String DUMMY_NAME = "Dummy";
    private final static String DUMMY_URL = "https://docs.oracle.com/javase/8/docs/api/java/math/BigDecimal.html";
    private final static String DUMMY_ATT = "class";
    private final static String DUMMY_VAL = "block";
    private final static String DUMMY_LIMIT = "0";
    private final static String DUMMY_TERM = "decimal";
    
    //Test values, control the index crawled and written by the tests.
    private final static String TEST_URL = "https://docs.python.org/3/library/array"; 
    private final static String HOP_LIMIT = "5";            // number of hops 
    private final static String TEST_TERM = "exception";    // term to search for  
    private final static String TEST_PAGE = "docs.python.org/3/c-api/buffer.html";    // page to search in 
    private final static String TEST_NAME = "JhopTest";
    // test query
    private final static String TEST_ATT = "class";         
    private final static String TEST_VAL = "section";       
    
    /**
     * Test whether a URL can successfully be crawled to populate a database.
     */
    @Test
    public void crawling() {
        Index index = new Index();
        index.getOptions().setName(TEST_NAME);
        
        try {
            Crawler crawler = initCrawler();
            index = crawler.exec(index);
            
            // pass if there were no errors and the test page was crawled
            if(!crawler.success()) fail(crawler.getError().getMessage());
            assertTrue(index.getPage(TEST_PAGE).size() > 0);
        }
        catch(CommandException e) {
            fail(e.getMessage());
        }
    }
    
    
    /**
     * Test whether an index can successfully be written to the database.
     */
    @Test
    public void creating() {
        Index index = new Index();
        index.getOptions().setName(TEST_NAME);
                
        //create test arguments
        List<String> args = new ArrayList<>();
        args.add(TEST_NAME);
        
        Model.connectDB();
        
        try {
            index = initCrawler().exec(index);
            Executable writer = new DatabaseIndexWriter(args, Model.getDBConnection());
            
            writer.exec(index);
            
            // pass if there were no errors and the database has the index
            if(!writer.success()) fail(writer.getError().getMessage());
            assertTrue(DatabaseHelper.dbContainsIndex(Model.getDBConnection(), TEST_NAME));
            
            //drop changes made to database
            Executable dropper = new DatabaseIndexDropper(args, Model.getDBConnection());
            dropper.exec(index);
        }
        catch(CommandException e) {
            fail(e.getMessage());
        }
        
        Model.disconnectDB();
    }
     
    /**
     * A simple to test to ensure the index can query the test term.
     */
    @Test
    public void searching() {
        try {
            createDummy();
        }
        catch(CommandException e) {
            fail("Dummy creation failed: " + e.getMessage());
        }
        
        Index index = new Index();
        Model.connectDB();
        
        //create test arguments
        List<String> args = new ArrayList<>();
        args.add(DUMMY_NAME);
        
        try {
            Executable reader = new DatabaseIndexReader(args, Model.getDBConnection());
            index = reader.exec(index);

            // pass if the index can find the dummy term
            assertTrue(index.getPagesContaining(DUMMY_TERM).size() > 0);
            dropDummy();
        }
        catch(CommandException e) {
            fail(e.getMessage());
        }
        
        Model.disconnectDB();
    }
    
    /**
     * Test whether an index can be read from the database.
     */
    @Test
    public void reading() {
        try {
            createDummy();
        }
        catch(CommandException e) {
            fail("Dummy creation failed: " + e.getMessage());
        }
        
        Index index = new Index();
        Model.connectDB();
        
        //create test arguments
        List<String> args = new ArrayList<>();
        args.add(DUMMY_NAME);
        
        try {
            Executable reader = new DatabaseIndexReader(args, Model.getDBConnection());
            index = reader.exec(index);
            
            //pass if the reader did not fail and the active index is correct
            if(!reader.success()) fail(reader.getError().getMessage());
            assertTrue(index.getOptions().getName().equals(DUMMY_NAME));
            assertTrue(index.getTerms().size() > 0);
            dropDummy();
        }
        catch(CommandException e) {
            fail(e.getMessage());
        }
        
        Model.disconnectDB();
    }

    /**
     * Test whether a database can drop an index
     */
    @Test
    public void dropping() {
        try {
            createDummy();
        }
        catch(CommandException e) {
            fail("Dummy creation failed: " + e.getMessage());
        }
        
        Model.connectDB();
        //create test arguments
        List<String> args = new ArrayList<>();
        args.add(DUMMY_NAME);
        
        try {
            Executable dropper = new DatabaseIndexDropper(args, Model.getDBConnection());
            dropper.exec(null);
            
            //pass if the reader did not fail and the index was dropped
            if(!dropper.success()) fail(dropper.getError().getMessage());
            assertTrue(!DatabaseHelper.dbContainsIndex(Model.getDBConnection(), DUMMY_NAME));
        }
        catch(CommandException e) {
            fail(e.getMessage());
        }
        
        Model.disconnectDB();
    }
    
    private Crawler initCrawler() throws CommandException{
        //create arguments for the test
        List<String> args = new ArrayList<>();
        args.add(TEST_URL);
        args.add(TEST_ATT);
        args.add(TEST_VAL);
        args.add(HOP_LIMIT);
        
        return new Crawler(args);
    }
    
    /**
     * This method inserts a dummy index into the database. Any method relying on 
     * the presence of an index in the database should call this method as they are required to not
     * rely on the results of other tests first inserting an index into the database.
     */
    private void createDummy() throws CommandException{
        //create args
        List<String> args = new ArrayList<>();
        args.add(DUMMY_URL);
        args.add(DUMMY_ATT);
        args.add(DUMMY_VAL);
        args.add(DUMMY_LIMIT);
        
        Index index = new Index();
        index.getOptions().setName(DUMMY_NAME);
        Model.connectDB();
        
        Executable crawler = new Crawler(args);
        index = crawler.exec(index);
        
        args.clear();
        args.add(DUMMY_NAME);
        
        Executable writer = new DatabaseIndexWriter(args, Model.getDBConnection());
        writer.exec(index);
        Model.disconnectDB();
    }
    
    /**
     * Drops any dummy indexes used to isolate tests.
     */
    private void dropDummy() throws CommandException{
        List<String> args = new ArrayList<>();
        args.add(DUMMY_NAME);
        Model.connectDB();
        
        Executable dropper = new DatabaseIndexDropper(args, Model.getDBConnection());
        dropper.exec(null);
                
        Model.disconnectDB();
    }
}
