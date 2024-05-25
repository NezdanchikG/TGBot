package com.movierec.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "films")
public class Film {
    @Id
    private String id;
    private String title;
    private String genre;
    private String actor;
    private String releaseYear;
    private String description;

    // Add the watched field
    private boolean watched;

    // Add setter for watched field
    public void setWatched(boolean watched) {
        this.watched = watched;
    }

    // Add getter for watched field
    public boolean isWatched() {
        return watched;
    }
}