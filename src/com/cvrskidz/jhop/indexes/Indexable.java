package com.cvrskidz.jhop.indexes;

/**
 * The Indexable interface marks that a class can index or track the contents of
 * a certain type.
 * <p>
 * Classes that implement Indexable should fully index inputs when the `index` 
 * method is called. Any executing code can invoke the indexing functionality of
 * an implementing class by calling `index`.
 * 
 * @author cvrskidz 18031335
 * @param <V> The type of object indexed.
 */
public interface Indexable<V, K> extends Updatable<V> {
    /**
     * Index the given object.
     * 
     * @param e The object to index
     * @return A reference to this after indexing.
     */
    public Indexable<V, K> index(V e);
    
    /**
     * Checks whether the specified key has been indexed.
     * 
     * @param key The key to check.
     * @return True if the key has been indexed. False otherwise.
     */
    public boolean contains(K key);
}
