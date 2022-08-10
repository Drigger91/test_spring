package com.bfhl.test.repositories;

import com.bfhl.test.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;


@Repository
public class MongoRepo implements UserRepository{
    private final MongoOperations template;
    @Autowired
    public MongoRepo(MongoTemplate template) {
        this.template = template;
    }


    @Override
    public User findByEmail(String email) {
        Query q = new Query();
        q.addCriteria(Criteria.where("email").is(email));
        List<User> list = template.find(q,User.class,"User");
        return list.size() == 0 ? null : list.get(0);
    }

    @Override
    public Optional<User> findById(String id) {
        Query q = new Query();
        q.addCriteria(Criteria.where("_id").is(id));
        List<User> users =  template.find(q,User.class,"User");
        if(users.size() == 0){
            return null;
        }
        return Optional.ofNullable(users.get(0));
    }

    @Override
    public boolean existsById(String id) {
        Query q = new Query();
        q.addCriteria(Criteria.where("_id").is(id));
        return template.exists(q,"User");
    }

    @Override
    public List<?> findAll() {
        return template.findAll(User.class,"User");
    }

    @Override
    public User save(User user) {
        return template.save(user,"User");
    }

    @Override
    public void delete(User user) {
        Query q = new Query();
        q.addCriteria(Criteria.where("email").is(user.getEmail()));
        template.findAndRemove(q,User.class,"User");
    }
}
