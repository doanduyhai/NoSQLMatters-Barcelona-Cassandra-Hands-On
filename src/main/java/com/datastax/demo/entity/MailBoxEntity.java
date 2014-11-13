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
@Table(keyspace = Schema.KEYSPACE, name = Schema.MAILBOX)
public class MailBoxEntity {

    @PartitionKey(0)
    private String login;

    @PartitionKey(1)
    @Enumerated(EnumType.STRING)
    private MessageType type;

    @ClusteringColumn(0)
    private Date date;

    private String interlocutor;

    private String content;


    public static enum MessageType {
        MAIN, FORUM, PROMOTION, SOCIAL;
    }
}
