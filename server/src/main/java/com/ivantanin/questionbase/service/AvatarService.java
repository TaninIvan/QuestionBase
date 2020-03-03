package com.ivantanin.questionbase.service;

import com.ivantanin.questionbase.dto.AvatarDto;
import com.ivantanin.questionbase.entity.Avatar;
import com.ivantanin.questionbase.entity.User;
import com.ivantanin.questionbase.repository.AnswerRepository;
import com.ivantanin.questionbase.repository.AvatarRepository;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

@Service
@Log
public class AvatarService {

    @Autowired AvatarRepository avatarRepository;
    @Autowired UserService userService;
    @Autowired ModelMapper modelMapper;
    @Autowired AnswerRepository answerRepository;

    // create
    public Avatar createAvatar(Long userId, File file) throws Exception {
        Avatar avatar = new Avatar();
        avatar.setAvatar_id(userId);
        User us = userService.getById(userId);
        avatar.setUser(us);

        BufferedImage originalImage = ImageIO.read(file);

        // convert image to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(originalImage, "jpg", baos);
        baos.flush();
        avatar.setImage(baos.toByteArray());
        baos.close();


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
        return avatarRepository.findById(id).orElse(null);
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
        if(avatarRepository.existsById(id))
            throw new Exception("Avatar with id " + id + " does not exist!");
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

    public ResponseEntity<byte[]> convertToResponseEntity(Avatar avatar) {
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(avatar.getImage());
    }
}
