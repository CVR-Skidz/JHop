package com.cvrskidz.jhop.indexes;

public interface Indexable<E> extends Updateable<E>{
    public Indexable<E> index(E e);
    public boolean contains(IndexEntry key);
}
