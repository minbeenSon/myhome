package com.study.practice.controller;

import com.study.practice.entity.User;
import com.study.practice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "account/login";
    }

    @GetMapping("/register")
    public String register() {
        return "account/register";
    }

    @PostMapping("/register/process")
    public String registerProcess(User user, Model model) {
        userService.userSave(user);

        model.addAttribute("message", "회원가입완료");
        model.addAttribute("searchUrl", "/");
        return "message";
    }

}
