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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    @GetMapping("page")
    @ResponseBody
    public List<AvatarDto> getAvatars(
            @PageableDefault(sort = {"avatar_id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return avatarService.getAll()
                .stream()
                .map(avatarService::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("byUserId/{userId}")
    public ResponseEntity<byte[]> getAvatarByUserId(@PathVariable("userId") Long userId) throws Exception {
        User user = userService.getById(userId);
        Avatar avatar = avatarService.getAvatarById(userId);
        if (avatar == null)
            throw new Exception("User with id " + userId + " does not has a avatar yet!");

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(avatar.getImage());
    }

    // PUT
    @PutMapping("byUserId/{userId}")
    public AvatarDto updateAvatarByUserId(@RequestParam("file") MultipartFile file,
                                          @PathVariable("userId") Long userId) throws Exception {
        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            Avatar avatar = avatarService.getAvatarById(userId);
            User user = userService.getById(userId);

            avatar.setAvatar_id(userId);
            avatar.setImage(bytes);
            user.setAvatar(avatar);

            userService.updateUser(user);
            return avatarService.convertToDto(avatarService.update(avatar));
        }
        return null;
    }

    //  DELETE
    @DeleteMapping("byUserId/{userId}")
    public void deleteAvatarByUserId(@PathVariable("userId") Long userId) throws Exception {
        User user = userService.getById(userId);
        avatarService.delete(userId);
    }
}

