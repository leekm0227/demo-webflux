package com.example.voto.repository;

import com.example.voto.domain.User;
import com.example.voto.domain.Vote;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, String> {

    Flux<User> findAllByOrderByCreateAtDesc(Pageable pageable);

    Mono<User> findByAccountAndPassword(String account, String password);
}
