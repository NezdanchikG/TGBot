package com.movierec.controller;

import com.movierec.model.Film;
import com.movierec.model.WatchList;
import com.movierec.service.WatchListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import

        reactor.core.publisher.Mono;

@RestController
@RequestMapping("/watchlist")
public class WatchListController {

    @Autowired
    private WatchListService watchListService;

    @GetMapping
    public Mono<WatchList> getWatchList() {
        return ReactiveSecurityContextHolder.getContext()
                .map(context -> (UserDetails) context.getAuthentication().getPrincipal())
                .flatMap(userDetails -> watchListService.getWatchList(userDetails.getUsername()));
    }

    @PostMapping
    public Mono<WatchList> addFilmToWatchList(@RequestBody Film film) {
        return ReactiveSecurityContextHolder.getContext()
                .map(context -> (UserDetails) context.getAuthentication().getPrincipal())
                .flatMap(userDetails -> watchListService.addFilmToWatchList(userDetails.getUsername(), film));
    }

    @PutMapping("/mark-watched/{filmId}")
    public Mono<WatchList> markFilmAsWatched(@PathVariable String filmId) {
        return ReactiveSecurityContextHolder.getContext()
                .map(context -> (UserDetails) context.getAuthentication().getPrincipal())
                .flatMap(userDetails -> watchListService.markFilmAsWatched(userDetails.getUsername(), filmId));
    }

    @DeleteMapping("/{filmId}")
    public Mono<WatchList> removeFilmFromWatchList(@PathVariable String filmId) {
        return ReactiveSecurityContextHolder.getContext()
                .map(context -> (UserDetails) context.getAuthentication().getPrincipal())
                .flatMap(userDetails -> watchListService.removeFilmFromWatchList(userDetails.getUsername(), filmId));
    }
}
