package com.example.voto.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@EqualsAndHashCode(callSuper = true)
public class User extends AbstractDomain {

    @Indexed(unique = true)
    private String account;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String name;

    @Setter(AccessLevel.NONE)
    private long ticket;

    public User() {
        ticket = 0;
    }

    public boolean decreaseTicket() {
        if (ticket > 0) {
            ticket--;
            return true;
        }

        return false;
    }
}
