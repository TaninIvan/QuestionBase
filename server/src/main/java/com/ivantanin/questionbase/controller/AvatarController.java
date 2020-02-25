package com.ivantanin.questionbase.controller;

import com.ivantanin.questionbase.dto.AvatarDto;
import com.ivantanin.questionbase.entity.Avatar;
import com.ivantanin.questionbase.service.AvatarService;
import com.ivantanin.questionbase.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.expression.ParseException;
import org.springframework.web.bind.annotation.*;

@RequestMapping("avatar")
@RestController
public class AvatarController {

    @Autowired AvatarService avatarService;
    @Autowired UserService userService;
    @Autowired ModelMapper modelMapper;

    // POST
    @PostMapping()
    public AvatarDto createAvatar(@RequestBody AvatarDto avatarDto) throws Exception {
        return  convertToDto(avatarService.createAvatar(convertToEntity(avatarDto)));
    }

    // GET
    @GetMapping("all")
    @ResponseBody
    public Iterable<Avatar> getAvatars(@PageableDefault(sort = {"avatar_id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        Iterable<Avatar> avatars = avatarService.getAll();
        avatars.forEach(this::convertToDto);;
        return avatars;
    }

    @GetMapping("{avatar_id}")
    public AvatarDto getAvatar(@PathVariable("avatar_id") Long avatar_id){
        return convertToDto(avatarService.get(avatar_id));
    }

    @GetMapping("byUserId/{userId}")
    public AvatarDto getAvatarByUserId(@PathVariable("userId") Long userId){
        return convertToDto(avatarService.get(userService.get(userId).getAvatar().getAvatar_id()));
    }

    // PUT
    @PutMapping("{avatar_id}")
    public void updateAvatar(@RequestBody AvatarDto avatarDto, @PathVariable("avatar_id") Long avatar_id){
        avatarDto.setAvatar_id(avatar_id);
        avatarService.update(convertToEntity(avatarDto));
    }

    @PutMapping("byUserId/{userId}")
    public void updateAvatarByUserId(@RequestBody AvatarDto avatarDto, @PathVariable("userId") Long userId){
        avatarService.update(convertToEntity(avatarDto));
    }

    //  DELETE
    @DeleteMapping("{avatar_id}")
    public String deleteAvatar(@PathVariable("avatar_id") Long avatar_id){
        avatarService.delete(avatar_id);
        return "Avatar has deleted!";
    }

    @DeleteMapping("byUserId/{userId}")
    public String deleteAvatarByUserId(@PathVariable("userId") Long userId){
        avatarService.delete(userService.get(userId).getAvatar().getAvatar_id());
        return "Avatar has deleted!";
    }

    // CONVERTERS
    private AvatarDto convertToDto(Avatar avatar) {
        AvatarDto avatarDto = modelMapper.map(avatar, AvatarDto.class);
        avatarDto.setUserId(avatar.getAvatar_id());
        return avatarDto;
    }

    private Avatar convertToEntity(AvatarDto avatarDto) throws ParseException {
        Avatar avatar = modelMapper.map(avatarDto, Avatar.class);
        avatar.setUser(userService.get(avatarDto.getUserId()));
        return avatar;
    }

}

