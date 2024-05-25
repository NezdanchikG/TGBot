package com.movierec.service;

import com.movierec.model.Film;
import com.movierec.model.WatchList;
import com.movierec.repository.WatchListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Service
public class WatchListService {

    @Autowired
    private WatchListRepository watchListRepository;

    public Mono<WatchList> getWatchList(String userId) {
        return watchListRepository.findByUserId(userId);
    }

    public Mono<WatchList> addFilmToWatchList(String userId, Film film) {
        return watchListRepository.findByUserId(userId)
                .defaultIfEmpty(new WatchList())
                .flatMap(watchList -> {
                    watchList.setUserId(userId);
                    watchList.getFilms().add(film);
                    return watchListRepository.save(watchList);
                });
    }

    public Mono<WatchList> markFilmAsWatched(String userId, String filmId) {
        return watchListRepository.findByUserId(userId)
                .flatMap(watchList -> {
                    watchList.getFilms().stream()
                            .filter(film -> film.getId().equals(filmId))
                            .findFirst()
                            .ifPresent(film -> film.setWatched(true));
                    return watchListRepository.save(watchList);
                });
    }

    public Mono<WatchList> removeFilmFromWatchList(String userId, String filmId) {
        return watchListRepository.findByUserId(userId)
                .flatMap(watchList -> {
                    watchList.setFilms(watchList.getFilms().stream()
                            .filter(film -> !film.getId().equals(filmId))
                            .collect(Collectors.toList()));
                    return watchListRepository.save(watchList);
                });
    }
}