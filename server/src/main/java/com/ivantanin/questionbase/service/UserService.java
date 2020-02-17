package com.ivantanin.questionbase.service;

import com.ivantanin.questionbase.entity.User;
import com.ivantanin.questionbase.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired UserRepository userRepository;
    private static Logger log = Logger.getLogger(UserService.class.getName());

    // create
    public User createUser(String username, String password, int score) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setScore(score);
        log.fine("New user saved!");
        return user;
    }

    public User createUser(User newUser) {
        return userRepository.save(newUser);
    }

    // get
    public User get(Long id) {
        return userRepository.findById(id).orElse(new User());
    }

    public Iterable<User> getAll() {
        return (userRepository.findAll());
    }

    public List<User> getUserList(Pageable pageable) { ;
        Page<User> users = userRepository.findAll(pageable);
        return users.getContent();
    }

    public List<User> getUsersWithoutAnswers(Pageable pageable) {
        return userRepository.findAllUsersWithoutAnswers(pageable).stream().collect(Collectors.toList());
    }

    // update
    public void updateUser(User newUser) {
        userRepository.save(newUser);
    }

    // delete
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }
}
