package com.bfhl.test.mongotest;

import com.bfhl.test.entities.User;
import com.bfhl.test.repositories.MongoRepo;
import com.mongodb.client.MongoClients;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.ImmutableMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@DataMongoTest
@ExtendWith(SpringExtension.class)
public class MongoTest {
    private static MongodExecutable mongodExecutable;
    private static MongoTemplate mongoTemplate;
    private static MongoRepo repository;
    @AfterAll
    static void clean() {
        mongodExecutable.stop();
    }
    @AfterEach
    void clearDB(){
        mongoTemplate.dropCollection("Test");
    }
    @BeforeAll
    static void setup() throws Exception {
        String ip = "localhost";
        int port = 27019;

        ImmutableMongodConfig mongodConfig = MongodConfig.builder()
                .version(Version.V3_2_20)
                .net(new Net(ip, port, Network.localhostIsIPv6()))
                .build();

        MongodStarter starter = MongodStarter.getDefaultInstance();
        mongodExecutable = starter.prepare(mongodConfig);
        mongodExecutable.start();
        mongoTemplate = new MongoTemplate(MongoClients.create("mongodb://localhost:27019"), "Test");
        repository = new MongoRepo(mongoTemplate);
    }

    @Test
    public void saveUsers() {
        User user = new User("id", "name", "pass", "me@me.com");
        Assertions.assertEquals(user, repository.save(user));
    }
    @Test
    public void getUsers(){
        User user1 = new User("id2", "name", "pass", "me@me2.com");
        User user2 = new User("id", "name", "pass", "me@me1.com");
        repository.save(user1);
        repository.save(user2);
        List<User> list = new ArrayList<>();
        list.add(user1);
        list.add(user2);
        Assertions.assertEquals(2, repository.findAll().size());
    }
    @Test
    public void findByEmail(){
        User user1 = new User("id2", "name", "pass", "me@me2.com");
        repository.save(user1);
        Assertions.assertEquals(user1,repository.findByEmail(user1.getEmail()));

    }

}
