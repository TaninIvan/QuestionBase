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

    // create
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

    public Avatar createAvatar(Avatar avatar) throws Exception {
        try {
            if (userService.get(avatar.getUser().getId()).getAvatar() != null)
                throw new Exception("This user already has avatar! You should use update method.");
        } catch (NullPointerException n) {
            throw new Exception("User with that id do not exist!");
        }
        return avatarRepository.save(avatar);
    }

    // get
    public Avatar get(Long id) {
        return avatarRepository.findById(id).orElse(null);
    }

    public Iterable<Avatar> getAll() {
        return avatarRepository.findAll();
    }

    // update
    public void update(Avatar avatar) {
        avatarRepository.save(avatar);
    }

    // delete
    public void delete(Long id) {
        avatarRepository.deleteById(id);
    }

    public void deleteAll() {
        avatarRepository.deleteAll();
    }
}
