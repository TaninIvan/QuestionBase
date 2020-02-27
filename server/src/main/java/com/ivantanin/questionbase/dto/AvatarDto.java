package com.ivantanin.questionbase.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AvatarDto {

    @NotNull
    private Long avatar_id;
    private byte[] image;
    private Long userId;

}
