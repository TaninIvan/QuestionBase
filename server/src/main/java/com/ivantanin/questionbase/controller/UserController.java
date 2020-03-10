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
import org.springframework.web.server.ResponseStatusException;

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
    public UserDto createUser(@RequestBody UserDto userDto) {
        User user = userService.convertToEntity(userDto);
        try {
            User userCreated = userService.createUser(user);
            return userService.convertToDto(userCreated);
        } catch (Exception e){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage(),e);
        }
    }

    // GET
    @GetMapping("{id}")
    @ResponseBody
    public UserDto getUser(@PathVariable("id") Long id) throws Exception {
        try {
            return userService.convertToDto(userService.getById(id));
        } catch (Exception e){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage(),e);
        }
    }

    @GetMapping("page")
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

        return userService.getUsersByScore(from.orElse(1), to.orElse(100), pageable)
                .stream()
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
    public UserDto updateUser(@PathVariable Long id, @RequestBody UserDto userDto) throws ParseException {

        try {
            User user = userService.convertToEntity(userDto);
            user.setId(id);
            return userService.convertToDto(userService.updateUser(user));
        } catch (Exception e){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage(),e);
        }
    }

    // DELETE
    @DeleteMapping("{id}")
    public String deleteUser(@PathVariable("id") Long id){
        try {
            userService.delete(id);
            return "User has deleted!";
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User with id " + id + " does not exist", e);
        }

    }

    @DeleteMapping("deleteByUsername/{username}")
    public String deleteUser(@PathVariable("username") String username){
        try {
            userService.delete(username);
            return "User has deleted!";
        } catch (NullPointerException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User with username " + username + " does not exist", e);
        }
    }

    @DeleteMapping("all")
    public String deleteAllUsers(){
        userService.deleteAll();
        return "All users have deleted!";
    }
}

