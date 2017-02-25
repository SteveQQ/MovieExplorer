package com.steveq.movieexplorer.model;


import java.util.ArrayList;

public class Person {
    String profile_path;
    ArrayList<Movie> known_for;
    String name;
    Double popularity;

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public ArrayList<Movie> getKnown_for() {
        return known_for;
    }

    public void setKnown_for(ArrayList<Movie> known_for) {
        this.known_for = known_for;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }
}
