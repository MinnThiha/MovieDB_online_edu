package com.moiveapp.com.new_movie.model;

import com.moiveapp.com.new_movie.model.Backdrops;

import java.util.List;

public class DetailSs
{
    private String id;

    private List<Backdrops> backdrops;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public List<Backdrops> getBackdrops() {
        return backdrops;
    }

    public void setBackdrops(List<Backdrops> backdrops) {
        this.backdrops = backdrops;
    }
}