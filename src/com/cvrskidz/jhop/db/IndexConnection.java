package com.cvrskidz.jhop.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class IndexConnection {
    private SessionFactory sessionFactory;
    
    public IndexConnection() {
        // Build registry to get application session
        final StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
        final StandardServiceRegistry registry = registryBuilder.configure().build();
        
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            // Clean up
            StandardServiceRegistryBuilder.destroy(registry);
            System.out.println(e);
            e.printStackTrace();
            sessionFactory = null;
        }
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public static void main(String[] args) {
        // Testing database connection
        IndexConnection db = new IndexConnection();
        Session session = db.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(new IndexLog("test ", "class", "content"));
        session.getTransaction().commit();
        session.close();
    }
}
