package com.sdk.expandroidsdk.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cesar Oyarzun on 10/30/15.
 */
public class ResultLocation {

    private Integer total;
    private List<Location> results = new ArrayList<Location>();

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
    public List<Location> getResults() {
        return results;
    }

    /**
     *
     * @param results
     * The results
     */
    public void setResults(List<Location> results) {
        this.results = results;
    }


}
