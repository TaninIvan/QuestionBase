package com.ivantanin.questionbase.service;

import com.ivantanin.questionbase.entity.User;
import com.ivantanin.questionbase.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Transactional
    public User createUser(String username, String password, int score){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setScore(score);

        userRepository.save(user);
        System.out.println("I saved user");
        return user;
    }

    public String getUser(Long id) {
        return String.valueOf(userRepository.findById(id).orElse(new User()));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }
}
