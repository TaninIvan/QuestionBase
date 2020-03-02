package com.ivantanin.questionbase.controller;

import com.ivantanin.questionbase.dto.AvatarDto;
import com.ivantanin.questionbase.entity.Avatar;
import com.ivantanin.questionbase.entity.User;
import com.ivantanin.questionbase.service.AvatarService;
import com.ivantanin.questionbase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("avatar")
@RestController
public class AvatarController {

    @Autowired AvatarService avatarService;
    @Autowired UserService userService;

    // GET
    @GetMapping("all")
    @ResponseBody
    public List<AvatarDto> getAvatars(
            @PageableDefault(sort = {"avatar_id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return avatarService.getAll()
                .stream()
                .map(avatarService::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("byUserId/{userId}")
    public byte[] getAvatarByUserId(@PathVariable("userId") Long userId) throws Exception {
        User user = userService.getById(userId);
        Avatar avatar = avatarService.getAvatarById(userId);
        if (avatar == null)
            throw new Exception("User with id " + userId + " does not has a avatar yet!");
        //return avatarService.convertToDto(avatar);
       return avatar.getImage();
    }

    // PUT
    /*
    @PutMapping("byUserId/{userId}")
    public void updateAvatarByUserId(@RequestBody AvatarDto avatarDto,
                                     @PathVariable("userId") Long userId) throws Exception {
        User user = userService.getById(userId);

        avatarDto.setAvatar_id(userId);
        Avatar avatar = avatarService.convertToEntity(avatarDto);
        user.setAvatar(avatar);

        userService.updateUser(user);
        avatarService.update(avatar);
    } */

    // PUT
    @PutMapping("byUserId/{userId}")
    public void updateAvatarByUserId(@RequestParam("file") MultipartFile file,
                                     @PathVariable("userId") Long userId) throws Exception {
        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            Avatar avatar = avatarService.getAvatarById(userId);
            User user = userService.getById(userId);

            avatar.setAvatar_id(userId);
            avatar.setImage(bytes);
            user.setAvatar(avatar);

            userService.updateUser(user);
            avatarService.update(avatar);
    }


    }

    //  DELETE
    @DeleteMapping("byUserId/{userId}")
    public String deleteAvatarByUserId(@PathVariable("userId") Long userId) throws Exception {
        User user = userService.getById(userId);
        avatarService.delete(userId);
        return "Avatar has deleted!";
    }
}

