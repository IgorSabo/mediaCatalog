package com.catalog.model.entities;

import javax.persistence.*;

@Entity
@Table(name="cntbygenre")
public class CntByGenre {
	
	@Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int IDgenre;
	private int total;
	private String genre="";
	
	public int getIDgenre() {
		return IDgenre;
	}
	public void setIDgenre(int IDgenre) {
		this.IDgenre = IDgenre;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
	
	
}
