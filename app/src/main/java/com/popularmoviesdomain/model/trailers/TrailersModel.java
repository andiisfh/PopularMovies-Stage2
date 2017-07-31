package com.popularmoviesdomain.model.trailers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by andiisfh on 31/07/17.
 */

public class TrailersModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<TrailersList> results = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<TrailersList> getResults() {
        return results;
    }

    public void setResults(List<TrailersList> results) {
        this.results = results;
    }
}
