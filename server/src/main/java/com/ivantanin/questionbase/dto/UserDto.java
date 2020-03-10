package com.ivantanin.questionbase.dto;

import com.ivantanin.questionbase.entity.Address;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserDto {

    @NotNull
    private Long id;
    @NotNull
    private String username;
    @NotNull
    private String password;
    private Integer score;
    private Long avatarId;
    private Address[] address;

}
