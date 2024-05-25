package com.movierec.service;

import com.movierec.model.Film;
import com.movierec.model.Preference;
import com.movierec.repository.FilmRepository;
import com.movierec.repository.PreferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class FilmService {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private PreferenceRepository preferenceRepository;

    public Mono<Film> findFilmByCriteria(String userId, String genre, String actor, String releaseYear, String keywords) {
        // Find user preferences if some criteria are not set
        Mono<Preference> userPreferences = preferenceRepository.findByUserId(userId).next();

        return userPreferences.flatMap(preference -> {
            String finalGenre = genre != null ? genre : preference.getGenre();
            String finalActor = actor != null ? actor : preference.getActor();
            String finalReleaseYear = releaseYear != null ? releaseYear : preference.getReleaseYear();

            return filmRepository.findFilmByCriteria(finalGenre, finalActor, finalReleaseYear, keywords);
        });
    }

    public Mono<Film> findRandomFilm() {
        return filmRepository.findRandomFilm();
    }
}