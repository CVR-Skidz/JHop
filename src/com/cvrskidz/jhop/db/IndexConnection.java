package com.cvrskidz.jhop.db;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class IndexConnection {
    private final String url = "jdbc:derby:db\\JhopIndexes";
    
    private Connection db;
    
    public IndexConnection(String uname, String password) {
        try {
            db = DriverManager.getConnection(url, uname, password);
            System.out.println("Connected with " + db.getMetaData().getDriverName());
        }
        catch (SQLException e) {
            System.out.println("Could not connect to " + url);
            do {
                System.out.println(e.getMessage());
                e = e.getNextException();
            } while (e != null);
        }
    }
    
    public static void main(String[] args) {
        // Testing database connection
        Scanner input = new Scanner(System.in);
        
        System.out.print("Username: ");
        String uname = input.nextLine();
        
        System.out.print("Password: ");
        String password = input.nextLine();

        System.out.println(uname + " : " + password);
        new IndexConnection(uname, password);
    }
}
