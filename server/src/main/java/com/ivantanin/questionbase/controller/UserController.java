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
    public UserDto getUser(@PathVariable("id") Long id){
        return userService.convertToDto(userService.get(id));
    }

    @GetMapping("all")
    public Iterable<User> getAllUsers(){
        return userService.getAll();
    }

    @GetMapping("all/page")
    @ResponseBody
    public List<UserDto> getUsers(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        List<User> users = userService.getUserPage(pageable);
        return users.stream()
                .map(userService::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("filterByScore")
    public List<User> filterByScore(
            @RequestParam(required = false) Optional<Integer> from,
            @RequestParam(required = false) Optional<Integer> to,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable){

        return userService.getUserPage(pageable).stream().filter(user ->
                user.getScore() >= from.orElse(1) && user.getScore() <= to.orElse(1))
                .collect(Collectors.toList());
    }

    @GetMapping("noAnswer")
    public  List<User> getAllUsersWithNoAnswer(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return userService.getUsersWithoutAnswers(pageable);
    }

    // PUT
    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@RequestBody UserDto userDto) throws ParseException {
        User user = userService.convertToEntity(userDto);
        userService.updateUser(user);
    }

    // DELETE
    @DeleteMapping("{id}")
    public String deleteUser(@PathVariable("id") Long id){
        userService.delete(id);
        return "User has deleted!";
    }

    @DeleteMapping("deleteByUsername/{username}")
    public String deleteUser(@PathVariable("username") String username){
        userService.delete(username);
        return "User has deleted!";
    }

    @DeleteMapping("all")
    public String deleteAllUsers(){
        userService.deleteAll();
        return "All users have deleted!";
    }
}

