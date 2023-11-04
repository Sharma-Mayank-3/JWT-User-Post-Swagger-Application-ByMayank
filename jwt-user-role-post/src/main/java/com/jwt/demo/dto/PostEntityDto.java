package com.jwt.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostEntityDto {

    private int postId;
    private String postDescription;
    private UserEntityDto userEntity;
}
