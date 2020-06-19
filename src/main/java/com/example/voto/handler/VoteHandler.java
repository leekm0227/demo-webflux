package com.example.voto.handler;

import com.example.voto.config.ErrorCode;
import com.example.voto.domain.Result;
import com.example.voto.domain.User;
import com.example.voto.domain.Vote;
import com.example.voto.domain.VoteItem;
import com.example.voto.repository.UserRepository;
import com.example.voto.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Component
public class VoteHandler extends AbstractHandler {

    @Autowired
    UserRepository userRepository;

    @Autowired
    VoteRepository voteRepository;

    public Mono<ServerResponse> getVote(ServerRequest request) {
        return response.apply(voteRepository.findAllByOrderByCreateAtDesc(paging.apply(request))
                .collectList()
                .flatMap(votes -> Mono.just(new Result() {{
                    setErrorCode(ErrorCode.DUPLICATE_KEY);
                    addData("vote", votes);
                }}))
        );
    }

    public Mono<ServerResponse> createVote(ServerRequest request) {
        return request.bodyToMono(Vote.class).flatMap(
                vote -> response.apply(voteRepository.save(vote)
                        .flatMap(createdVote -> Mono.just(new Result() {{
                            addData("vote", createdVote);
                        }}))
                )
        );
    }

    public Mono<ServerResponse> updateVote(ServerRequest request) {
        return response.apply(voteRepository.findById(request.pathVariable("id")).flatMap(
                updated -> request.bodyToMono(Vote.class).flatMap(
                        vote -> {
                            if (!vote.getSubject().isEmpty()) updated.setSubject(vote.getSubject());
                            return voteRepository.save(updated);
                        }
                )).flatMap(vote -> Mono.just(new Result() {{
                    addData("vote", vote);
                }}))
        );
    }

    public Mono<ServerResponse> getVoteById(ServerRequest request) {
        return response.apply(voteRepository
                .findById(request.pathVariable("id"))
                .flatMap(vote -> Mono.just(new Result() {{
                    addData("vote", vote);
                }}))
        );
    }

    public Mono<ServerResponse> addVoteItem(ServerRequest request) {
        return response.apply(voteRepository.findById(request.pathVariable("id")).flatMap(
                vote -> request.bodyToMono(VoteItem.class).flatMap(
                        voteItem -> voteRepository.save(vote.addVoteItem(voteItem))
                                .flatMap(updatedVote -> Mono.just(new Result() {{
                                    addData("vote", vote);
                                }}))
                ))
        );
    }

    @Transactional
    public Mono<ServerResponse> likeVoteItem(ServerRequest request) {
        String voteId = request.pathVariable("id");
        String itemId = request.pathVariable("itemId");
        String userId = request.headers().firstHeader("auth");
        assert userId != null;

        return response.apply(voteRepository.findById(voteId).zipWith(userRepository.findById(userId)).map(findTuple -> {
                    Vote vote = findTuple.getT1();
                    User user = findTuple.getT2();

                    if (user.decreaseTicket()) {
                        vote.setItemList(vote.getItemList().stream().peek(voteItem -> {
                            if (voteItem.getId().equals(itemId)) voteItem.increaseCount();
                        }).collect(Collectors.toList()));
                    }

                    return findTuple;
                }).flatMap(saveTuple -> Mono.zip(voteRepository.save(saveTuple.getT1()), userRepository.save(saveTuple.getT2()))
                        .flatMap(objects1 -> Mono.just(new Result() {{
                            addData("vote", objects1.getT1());
                            addData("user", objects1.getT2());
                        }}))
                )
        );
    }
}
