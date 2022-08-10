package com.bfhl.test.redistest;

import com.bfhl.test.entities.User;
import com.bfhl.test.repositories.RedisUserRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fppt.jedismock.RedisServer;
import org.junit.Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@RunWith(SpringRunner.class)
public class RedisTest {
    RedisServer server = RedisServer.newRedisServer().start();
    Jedis jedis = new Jedis(server.getHost(), server.getBindPort());
    JedisPoolConfig config = new JedisPoolConfig();
    private JedisPool jedisPool = new JedisPool(config,"localhost",6379,1000,"changeit");


    private final RedisUserRepo redisUserRepo  = new RedisUserRepo(jedisPool);

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
        assertThat(redisUserRepo.save(user)).isEqualTo(user);
    }
}
