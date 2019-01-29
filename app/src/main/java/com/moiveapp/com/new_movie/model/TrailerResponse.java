package com.moiveapp.com.new_movie.model;

import java.util.List;

public class TrailerResponse
{
    private String id;

    private List<TrailerResults> results;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public List<TrailerResults> getResults() {
        return results;
    }

    public void setResults(List<TrailerResults> results) {
        this.results = results;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", results = "+results+"]";
    }
}