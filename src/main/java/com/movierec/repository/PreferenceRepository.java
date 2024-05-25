package com.movierec.repository;

import com.movierec.model.Preference;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface PreferenceRepository extends ReactiveMongoRepository<Preference, String> {
    Flux<Preference> findByUserId(String userId);
}
