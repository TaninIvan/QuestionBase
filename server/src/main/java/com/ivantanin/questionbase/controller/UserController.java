package com.ivantanin.questionbase.controller;

import com.ivantanin.questionbase.dto.UserDto;
import com.ivantanin.questionbase.entity.User;
import com.ivantanin.questionbase.service.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/user")
@RestController
@Log
public class UserController {

    @Autowired UserService userService;

    // POST
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public UserDto createUser(@RequestBody UserDto userDto) throws Exception {
        User user = userService.convertToEntity(userDto);
        User userCreated = userService.createUser(user);
        return userService.convertToDto(userCreated);
    }

    // GET
    @GetMapping("{id}")
    @ResponseBody
    public UserDto getUser(@PathVariable("id") Long id) throws Exception {
        return userService.convertToDto(userService.getById(id));
    }

    @GetMapping("all")
    public List<UserDto> getAllUsers(){
        return userService.getAll()
                .stream()
                .map(userService::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("all/page")
    @ResponseBody
    public List<UserDto> getUsers(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return userService.getUserPage(pageable).stream()
                .map(userService::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("filterByScore")
    public List<UserDto> filterByScore(
            @RequestParam(required = false) Optional<Integer> from,
            @RequestParam(required = false) Optional<Integer> to,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable){

        return userService.getUserPage(pageable)
                .stream()
                .filter(user -> user.getScore() >= from.orElse(1) && user.getScore() <= to.orElse(1))
                .map(userService::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("noAnswer")
    public  List<UserDto> getAllUsersWithNoAnswer(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return userService.getUsersWithoutAnswers(pageable)
                .stream()
                .map(userService::convertToDto)
                .collect(Collectors.toList());
    }

    // PUT
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@PathVariable Long id, @RequestBody UserDto userDto) throws ParseException {
        User user = userService.convertToEntity(userDto);
        user.setId(id);
        userService.updateUser(user);
    }

    // DELETE
    @DeleteMapping("{id}")
    public String deleteUser(@PathVariable("id") Long id){
        userService.delete(id);
        return "User has deleted!";
    }

    @DeleteMapping("deleteByUsername/{username}")
    public String deleteUser(@PathVariable("username") String username) throws Exception {
        userService.delete(username);
        return "User has deleted!";
    }

    @DeleteMapping("all")
    public String deleteAllUsers(){
        userService.deleteAll();
        return "All users have deleted!";
    }
}

