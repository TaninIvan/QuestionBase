package com.ivantanin.questionbase.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@ToString
public class AvatarDto {

    @NotNull
    private Long avatar_id;
    private byte[] image;
    private Long userId;

}