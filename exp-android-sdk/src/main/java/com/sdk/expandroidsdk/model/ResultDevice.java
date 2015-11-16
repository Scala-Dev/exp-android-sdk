package com.sdk.expandroidsdk.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cesar Oyarzun on 10/30/15.
 */
public class ResultDevice {

    private Integer total;
    private List<Device> results = new ArrayList<Device>();

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
    public List<Device> getResults() {
        return results;
    }

    /**
     *
     * @param results
     * The results
     */
    public void setResults(List<Device> results) {
        this.results = results;
    }


}
