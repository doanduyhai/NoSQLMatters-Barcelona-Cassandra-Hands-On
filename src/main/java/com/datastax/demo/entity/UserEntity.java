package com.datastax.demo.entity;

import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(keyspace = Schema.KEYSPACE, name = Schema.USERS)
public class UserEntity {

    @PartitionKey
    private String login;

    private String name;

    private Integer age;

}
