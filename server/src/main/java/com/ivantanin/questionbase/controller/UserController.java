package com.ivantanin.questionbase.controller;

import com.ivantanin.questionbase.dto.UserDto;
import com.ivantanin.questionbase.entity.User;
import com.ivantanin.questionbase.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired UserService userService;
    @Autowired ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public UserDto createUser(@RequestBody UserDto userDto) throws ParseException{
        User user = convertToEntity(userDto);
        User userCreated = userService.createUser(user);
        return convertToDto(userCreated);
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public User getUser(@PathVariable("id") Long id){
        return userService.get(id);
    }

    @GetMapping("/all")
    @ResponseBody
    public List<UserDto> getUsers(
            @PathVariable("page") int page,
            @PathVariable("size") int size,
            @PathVariable("sortDir") String sortDir,
            @PathVariable("sort") String sort) {

        List<User> posts = userService.getUserList(page, size, sortDir, sort);
        return posts.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@RequestBody UserDto userDto) throws ParseException {
        User user = convertToEntity(userDto);
        userService.updateUser(user);
    }

    @DeleteMapping("/{id}" )
    public String deleteUser(@PathVariable("id") Long id){
        userService.delete(id);
        return "Deleted";
    }

    @DeleteMapping("/deleteAll")
    public String deleteAllUsers(){
        userService.deleteAll();
        return "All deleted";
    }

    @GetMapping(value = "/streamTest")
    @ResponseBody
    public List<User> streamTest(
            @PathVariable("from") int from,
            @PathVariable("to") int to){
        return userService.getUserWithScoresBetween(from,to);
    }

    private UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    private User convertToEntity(UserDto userDto) throws ParseException {
        return modelMapper.map(userDto, User.class);
    }

}

