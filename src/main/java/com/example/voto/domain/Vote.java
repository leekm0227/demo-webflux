package com.example.voto.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
@EqualsAndHashCode(callSuper = true)
public class Vote extends AbstractDomain {
    private String subject;
    private List<VoteItem> itemList;
}
