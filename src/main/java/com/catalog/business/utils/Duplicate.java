package com.catalog.business.utils;

/**
 * Created by Gile on 5/18/2017.
 */
public class Duplicate {

    public Duplicate(int id, String location, String name, long occurence){
        this.id = id;
        this.location = location;
        this.occurence = occurence;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getOccurence() {
        return occurence;
    }

    public void setOccurence(long occurence) {
        this.occurence = occurence;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    int id;

    long occurence;

    String location;

    String name;
}
