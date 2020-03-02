package com.ivantanin.questionbase.service;

import com.ivantanin.questionbase.dto.AvatarDto;
import com.ivantanin.questionbase.entity.Avatar;
import com.ivantanin.questionbase.entity.User;
import com.ivantanin.questionbase.repository.AvatarRepository;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@Log
public class AvatarService {

    @Autowired AvatarRepository avatarRepository;
    @Autowired UserService userService;
    @Autowired ModelMapper modelMapper;

    // create
    public Avatar createAvatar(Long userId, String imageURL) throws Exception {
        Avatar avatar = new Avatar();
        avatar.setAvatar_id(userId);
        User us = userService.getById(userId);
        avatar.setUser(us);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try(FileOutputStream fos = new FileOutputStream(imageURL)){
            baos.writeTo(fos);
        } catch (IOException ioe) {
            log.warning(ioe.getMessage());
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
            if (getAvatarById(avatar.getAvatar_id()) != null)
                throw new Exception("This user already has avatar! You should use update method.");
        } catch (NullPointerException n) {
            throw new Exception("User with that id do not exist!");
        }
        return avatarRepository.save(avatar);
    }

    // get
    public Avatar getAvatarById(Long id) {
        return avatarRepository.findById(id).orElse(new Avatar());
    }

    public List<Avatar> getAll() {
        return avatarRepository.findAll();
    }

    // update
    public void update(Avatar avatar) {
        avatarRepository.save(avatar);
    }

    // delete
    public void delete(Long id) throws Exception {
        User user = userService.getById(id);
        user.setAvatar(null);
        userService.updateUser(user);
        avatarRepository.deleteById(id);
    }

    public void deleteAll() {
        avatarRepository.deleteAll();
    }

    // CONVERTERS
    public AvatarDto convertToDto(Avatar avatar) {
        AvatarDto avatarDto = modelMapper.map(avatar, AvatarDto.class);
        return avatarDto;
    }

    public Avatar convertToEntity(AvatarDto avatarDto) throws ParseException {
        Avatar avatar = modelMapper.map(avatarDto, Avatar.class);
        return avatar;
    }
}
