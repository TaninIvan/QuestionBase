package com.ivantanin.questionbase.service;

import com.ivantanin.questionbase.dto.UserDto;
import com.ivantanin.questionbase.entity.Role;
import com.ivantanin.questionbase.entity.User;
import com.ivantanin.questionbase.repository.AnswerRepository;
import com.ivantanin.questionbase.repository.AvatarRepository;
import com.ivantanin.questionbase.repository.UserRepository;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.expression.ParseException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log
public class UserService {

    @Autowired UserRepository userRepository;
    @Autowired ModelMapper modelMapper;
    @Autowired AvatarService avatarService;
    @Autowired AnswerRepository answerRepository;
    @Autowired AvatarRepository avatarRepository;
    @Autowired BCryptPasswordEncoder bCryptPasswordEncoder;

    // create
    public User createUser(String username, String password, Role role, int score) throws Exception {
        if(userRepository.findByUsername(username).isPresent())
            throw new Exception("The user with this nickname already exists.");
        User user = new User();
        user.setUsername(username);
        user.setScore(score);

        user.setRole(role);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        log.fine("New user saved!");
        return userRepository.save(user);
    }

    public User createUser(User newUser) throws Exception {
        if (userRepository.findByUsername(newUser.getUsername()).isPresent())
            throw new Exception("User with nickname "  + newUser.getUsername() + " already exist!");

        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        return userRepository.save(newUser);
    }

    // get
    public User getById(Long id) throws Exception {
        User user = userRepository.findById(id).orElse(null);
        if (user == null)
            throw new Exception("User with id " + id + " does not exist!");
        return user;
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public List<User> getAll() {
        return (userRepository.findAll());
    }

    public List<User> getUserPage(Pageable pageable) {
        return userRepository.findAll(pageable).getContent();
    }

    public List<User> getUsersWithoutAnswers(Pageable pageable) {
        return userRepository.findAllUsersWithoutAnswers(pageable)
                .stream()
                .collect(Collectors.toList());
    }

    public List<User> getUsersByScore(Integer from, Integer to, Pageable pageable) {
        return userRepository.getUsersByScore(from, to, pageable);
    }

    // update
    public User updateUser(User newUser) throws Exception {
        User user = userRepository.findById(newUser.getId()).orElse(null);
        if (user == null)
            throw new Exception("Such user does not exist!");
        return userRepository.save(newUser);
    }

    // delete
    @Transactional
    public void delete(Long id) {
        answerRepository.deleteAllByUserId(id);
        userRepository.deleteById(id);
    }

    @Transactional
    public void delete(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null)
            throw new NullPointerException();
        Long userId = user.getId();
        answerRepository.deleteAllByUserId(userId);
        userRepository.deleteByUsername(username);
    }

    @Transactional
    public void deleteAll() {
        answerRepository.deleteAll();
        avatarRepository.deleteAll();
        userRepository.deleteAll();
    }

    // CONVERTERS
    public UserDto convertToDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        if (user.getAvatar() != null)
            userDto.setAvatarId(user.getAvatar().getAvatar_id());
        userDto.setPassword("");
        return userDto;
    }

    public User convertToEntity(UserDto userDto) throws ParseException {
        User user = modelMapper.map(userDto, User.class);
        if (userDto.getAvatarId() != null)
            user.setAvatar(avatarService.getAvatarById(userDto.getAvatarId()));
        if (userDto.getRole() == null)
            user.setRole(Role.USER);
        return user;
    }
}
