package com.cvrskidz.jhop.db.indexutil;

import com.cvrskidz.jhop.db.*;
import java.util.List;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 * The DatabaseHelper class contains static methods to perform hibernate operations
 * on a connected database. This could include executing HQL on a session, manipulating
 * object mappings, and saving or deleting persistent objects.
 * 
 * @author cvrskidz 18031335
 */
public class DatabaseHelper{
    /**
     * Check whether the given database contains the given index.
     * 
     * @param db The connected database connection
     * @param indexName The name of the target index
     * @return  True if the database contains the index, false otherwise.
     */
    public static boolean dbContainsIndex(IndexConnection db, String indexName) {
        indexName = indexName.replace("'", "\'");
        StringBuilder hql = new StringBuilder("from IndexLog ");
        hql.append("where index_name='").append(indexName).append("'");
        
        List results = DatabaseHelper.execute(db, hql.toString());
        return results != null ? results.size() > 0 : false;
    }
    
    /**
     * Check whether the given database contains the given page within the given index.
     * 
     * @param db The connected database
     * @param url The page URL
     * @param indexName The target index name
     * @return True if the database contains the page within the index, false otherwise.
     */
    public static boolean dbContainsPage(IndexConnection db, String url, String indexName) {
        indexName = indexName.replace("'", "\'");
        url = url.replace("'", "\'");
        StringBuilder hql = new StringBuilder("from Page ");
        hql.append("where url='").append(url).append("' ");
        hql.append("and index_name='").append(indexName).append("'");
        
        List results = DatabaseHelper.execute(db, hql.toString());
        return results != null ? results.size() > 0 : false;

    }
    
    /**
     * Check whether the given database contains the given term within the given page.
     * 
     * @param db The connected database
     * @param term The term to query
     * @param url The page containing the term
     * @return True if the database has indexed the term within the page, false otherwise.
     */
    public static boolean dbContainsTerm(IndexConnection db, String term, String url) {
        url = url.replace("'", "\'");
        term = term.replace("'", "[comma]");
        StringBuilder hql = new StringBuilder("from Term ");
        hql.append("where term='").append(term).append("' ");
        hql.append("and page='").append(url).append("'");
        
        List results = DatabaseHelper.execute(db, hql.toString());
        return results != null ? results.size() > 0 : false;
    }
    
    /**
     * Execute the given HQL on the active session.
     * 
     * @param db The connected database connection.
     * @param hql The HQL to execute.
     * @return A list of results for the given query.
     */
    public static List execute(IndexConnection db, String hql) {
        Session session = db.getSession();
        session.beginTransaction();
        Query query = session.createQuery(hql);
        List results = query.list();
        session.getTransaction().commit();
        
        return results;
    }
    
    /**
     * Execute a query that modifies records in the database.
     * 
     * @param db The connected database
     * @param hql The query to execute
     * @return The number of rows modified.
     */
    public static int executeModification(IndexConnection db, String hql) {
        Session session = db.getSession();
        session.beginTransaction();
        Query query = session.createQuery(hql);
        int results = query.executeUpdate();
        session.getTransaction().commit();
        
        return results;
    }
    
    /**
     * Save an object into the database.
     * 
     * @param db The connected database.
     * @param o The object to save.
     */
    public static void save(IndexConnection db, Object o) {
        Session session = db.getSession();
        session.beginTransaction();

        try {
            session.save(o);
        }
        catch (NonUniqueObjectException e) {    // Was there a conflict with this object in the session?
            session.remove(o);
            session.save(o);
        }
        finally {
            session.getTransaction().commit();
        }
    }
    
    /**
     * Update the record in the database mapped to the given object.
     * 
     * @param db The connected database.
     * @param o The object to update.
     */
    public static void update(IndexConnection db, Object o) {
        Session session = db.getSession();
        session.beginTransaction();
        session.update(o);
        session.getTransaction().commit();
    }
    
    /**
     * Delete the given object from the database.
     * 
     * @param db The connected database.
     * @param o The object to delete.
     */
    public static void delete(IndexConnection db, Object o) {
        Session session = db.getSession();
        session.beginTransaction();
        session.delete(o);
        session.getTransaction().commit();
    }
}
