/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.model.entities;

import javax.persistence.*;

/**
 * @author Gile
 */
@Entity
public class Title {
    @Id
    private int IDfilm;

    @Column(name = "rawname")
    private String rawName;

    @Column(name = "imdbtitle")
    private String imdbTitle;
    private int IDtype;
    private String genre;
    private String year;
    private String IMDBlink;
    private String location;
    @Lob
    private String description;
    private String quality;
    private String actors;
    private String picture;

    @Column(name = "lastadded")
    private int lastAdded;

    @Column(name = "mustwatch")
    private int mustWatch;

    private int favorite;
    private int incorrect;
    private Float imdbRating;

    @Lob
    private String apiResponse;

    @OneToOne(mappedBy = "titleEntity", cascade = CascadeType.REMOVE)
    private TitleJsonResponse titleJsonResponse;

    @Override
    public String toString() {
        return "Title{" +
                "IDfilm=" + IDfilm +
                ", rawName='" + rawName + '\'' +
                ", imdbTitle='" + imdbTitle + '\'' +
                ", IDtype=" + IDtype +
                ", genre='" + genre + '\'' +
                ", year='" + year + '\'' +
                ", IMDBlink='" + IMDBlink + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", quality='" + quality + '\'' +
                ", actors='" + actors + '\'' +
                ", picture='" + picture + '\'' +
                ", lastAdded=" + lastAdded +
                ", mustWatch=" + mustWatch +
                ", favorite=" + favorite +
                ", incorrect=" + incorrect +
                ", imdbRating=" + imdbRating +
                ", apiResponse='" + apiResponse + '\'' +
                '}';
    }

    public int getIDfilm() {
        return IDfilm;
    }

    public void setIDfilm(int IDfilm) {
        this.IDfilm = IDfilm;
    }

    public String getRawName() {
        return rawName;
    }

    public void setRawName(String rawName) {
        this.rawName = rawName;
    }

    public String getImdbTitle() {
        return imdbTitle;
    }

    public void setImdbTitle(String imdbTitle) {
        this.imdbTitle = imdbTitle;
    }

    public int getIDtype() {
        return IDtype;
    }

    public void setIDtype(int IDtype) {
        this.IDtype = IDtype;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getIMDBlink() {
        return IMDBlink;
    }

    public void setIMDBlink(String IMDBlink) {
        this.IMDBlink = IMDBlink;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getLastAdded() {
        return lastAdded;
    }

    public void setLastAdded(int lastAdded) {
        this.lastAdded = lastAdded;
    }

    public int getMustWatch() {
        return mustWatch;
    }

    public void setMustWatch(int mustWatch) {
        this.mustWatch = mustWatch;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public int getIncorrect() {
        return incorrect;
    }

    public void setIncorrect(int incorrect) {
        this.incorrect = incorrect;
    }

    public Float getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(Float imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getApiResponse() {
        return apiResponse;
    }

    public void setApiResponse(String apiResponse) {
        this.apiResponse = apiResponse;
    }

    public TitleJsonResponse getTitleJsonResponse() {
        return titleJsonResponse;
    }

    public void setTitleJsonResponse(TitleJsonResponse titleJsonResponse) {
        this.titleJsonResponse = titleJsonResponse;
    }
}
