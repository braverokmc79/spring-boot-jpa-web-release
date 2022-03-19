package com.godcoder.myhome.repository;

import com.godcoder.myhome.model.QUser;
import com.godcoder.myhome.model.User;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class CustomizedUserRepositoryImpl implements CustomizedUserRepository {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public  List<User> findByUsernameCustom(String username) {
        QUser qUser = QUser.user;
        JPAQuery<?> query = new JPAQuery<Void>(em);
        List<User> users = query.select(qUser)
                .from(qUser)
                .where(qUser.username.contains(username))
                .fetch();
        return users;
    }

    @Override
    public List<User> findByUsernameJDBC(String username) {
        String sql =" SELECt * FROM USER WHERE username like concat('%', ? , '%')  " ;

        List<User> users=(List<User>)jdbcTemplate.query(sql,
                new Object[]{username},
                new BeanPropertyRowMapper<>(User.class));
        return users;
    }





}
