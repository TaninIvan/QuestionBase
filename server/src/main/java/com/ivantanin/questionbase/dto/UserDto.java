package com.ivantanin.questionbase.dto;

import com.ivantanin.questionbase.entity.Address;
import com.ivantanin.questionbase.entity.Role;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserDto {
    @NotNull private Long id;
    @NotNull private String username;
    @NotNull private String password;
    @NotNull private boolean active;
    private Role role;
    private Integer score;
    private Long avatarId;
    private Address[] address;
}
