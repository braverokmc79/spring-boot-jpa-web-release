package com.godcoder.myhome.repository;

import com.godcoder.myhome.model.Board;
import com.godcoder.myhome.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);


    List<User> findByIdOrUsername(Long id, String username);
}
