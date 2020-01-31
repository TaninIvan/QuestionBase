package com.ivantanin.questionbase.controller;

import com.ivantanin.questionbase.service.AvatarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@RequestMapping("/user/avatar")
@RestController
public class AvatarController {

    @Autowired
    AvatarService avatarService;

    @GetMapping("save")
    public String saveAvatar(){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try(FileOutputStream fos = new FileOutputStream("C:\\Users\\ivan.tanin\\Desktop\\QuestionBase\\server\\src\\main\\resources\\ava1.jpg")){
            baos.writeTo(fos);
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }

        System.out.println("Save request for user");
        return  avatarService.createAvatar( 1L, baos.toByteArray());
    }

    @GetMapping("read")
    public String readAvatar(){
        return String.valueOf(avatarService.get(1L));
    }

    @GetMapping("delete")
    public String deleteAvatar(){
        avatarService.delete(1L);
        return "User has deleted!";
    }
}

