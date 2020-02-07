package com.ivantanin.questionbase.service;

import com.ivantanin.questionbase.entity.Avatar;
import com.ivantanin.questionbase.entity.User;
import com.ivantanin.questionbase.repository.AvatarRepository;
import com.ivantanin.questionbase.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired UserRepository userRepository;
    @Autowired AvatarRepository avatarRepository;

    public User createUser(String username, String password, int score){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setScore(score);

        userRepository.save(user);
        System.out.println("I saved new user");
        return user;
    }

    public void createUser(User newUser){
        userRepository.save(newUser);
    }

    public User get(Long id) {
        return userRepository.findById(id).orElse(new User());
    }

    public Iterable<User> getAll() {
        return (userRepository.findAll());
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

    public void addAvatar(Long userId, Long avatarId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Avatar> avatar = avatarRepository.findById(avatarId);

        user.get().setAvatar(avatar.get());
        avatar.get().setUser(user.get());

        userRepository.save(user.get());
    }
}
