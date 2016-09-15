package com.scala.exp.android.sdk.model;

import android.support.annotation.NonNull;

import com.google.gson.internal.LinkedTreeMap;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by adamgalloway on 12/10/15.
 */
public class SearchResults<T> extends AbstractModel implements List<T> {

    private Integer total;
    private List<T> results = new ArrayList<T>();

    /**
     *
     * @return
     * The total
     */
    public Integer getTotal() {
        return total;
    }

    /**
     *
     * @param total
     * The total
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     *
     * @return
     * The results
     */
    public List<T> getResults() {
        return results;
    }

    /**
     *
     * @param results
     * The results
     */
    public void setResults(List<T> results) {
        this.results = results;
    }


    @Override
    public void add(int location, T object) {

    }

    @Override
    public boolean add(T object) {

        return false;
    }

    @Override
    public boolean addAll(int location, Collection<? extends T> collection) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> collection) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean contains(Object object) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return false;
    }

    @Override
    public T get(int location) {
        return this.results.get(location);
    }

    @Override
    public int indexOf(Object object) {
        return this.results.indexOf(object);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @NonNull
    @Override
    public Iterator<T> iterator() {
        return this.results.iterator();
    }

    @Override
    public int lastIndexOf(Object object) {
        return this.results.lastIndexOf(object);
    }

    @Override
    public ListIterator<T> listIterator() {
        return this.results.listIterator();
    }

    @NonNull
    @Override
    public ListIterator<T> listIterator(int location) {
        return this.results.listIterator(location);
    }

    @Override
    public T remove(int location) {
        return this.results.remove(location);
    }

    @Override
    public boolean remove(Object object) {
        return this.results.remove(object);
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return false;
    }

    @Override
    public T set(int location, T object) {
        return null;
    }

    @Override
    public int size() {
        return this.results.size();
    }

    @NonNull
    @Override
    public List<T> subList(int start, int end) {
        return null;
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @NonNull
    @Override
    public <T1> T1[] toArray(T1[] array) {
        return null;
    }
}

