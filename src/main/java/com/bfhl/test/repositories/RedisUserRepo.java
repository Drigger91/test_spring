package com.bfhl.test.repositories;



import com.bfhl.test.entities.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.unix.Errors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanResult;

import java.util.*;

@Repository
@Slf4j
public class RedisUserRepo implements UserRepository {
    private final JedisPool jedisPool;
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    public RedisUserRepo( JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Override
    public User findByEmail(String email) {
        try{
            Jedis jedis = jedisPool.getResource();
            String userAsString = jedis.get(email);
            log.info("User is : " + userAsString);
            return changeStringToUser(userAsString);

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String id) {
        return false;
    }

    @Override
    public List<?> findAll() throws JsonProcessingException {
        log.info("In redis");
        List<String> list = new ArrayList<>();
        try{
            Jedis jedis = jedisPool.getResource();
            ScanResult<String> keys =jedis.scan(100000);
            list = keys.getResult();
            List<User> ans = new ArrayList<>();
            for(int i = 0;i<list.size();i++){
                User user = changeStringToUser(jedis.get(list.get(i)));
                ans.add(user);
            }
            return ans;
        }catch(Error e){
            throw new Error("Something went wrong");
        }
    }

    String changeUserToString(User user) throws JsonProcessingException {
        return mapper.writeValueAsString(user);
    }
    @Override
    public User save(User user) throws JsonProcessingException {
        String userToString = changeUserToString(user);

        try{
            Jedis jedis = jedisPool.getResource();
            String str = jedis.set(user.getEmail(),userToString);
            User stringToUser = changeStringToUser(userToString);
            log.info("USER:"+str);
            return stringToUser;
        }catch(Error e){
            throw new Error("JedisPool not configured yet");
        }
    }

    private User changeStringToUser(String value) throws JsonProcessingException {
        return mapper.readValue(value,User.class);
    }

    @Override
    public void delete(User user) {
        try{
            Jedis jedis = jedisPool.getResource();
            jedis.del(user.getEmail());
        }catch (Error e){
            throw new Error("Jedis not configured yet!");
        }
    }
}
