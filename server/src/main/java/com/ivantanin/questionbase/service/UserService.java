package com.ivantanin.questionbase.service;

import com.ivantanin.questionbase.entity.Avatar;
import com.ivantanin.questionbase.entity.User;
import com.ivantanin.questionbase.repository.AvatarRepository;
import com.ivantanin.questionbase.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AvatarRepository avatarRepository;

    @Transactional
    public User createUser(String username, String password, int score){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setScore(score);

        userRepository.save(user);
        System.out.println("I saved new user");
        return user;
    }

    public User get(Long id) {
        return userRepository.findById(id).orElse(new User());
    }

    public String getAll() {
        return String.valueOf(userRepository.findAll());
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
