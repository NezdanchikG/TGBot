package com.movierec.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "preferences")
public class Preference {
    @Id
    private String id;
    private String userId;
    private String genre;
    private String actor;
    private String releaseYear;
}
