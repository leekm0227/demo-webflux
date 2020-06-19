package com.example.voto.repository;

import com.example.voto.domain.Vote;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface VoteRepository extends ReactiveCrudRepository<Vote, String> {

    Flux<Vote> findAllByOrderByCreateAtDesc(Pageable pageable);
}
