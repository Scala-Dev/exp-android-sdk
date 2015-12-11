package com.scala.exp.android.sdk.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adamgalloway on 12/10/15.
 */
public class SearchResults<T> {

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

}

