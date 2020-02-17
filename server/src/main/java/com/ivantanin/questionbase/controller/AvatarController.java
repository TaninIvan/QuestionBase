package com.ivantanin.questionbase.controller;

import com.ivantanin.questionbase.entity.Avatar;
import com.ivantanin.questionbase.service.AvatarService;
import com.ivantanin.questionbase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/{userId}/avatar")
@RestController
public class AvatarController {

    @Autowired AvatarService avatarService;
    @Autowired UserService userService;

    // POST
    @PutMapping("/{avatarURL}")
    public Avatar createAvatar(@PathVariable("userId") Long userId, @PathVariable("avatarURL") String avatarURL){
        return  avatarService.createAvatar( userId, avatarURL);
    }

    // GET
    @GetMapping()
    public Avatar getAvatar(@PathVariable("userId") Long userId){
        return avatarService.get(userService.get(userId).getAvatar().getAvatar_id());
    }

    //  DELETE
    @DeleteMapping("/{id}")
    public String deleteAvatar(@PathVariable("id") Long id){
        avatarService.delete(id);
        return "Avatar has deleted!";
    }
}

