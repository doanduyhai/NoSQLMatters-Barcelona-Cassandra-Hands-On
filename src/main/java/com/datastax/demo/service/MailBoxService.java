package com.datastax.demo.service;

import com.datastax.demo.entity.MailBoxEntity;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Param;
import com.datastax.driver.mapping.annotations.Query;

import java.util.Date;

@Accessor
public interface MailBoxService {

    @Query("INSERT INTO mapper_module.mailbox(login,type,date,interlocutor,content) VALUES(:login,:type,:date,:interlocutor,:content)")
    public ResultSet createEmail(@Param("login") String login,
                            @Param("type")MailBoxEntity.MessageType type,
                            @Param("date") Date date,
                            @Param("interlocutor") String interlocutor,
                            @Param("content") String content);

    @Query("SELECT * FROM mapper_module.mailbox WHERE login=:login AND type=:type LIMIT :lim")
    public Result<MailBoxEntity> loadNFirstEmailByType(@Param("login") String login,
                                                       @Param("type") String type,
                                                       @Param("lim") int limit);
}
