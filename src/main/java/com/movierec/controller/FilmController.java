package com.movierec.controller;

import com.movierec.model.Film;
import com.movierec.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/films")
public class FilmController {

    @Autowired
    private FilmService filmService;

    @GetMapping("/search")
    public Mono<Film> findFilm(@RequestParam(required = false) String genre,
                               @RequestParam(required = false) String actor,
                               @RequestParam(required = false) String releaseYear,
                               @RequestParam(required = false) String keywords) {
        return ReactiveSecurityContextHolder.getContext()
                .map(context -> (UserDetails) context.getAuthentication().getPrincipal())
                .flatMap(userDetails -> filmService.findFilmByCriteria(userDetails.getUsername(), genre, actor, releaseYear, keywords));
    }

    @GetMapping("/random")
    public Mono<Film> findRandomFilm() {
        return filmService.findRandomFilm();
    }
}
