package com.example.voto.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document
@EqualsAndHashCode(callSuper = true)
public class Vote extends AbstractDomain {
    private String subject;
    private List<VoteItem> itemList;

    public Vote() {
        itemList = new ArrayList<>();
    }

    public Vote addVoteItem(VoteItem voteItem) {
        itemList.add(voteItem);
        return this;
    }
}
