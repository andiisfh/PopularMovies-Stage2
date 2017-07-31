package com.popularmoviesdomain.model.movies;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by andiisfh on 01/07/17.
 */

public class MoviesModel {

    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("total_results")
    @Expose
    private Integer totalMoviesLists;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("results")
    @Expose
    private List<MoviesList> results = null;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalMoviesLists() {
        return totalMoviesLists;
    }

    public void setTotalMoviesLists(Integer totalMoviesLists) {
        this.totalMoviesLists = totalMoviesLists;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<MoviesList> getMoviesLists() {
        return results;
    }

    public void setMoviesLists(List<MoviesList> results) {
        this.results = results;
    }

}
