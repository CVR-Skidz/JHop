package com.cvrskidz.jhop.db.indexutil;

import com.cvrskidz.jhop.db.*;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class DatabaseHelper{
    public static boolean dbContainsIndex(IndexConnection db, String indexName) {
        indexName = indexName.replace("'", "\'");
        StringBuilder hql = new StringBuilder("from IndexLog ");
        hql.append("where index_name='").append(indexName).append("'");
        
        List results = DatabaseHelper.execute(db, hql.toString());
        return results != null ? results.size() > 0 : false;
    }
    
    public static boolean dbContainsPage(IndexConnection db, String url, String indexName) {
        indexName = indexName.replace("'", "\'");
        url = url.replace("'", "\'");
        StringBuilder hql = new StringBuilder("from Page ");
        hql.append("where url='").append(url).append("' ");
        hql.append("and index_name='").append(indexName).append("'");
        
        List results = DatabaseHelper.execute(db, hql.toString());
        return results != null ? results.size() > 0 : false;

    }
    
    public static boolean dbContainsTerm(IndexConnection db, String term, String url) {
        url = url.replace("'", "\'");
        term = term.replace("'", "[comma]");
        StringBuilder hql = new StringBuilder("from Term ");
        hql.append("where term='").append(term).append("' ");
        hql.append("and page='").append(url).append("'");
        
        List results = DatabaseHelper.execute(db, hql.toString());
        return results != null ? results.size() > 0 : false;
    }
    
    public static List execute(IndexConnection db, String hql) {
        Session session = db.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery(hql);
        List results = query.list();
        session.getTransaction().commit();
        session.close();
        
        return results;
    }
    
    public static int executeModification(IndexConnection db, String hql) {
        Session session = db.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery(hql);
        int results = query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        
        return results;
    }
    
    public static void save(IndexConnection db, Object o) {
        Session session = db.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(o);
        session.getTransaction().commit();
        session.close();
    }
    
    public static void update(IndexConnection db, Object o) {
        Session session = db.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(o);
        session.getTransaction().commit();
        session.close();
    }
    
    public static void delete(IndexConnection db, Object o) {
        Session session = db.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(o);
        session.getTransaction().commit();
        session.close();
    }
}
