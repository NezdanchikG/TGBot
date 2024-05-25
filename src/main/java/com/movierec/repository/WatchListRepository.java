package com.movierec.repository;

import com.movierec.model.WatchList;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface WatchListRepository extends ReactiveMongoRepository<WatchList, String> {
    Mono<WatchList> findByUserId(String userId);
}
