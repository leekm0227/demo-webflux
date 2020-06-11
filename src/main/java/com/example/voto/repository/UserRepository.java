package com.example.voto.repository;

import com.example.voto.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.math.BigInteger;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, BigInteger> {

    Flux<User> findAllByOrderByCreateAtDesc(Pageable pageable);
}
