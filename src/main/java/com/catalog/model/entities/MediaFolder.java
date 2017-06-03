package com.catalog.model.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Gile on 4/30/2017.
 */
@Entity
public class MediaFolder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int pathId;
    String path;

    public int getPathId() {
        return pathId;
    }

    public void setPathId(int pathId) {
        this.pathId = pathId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
