package com.movierec.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "watchlists")
public class WatchList {
    @Id
    private String id;
    private String userId;
    private List<Film> films;
}
