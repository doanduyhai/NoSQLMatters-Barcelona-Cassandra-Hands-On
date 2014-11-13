package com.datastax.demo.entity;

import com.datastax.driver.mapping.EnumType;
import com.datastax.driver.mapping.annotations.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailBoxEntity {

    private String login;

    @Enumerated(EnumType.STRING)
    private MessageType type;

    private Date date;

    private String interlocutor;

    private String content;

    public static enum MessageType {
        MAIN, FORUM, PROMOTION, SOCIAL;
    }
}
