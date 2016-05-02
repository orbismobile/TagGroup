package com.orbis.tag.model.entity;

import java.io.Serializable;

/**
 * Created by untricardobravo on 14/04/16.
 */

public class TagEntity implements Serializable {

    String id, description;

    public TagEntity(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
