package com.godcoder.myhome.controller;

import com.godcoder.myhome.model.Board;
import com.godcoder.myhome.model.QUser;
import com.godcoder.myhome.model.User;
import com.godcoder.myhome.repository.UserRepository;
import com.godcoder.myhome.repository.UserRepository;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityExistsException;
import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserApiController {


    private final UserRepository repository;

    UserApiController(UserRepository repository) {
        this.repository = repository;
    }


    @GetMapping("/users2")
    Iterable<User> findUserQuery(@RequestParam(required = false, defaultValue = "") String method,
                             @RequestParam(required = false) String text
        ){

        Iterable<User> users=null;
        if("query".equals(method)){
             users=repository.findByUsernameQuery(text);
        }else if("nativeQuery".equals(method)){
            users=repository.findByUsernameNativedQuery(text);
        }else if("querydsl".equals(method)){

//            QCustomer customer = QCustomer.customer;
//            JPAQuery<?> query = new JPAQuery<Void>(entityManager);
//            Customer bob = query.select(customer)
//                    .from(customer)
//                    .where(customer.firstName.eq("Bob"))
//                    .fetchOne();
//
//            Predicate predicate=users
            log.info("querydsl  검색 ");
            QUser user =QUser.user;
            BooleanExpression b=user.username.contains(text);
            if(true){
                b=b.and(user.username.eq("HI"));
            }

//            Predicate predicate =user.username.contains(text);
//            users=repository.findAll(predicate);

        }else if("querydslCustom".equals(method)){
            log.info("querydslCustom  검색 ");
            users=repository.findByUsernameCustom(text);


        }else if("querydslJDBC".equals(method)){
            log.info("querydslJDBC  검색 ");
            users=repository.findByUsernameJDBC(text);

        }else{
            users=repository.findAll();
        }
        return  users;
    }



    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/users")
    List<User> all(@RequestParam(required = false, defaultValue = "") Long id
            , @RequestParam(required = false, defaultValue = "") String username) {

//        if( StringUtils.isEmpty(username)){
//            return repository.findAll();
//        }else{
//            return repository.findByIdOrUsername(id, username);
//        }

        List<User> users =repository.findAll();
        log.info("getBoserd() 호출전");
        log.info("getBoards().size()  -  {} :" , users.get(0).getBoards().size() );
        //log.info("getBoards().size()  제목 0 -  {} :" , users.get(0).getBoards().get(0).getTitle() );
        log.info("getBoserd()  호출후");
        return users;
    }



    // end::get-aggregate-root[]

    @PostMapping("/users")
    User newUser(@RequestBody User newUser) {
        return repository.save(newUser);
    }

    @GetMapping("/users/{id}")
    User one(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(EntityExistsException::new);
    }

    @PutMapping("/users/{id}")
    User replaceUser(@RequestBody User newUser, @PathVariable Long id) {

        return repository.findById(id)
                .map(user -> {
             //       user.setUsername(newUser.getUsername());
                    //user.setBoards(newUser.getBoards());
                    user.getBoards().clear();
                    user.getBoards().addAll(newUser.getBoards());

                    for(Board board : user.getBoards()){
                        board.setUser(user);
                    }
                    return repository.save(user);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return repository.save(newUser);
                });
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id) {
        repository.deleteById(id);
    }




}
