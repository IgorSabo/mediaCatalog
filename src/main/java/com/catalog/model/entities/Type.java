package com.catalog.model.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Type {
	@Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
	int IDtype;
	String name;
	public int getIDtype() {
		return IDtype;
	}
	public void setIDType(int iDType) {
		IDtype = iDType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
