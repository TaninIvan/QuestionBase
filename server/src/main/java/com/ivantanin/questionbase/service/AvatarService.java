package com.ivantanin.questionbase.service;

import com.ivantanin.questionbase.entity.Avatar;
import com.ivantanin.questionbase.entity.User;
import com.ivantanin.questionbase.repository.AvatarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AvatarService {

    @Autowired
    AvatarRepository avatarRepository;

    @Autowired
    UserService userService;

    @Transactional
    public String createAvatar(Long userId, byte[] image){
        Avatar avatar = new Avatar();
        User us = userService.get(userId);
        avatar.setUser(us);
        avatar.setImage(image);
        us.setAvatar(avatar);
        avatarRepository.save(avatar);
        System.out.println("I saved new avatar");
        return "Avatar has saved!";
    }

    public String get(Long id) {
        return String.valueOf(avatarRepository.findById(id).orElse(new Avatar()));
    }

    public String getAll() {
        return String.valueOf(avatarRepository.findAll());
    }

    public void delete(Long id) {
        avatarRepository.deleteById(id);
    }

    public void deleteAll() {
        avatarRepository.deleteAll();
    }
}
