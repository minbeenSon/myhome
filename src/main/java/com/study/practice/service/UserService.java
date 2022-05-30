package com.study.practice.service;

import com.study.practice.entity.Role;
import com.study.practice.entity.User;
import com.study.practice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //계정생성
    public void userSave(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword()); //비밀번호 암호화
        user.setPassword(encodedPassword);
        user.setEnabled(true);
        Role role = new Role();
        role.setId(1);
        user.getRoles().add(role);
        userRepository.save(user);
    }
}
