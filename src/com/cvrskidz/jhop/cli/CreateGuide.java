package com.cvrskidz.jhop.cli;

import com.cvrskidz.jhop.exceptions.CommandException;
import com.cvrskidz.jhop.executables.Crawler;
import com.cvrskidz.jhop.executables.Executable;
import com.cvrskidz.jhop.executables.indexutil.IndexWriter;
import com.cvrskidz.jhop.indexes.Index;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * An interactable guide to acquire the relevant input from the user
 * to crawl and index web pages. Upon displaying this component the 
 * active index is populated using a Crawler.
 *
 * @author cvrskidz 18031335
 * @see com.cvrskidz.jhop.executables.Crawler
 */
public class CreateGuide extends Guide {
    private Executable crawler, writer;
    
    /**
     * Construct a new CreateGuide with the given input scanner.
     * @param input The Scanner instance associated with the parent interface.
     */
    public CreateGuide(Scanner input) {
        super(input);
        crawler = null;
        writer = null;
    }
    
    /**
     * Prompt the user for a starting url, attribute query, hop limit,
     * and index name. 
     *
     * @see com.cvrskidz.jhop.indexes.Index
     */
    @Override
    public void poll() {
        String start, attribute, value, hops, name;
        
        // get url
        do {
            start = prompt("Enter starting url: ");
        } while(start.isEmpty());
        
        // get attribute
        do {
            attribute = prompt("Enter crawler attribute: ");
        } while(attribute.isEmpty());
        
        // get attribute value
        do {
            value = prompt("Enter attribute value: ");
        } while(value.isEmpty());
        
        // get hop limit
        do {
            hops = prompt("Enter maximum hops: ");
        } while(hops.isEmpty());
        
        // get index name
        do {
            name = prompt("Enter index name: ");
        } while(name.isEmpty());
        
        // create executables
        try {
            initCrawler(start, attribute, value, hops);
            initWriter(name);
        }
        catch(CommandException e) {
            System.out.println(e.getMessage());
            crawler = null;
            writer = null;
        }
    }
    
    /**
     * Crawl and index the results as specified by the input scanned by this guide.
     * Displaying the saved index.
     * @see com.cvrskidz.jhop.executables.Crawler
     * @see com.cvrskidz.jhop.executables.indexutil.IndexWriter
     */
    @Override
    public void display() {
        if(crawler == null || writer == null) return;
        
        Guide.index = crawler.exec(new Index());
        if(!crawler.success()) {
            System.out.println(crawler.getError().getMessage());
            return;
        }
        
        Guide.index = writer.exec(Guide.index);
        if(!crawler.success()) {
            System.out.println(crawler.getError().getMessage());
        }
    }
    
    /**
     * Creates a new Crawler, concatenating the required options into a list of arguments.
     *
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
     * Creates a new IndexWriter, concatenating the required options into a list of arguments.
     *
     * @see com.cvrskidz.jhop.executables.indexutil.IndexWriter
     */
    private void initWriter(String n) throws CommandException{
        ArrayList<String> args = new ArrayList();
        args.add(n);
        
        writer = new IndexWriter(args);
    }
}
