/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.model.entities;

import javax.persistence.*;

/**
 *
 * @author Gile
 */
@Entity
@Table(name="rawnames")
public class RawNames {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int IDname;
    String name;
    String type;
    
    @Column(name = "path")
    String location;
    
    @Column(name = "lastadded")
    int lastAdded;

    public int getIDname() {
        return IDname;
    }

    public void setIDname(int IDname) {
        this.IDname = IDname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    

    public int getLastAdded() {
        return lastAdded;
    }

    public void setLastAdded(int lastAdded) {
        this.lastAdded = lastAdded;
    }
    
    
}
