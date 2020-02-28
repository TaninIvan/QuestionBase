package com.ivantanin.questionbase.controller;

import com.ivantanin.questionbase.dto.AvatarDto;
import com.ivantanin.questionbase.service.AvatarService;
import com.ivantanin.questionbase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("avatar")
@RestController
public class AvatarController {

    @Autowired AvatarService avatarService;
    @Autowired UserService userService;

    // POST
    @PostMapping()
    public AvatarDto createAvatar(@RequestBody AvatarDto avatarDto) throws Exception {
        return  avatarService.convertToDto(avatarService.createAvatar(avatarService.convertToEntity(avatarDto)));
    }

    // GET
    @GetMapping("all")
    @ResponseBody
    public List<AvatarDto> getAvatars(@PageableDefault(sort = {"avatar_id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return avatarService.getAll()
                .stream()
                .map(avatarService::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("{avatar_id}")
    public AvatarDto getAvatar(@PathVariable("avatar_id") Long avatar_id){
        return avatarService.convertToDto(avatarService.get(avatar_id));
    }

    @GetMapping("byUserId/{userId}")
    public AvatarDto getAvatarByUserId(@PathVariable("userId") Long userId){
        return avatarService.convertToDto(avatarService.get(userService.getById(userId).getAvatar().getAvatar_id()));
    }

    // PUT
    @PutMapping("{avatar_id}")
    public void updateAvatar(@RequestBody AvatarDto avatarDto, @PathVariable("avatar_id") Long avatar_id){
        avatarDto.setAvatar_id(avatar_id);
        avatarService.update(avatarService.convertToEntity(avatarDto));
    }

    @PutMapping("byUserId/{userId}")
    public void updateAvatarByUserId(@RequestBody AvatarDto avatarDto, @PathVariable("userId") Long userId){
        avatarService.update(avatarService.convertToEntity(avatarDto));
    }

    //  DELETE
    @DeleteMapping("{avatar_id}")
    public String deleteAvatar(@PathVariable("avatar_id") Long avatar_id){
        avatarService.delete(avatar_id);
        return "Avatar has deleted!";
    }

    @DeleteMapping("byUserId/{userId}")
    public String deleteAvatarByUserId(@PathVariable("userId") Long userId){
        avatarService.delete(userService.getById(userId).getAvatar().getAvatar_id());
        return "Avatar has deleted!";
    }
}

