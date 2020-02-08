package com.ivantanin.questionbase.controller;

import com.ivantanin.questionbase.entity.Avatar;
import com.ivantanin.questionbase.service.AvatarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user/avatar")
@RestController
public class AvatarController {

    @Autowired
    AvatarService avatarService;

    @GetMapping("save")
    public Avatar saveAvatar(){
        return  avatarService.createAvatar( 1L, "src\\main\\resources\\testAvas\\ava1.jpg");
    }

    @GetMapping("read")
    public Avatar readAvatar(){
        return avatarService.get(1L);
    }

    @GetMapping("delete")
    public String deleteAvatar(){
        avatarService.delete(1L);
        return "Avatar has deleted!";
    }
}

