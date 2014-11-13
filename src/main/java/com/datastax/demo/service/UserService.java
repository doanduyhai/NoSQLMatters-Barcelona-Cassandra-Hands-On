package com.datastax.demo.service;

import com.datastax.demo.entity.UserEntity;
import com.datastax.driver.mapping.Mapper;

public class UserService {

    Mapper<UserEntity> mapper;

    public void create(String login, String name, int age){
        mapper.save(new UserEntity(login, name, age));
    }

    public UserEntity find(String login) {
        return mapper.get(login);
    }

    public void delete(String login) {
        mapper.delete(login);
    }
}
