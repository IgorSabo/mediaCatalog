package com.catalog.model.entities;

import javax.persistence.*;

/**
 * Created by Gile on 3/29/2018.
 */
@Entity
@Table(name = "titleRatings")
public class TitleRating extends BaseEntity {

    private String value;
    private String source;

    @ManyToOne(fetch = FetchType.EAGER)
    TitleJsonResponse titleJsonResponse;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public TitleJsonResponse getTitleJsonResponse() {
        return titleJsonResponse;
    }

    public void setTitleJsonResponse(TitleJsonResponse titleJsonResponse) {
        this.titleJsonResponse = titleJsonResponse;
    }
}
