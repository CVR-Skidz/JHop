package com.cvrskidz.jhop.db;

import java.util.logging.Level;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * An IndexConnection connects a JHop application to the JHop database with
 * hibernate, and creates a session for the application.
 * 
 * @author cvrskidz 18031335
 */
public class IndexConnection {
    private Session session;
    private SessionFactory sessionFactory; 
    
    /**
     * Create a connection to the JHop database.
     */
    public IndexConnection() {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        // Build registry to get application session
        final StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
        final StandardServiceRegistry registry = registryBuilder.configure().build();
        
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            session = sessionFactory.openSession();
        }
        catch (HibernateException e) {
            // Clean up
            StandardServiceRegistryBuilder.destroy(registry);
            System.out.println(e.getMessage());
        }
    }

    /**
     * @return The session connected to the JHop database.
     */
    public Session getSession() {
        return session;
    }
    
    /**
     * Close and reopen the active database session of this instance.
     */
    public void reloadSession() {
        session.close();
        session = sessionFactory.openSession();
    }
}
