package com.godcoder.myhome.repository;

import com.godcoder.myhome.model.User;

import java.util.List;

interface CustomizedUserRepository {
    List<User> findByUsernameCustom(String username);

    List<User> findByUsernameJDBC(String username);
}