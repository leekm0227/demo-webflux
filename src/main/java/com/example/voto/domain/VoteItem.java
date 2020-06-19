package com.example.voto.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
@EqualsAndHashCode(callSuper = true)
public class VoteItem extends AbstractDomain {
    private String subject;
    private long count;

    VoteItem() {
        setId(new ObjectId().toString());
        setCreateAt(LocalDateTime.now());
        setModifyAt(LocalDateTime.now());
        setCount(0);
    }

    public long increaseCount() {
        return count++;
    }
}
