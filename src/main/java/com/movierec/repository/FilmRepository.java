package com.movierec.repository;

import com.movierec.model.Film;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface FilmRepository extends ReactiveMongoRepository<Film, String> {

    @Query("{ 'genre': ?0, 'actor': ?1, 'releaseYear': ?2, 'description': { $regex: ?3, $options: 'i' } }")
    Mono<Film> findFilmByCriteria(String genre, String actor, String releaseYear, String keywords);

    @Query("{ }")
    Mono<Film> findRandomFilm();
}