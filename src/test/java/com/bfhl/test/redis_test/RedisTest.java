package com.bfhl.test.redis_test;

import com.bfhl.test.entities.User;
import com.bfhl.test.repositories.RedisUserRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fppt.jedismock.RedisServer;
import com.github.fppt.jedismock.server.ServiceOptions;
import com.github.incu6us.redis.mock.EnableRedisMockTemplate;
import org.junit.Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
public class RedisTest {
    RedisServer server = RedisServer.newRedisServer().start();
    Jedis jedis = new Jedis(server.getHost(), server.getBindPort());
    private RedisUserRepo redisUserRepo;

    public RedisTest() throws IOException {
    }
    @BeforeEach
    public void init() throws IOException {
        RedisServer server = RedisServer.newRedisServer().start();
        Jedis jedis = new Jedis(server.getHost(),server.getBindPort());
    }
    @Test
    public void canJedisSetValue(){
        assertEquals(jedis.set("key1", "value1"), "OK");
    }
    @Test
    public void canJedisGetValue(){
        jedis.set("key","value");
        jedis.set("key1","value1");
        assertEquals("value1",jedis.get("key1"));
    }
    @Test
    public void canRedisFetchUser() throws JsonProcessingException {
        User user = new User("id","name","pass","mail");
        redisUserRepo.save(user);
        assertThat(redisUserRepo.findAll()).isNotNull();
    }
    @Test
    public void canRedisSaveUser() throws JsonProcessingException {
        User user = new User("id","name","pass","mail");
        assertThat(redisUserRepo.save(user)).isNull();
    }
}
