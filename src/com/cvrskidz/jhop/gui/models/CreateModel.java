package com.cvrskidz.jhop.gui.models;

import com.cvrskidz.jhop.db.indexutil.DatabaseIndexWriter;
import com.cvrskidz.jhop.exceptions.CommandException;
import com.cvrskidz.jhop.executables.Crawler;
import com.cvrskidz.jhop.executables.Executable;
import com.cvrskidz.jhop.gui.view.JHopDetailsView;
import com.cvrskidz.jhop.indexes.Index;
import java.util.ArrayList;

/**
 * A model to acquire to crawl and index web pages. 
 *
 * @author cvrskidz 18031335
 * @see com.cvrskidz.jhop.executables.Crawler
 */
public class CreateModel extends Model {
    private Executable crawler, writer;
    private String name;
    
    /**
     * Construct a new CreateModel.
     */
    public CreateModel() {
        ARGC = 5;
        crawler = null;
        writer = null;
    }
    
    /**
     * Add the newly created index to JHop's view.
     */
    @Override
    public void alert() {
        if(error == null) {
            JHopDetailsView sideBar = Model.observer.getSideBar();
            sideBar.addIndex(name);         //add the index name to the view
            Model load = new LoadModel();   //load the pages of the new index into the view
            load.alert();                   //load without reading a new index
        }
        else {
            Model.observer.showError(error.getMessage());
        }
    }
    
    /**
     * Crawl and index the results as specified by the input of this model.
     * 
     * @param args This model expects to receive the following arguments, in the 
     * corresponding order: 
     * <pre>{index name}, {source url}, {tag attribute}, {attribute value}, {hop limit}</pre>
     * 
     * @see com.cvrskidz.jhop.executables.Crawler
     * @see com.cvrskidz.jhop.executables.indexutil.IndexWriter
     */
    @Override
    public void update(String... args) {
        if(args.length != ARGC) return;

        try {
            //Create crawler and writer
            name = args[0];
            initWriter();
            initCrawler(args[1], args[2], args[3], args[4]);
            
            Thread createThread = new Thread(this);
            createThread.start();
        }
        catch(CommandException e) {
            error = e;
            alert();
        }
    }
    
    /**
     * Creates a new Crawler, concatenating the required options into a list of arguments.
     * 
     * @param s The source URL
     * @param a The attribute to query
     * @param v The value to query for
     * @param h The hop limit for the crawler
     * @see com.cvrskidz.jhop.executables.Crawler
     */
    private void initCrawler(String s, String a, String v, String h) throws CommandException{
        ArrayList<String> args = new ArrayList();
        args.add(s);
        args.add(a);
        args.add(v);
        args.add(h);
        
        crawler = new Crawler(args);
    }
    
    /**
     * Creates a new IndexWriter from this instances index name.
     *
     * @see com.cvrskidz.jhop.executables.indexutil.IndexWriter
     */
    private void initWriter() throws CommandException{
        ArrayList<String> args = new ArrayList();
        args.add(name);
        
        writer = new DatabaseIndexWriter(args, Model.db);
    }

    /**
     * Execute the crawler associated with this instance on a new thread, 
     * setting the model to active until it is finished either successfully
     * or not.
     */
    @Override
    public void run() {
        Model.toggleActive();
        Model.index = crawler.exec(new Index());        //Crawl urls
        Model.toggleActive();
        if(crawler.success()) {
            Model.toggleActive();
            Model.index = writer.exec(Model.index);     //Write to index
            Model.toggleActive();
            error = writer.getError();
        }
        else error = crawler.getError();
            
        alert();
    }
}
