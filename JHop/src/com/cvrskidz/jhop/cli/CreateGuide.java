package com.cvrskidz.jhop.cli;

import com.cvrskidz.jhop.exceptions.CommandException;
import com.cvrskidz.jhop.executables.Crawler;
import com.cvrskidz.jhop.executables.Executable;
import com.cvrskidz.jhop.executables.indexutil.IndexWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class CreateGuide extends Guide {
    private Executable crawler, writer;
    
    public CreateGuide(Scanner input) {
        super(input);
        crawler = null;
        writer = null;
    }
    
    @Override
    public void poll() {
        String start, attribute, value, hops, name;
        
        do {
            start = prompt("Enter starting url: ");
        } while(start.isEmpty());
        
        do {
            attribute = prompt("Enter crawler attribute: ");
        } while(attribute.isEmpty());
        
        do {
            value = prompt("Enter attribute value: ");
        } while(value.isEmpty());
        
        do {
            hops = prompt("Enter maximum hops: ");
        } while(hops.isEmpty());
        
        do {
            name = prompt("Enter index name: ");
        } while(name.isEmpty());
        
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
    
    @Override
    public void display() {
        if(crawler == null || writer == null) return;
        
        Guide.index = crawler.exec(Guide.index);
        if(!crawler.success()) {
            System.out.println(crawler.getError().getMessage());
            return;
        }
        
        Guide.index = writer.exec(Guide.index);
        if(!crawler.success()) {
            System.out.println(crawler.getError().getMessage());
        }
    }
    
    private void initCrawler(String s, String a, String v, String h) throws CommandException{
        ArrayList<String> args = new ArrayList();
        args.add(s);
        args.add(a);
        args.add(v);
        args.add(h);
        
        crawler = new Crawler(args);
    }
    
    private void initWriter(String n) throws CommandException{
        ArrayList<String> args = new ArrayList();
        args.add(n);
        
        writer = new IndexWriter(args);
    }
}
