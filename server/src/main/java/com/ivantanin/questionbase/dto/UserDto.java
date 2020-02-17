package com.ivantanin.questionbase.dto;

import com.ivantanin.questionbase.entity.Address;
import com.ivantanin.questionbase.entity.Avatar;
import lombok.Data;
import lombok.ToString;
import javax.validation.constraints.NotNull;

@Data
@ToString
public class UserDto {

    @NotNull
    private Long id;
    @NotNull
    private String username;
    @NotNull
    private String password;
    private Integer score;
    private Avatar avatar;
    private Address[] address;

}
