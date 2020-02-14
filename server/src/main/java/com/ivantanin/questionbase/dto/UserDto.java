package com.ivantanin.questionbase.dto;

import com.ivantanin.questionbase.entity.Address;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@ToString
public class UserDto {

    @NotNull
    private Long id;
    private String username;
    private String password;
    private Integer score;
    private Long avatar;
    private Address[] address;

}
