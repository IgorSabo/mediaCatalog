package com.catalog.model.entities;

import javax.persistence.*;

@Entity
@Table(name="cntbytype")
public class CntByType {
	@Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int IDtype;
	private int total;
	private String type="";
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getIDtype() {
		return IDtype;
	}
	public void setIDtype(int iDtype) {
		IDtype = iDtype;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
	
}
