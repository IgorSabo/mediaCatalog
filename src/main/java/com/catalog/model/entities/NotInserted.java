package com.catalog.model.entities;

import javax.persistence.*;

@Entity
@Table(name="notinserted")
public class NotInserted {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int IDfilm;
	
	String name;
    String type;
    @Column(name = "path")
    String location;
    
    @Column(name = "lastadded")
    int lastAdded;

    public int getIDfilm() {
        return IDfilm;
    }

    public void setIDfilm(int IDfilm) {
        this.IDfilm = IDfilm;
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
}
