package com.catalog.model.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Genre {
	@Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
	int IDgenre;
	String name;
	public int getIDgenre() {
		return IDgenre;
	}
	public void setIDgenre(int iDgenre) {
		IDgenre = iDgenre;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}