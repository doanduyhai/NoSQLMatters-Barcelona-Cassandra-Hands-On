package com.datastax.demo.service;

import com.datastax.demo.entity.MailBoxEntity;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.mapping.MappingManager;
import org.cassandraunit.CassandraCQLUnit;
import org.cassandraunit.dataset.cql.ClassPathCQLDataSet;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static com.datastax.demo.entity.MailBoxEntity.MessageType;
import static com.datastax.demo.entity.Schema.KEYSPACE;
import static com.datastax.demo.entity.Schema.MAILBOX;
import static com.datastax.driver.core.querybuilder.QueryBuilder.*;
import static org.assertj.core.api.Assertions.assertThat;

public class MailBoxServiceTest {
    @Rule
    public CassandraCQLUnit embeddedCassandra = new CassandraCQLUnit(new ClassPathCQLDataSet("mapper_module.cql"));

    private Session session;
    private MailBoxService service;
    
    @Before
    public void setUp() {
        session = embeddedCassandra.session;
        service = new MappingManager(session).createAccessor(MailBoxService.class);
        session.execute("TRUNCATE mapper_module.mailbox");
    }
    
    @Test
    public void should_create_email() throws Exception {
        //Given
        Date date = new Date();

        //When
        service.createEmail("jdoe", MessageType.MAIN, date, "hsue", "Nice to meet you");

        //Then
        final Row found = session.execute(select().from(KEYSPACE, MAILBOX).where(eq("login", "jdoe")).and(eq("type", MessageType.MAIN.name()))).one();
        assertThat(found).isNotNull();
        assertThat(found.getDate("date")).isEqualTo(date);
        assertThat(found.getString("interlocutor")).isEqualTo("hsue");
        assertThat(found.getString("content")).isEqualTo("Nice to meet you");
    }
    
    @Test
    public void should_fetch_last_emails_from_main_inbox() throws Exception {
        //Given
        final String login = "jdoe";
        final String type = MessageType.MAIN.name();
        final String interlocutor = "hsue";

        final Date date1 = new Date(1L);
        final Date date2 = new Date(2L);
        final Date date3 = new Date(3L);
        final Date date4 = new Date(4L);
        final Date date5 = new Date(5L);

        final PreparedStatement prepared = session.prepare(QueryBuilder.insertInto(KEYSPACE, MAILBOX)
                .value("login", bindMarker("login"))
                .value("type", bindMarker("type"))
                .value("date", bindMarker("date"))
                .value("interlocutor", bindMarker("interlocutor"))
                .value("content", bindMarker("contet")));

        session.execute(prepared.bind(login, type, date1, interlocutor, "Hello there"));
        session.execute(prepared.bind(login, type, date2, interlocutor, "Nice to meet you"));
        session.execute(prepared.bind(login, type, date3, interlocutor, "How do you do ?"));
        session.execute(prepared.bind(login, type, date4, interlocutor, "My name is DOE, John DOE :)"));
        session.execute(prepared.bind(login, type, date5, interlocutor, "My tailor is rich"));

        //When
        final List<MailBoxEntity> mails = service.loadNFirstEmailByType(login, type, 3).all();

        //Then
        assertThat(mails).hasSize(3);
        assertThat(mails.get(0).getDate()).isEqualTo(date5);
        assertThat(mails.get(1).getDate()).isEqualTo(date4);
        assertThat(mails.get(2).getDate()).isEqualTo(date3);
    }
}
