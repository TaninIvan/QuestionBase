package com.ivantanin.questionbase.service;

import com.ivantanin.questionbase.entity.Avatar;
import com.ivantanin.questionbase.entity.User;
import com.ivantanin.questionbase.repository.AvatarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

@Service
public class AvatarService {

    @Autowired AvatarRepository avatarRepository;
    @Autowired UserService userService;

    private static Logger log = Logger.getLogger(AvatarService.class.getName());

    public Avatar createAvatar(Long userId, String imageURL){
        Avatar avatar = new Avatar();
        User us = userService.get(userId);
        avatar.setUser(us);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try(FileOutputStream fos = new FileOutputStream(imageURL)){
            baos.writeTo(fos);
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
        avatar.setImage(baos.toByteArray());

        us.setAvatar(avatar);
        avatarRepository.save(avatar);
        userService.updateUser(us);
        log.fine("New avatar saved!");
        return avatar;
    }

    public void createAvatar(Avatar avatar){
        avatarRepository.save(avatar);
        log.fine("New avatar saved!");
    }

    public Avatar get(Long id) {
        return avatarRepository.findById(id).orElse(new Avatar());
    }

    public Iterable<Avatar> getAll() {
        return avatarRepository.findAll();
    }

    public void delete(Long id) {
        avatarRepository.deleteById(id);
    }

    public void deleteAll() {
        avatarRepository.deleteAll();
    }
}
