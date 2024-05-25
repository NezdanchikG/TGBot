package com.movierec.controller;

import com.movierec.model.Preference;
import com.movierec.repository.PreferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/preferences")
public class PreferenceController {

    @Autowired
    private PreferenceRepository preferenceRepository;

    @GetMapping
    public Flux<Preference> getUserPreferences() {
        return ReactiveSecurityContextHolder.getContext()
                .map(context -> (UserDetails) context.getAuthentication().getPrincipal())
                .flatMapMany(userDetails -> preferenceRepository.findByUserId(userDetails.getUsername()));
    }

    @PostMapping
    public Mono<Preference> addPreference(@RequestBody Preference preference) {
        return ReactiveSecurityContextHolder.getContext()
                .map(context -> (UserDetails) context.getAuthentication().getPrincipal())
                .flatMap(userDetails -> {
                    preference.setUserId(userDetails.getUsername());
                    return preferenceRepository.save(preference);
                });
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deletePreference(@PathVariable String id) {
        return preferenceRepository.deleteById(id);
    }
}
