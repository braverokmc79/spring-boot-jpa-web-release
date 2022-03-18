package com.godcoder.myhome.controller;

import com.godcoder.myhome.model.User;
import com.godcoder.myhome.repository.UserRepository;
import com.godcoder.myhome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    UserService userService;


    @GetMapping("/login")
    public String login(){
        return "account/login";
    }


    @GetMapping("/register")
    public String registerForm(){
        return "account/register";
    }


    @PostMapping ("/register")
    public String register(User user, Model model){
        try{
            userService.save(user);
        }catch (Exception e){
            if(e.getMessage().contains("constraint")){
                model.addAttribute( "error" , "이미 등록된 아이디 입니다.");
            }else {
                model.addAttribute("error", e.getMessage());
            }
            return "account/register";
        }
        return "redirect:/";
    }




}
