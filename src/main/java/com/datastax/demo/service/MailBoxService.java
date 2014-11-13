package com.datastax.demo.service;

import com.datastax.demo.entity.MailBoxEntity;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.mapping.Result;


import java.util.Date;

public interface MailBoxService {

    public ResultSet createEmail(String login,
                                MailBoxEntity.MessageType type,
                                Date date,
                                String interlocutor,
                                String content);

    // Load first N email by type
    public Result<MailBoxEntity> loadNFirstEmailByType(String login,
                                                       String type,
                                                       int limit);
}
