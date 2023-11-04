package com.jwt.demo.service;

import com.jwt.demo.dto.PostEntityDto;

import java.util.List;

public interface PostEntityService {

    PostEntityDto createPost(PostEntityDto postEntityDto, int userId);

    PostEntityDto getPostById(int postId);

    List<PostEntityDto> getAllPost();

    List<PostEntityDto> getPostByUserId(int userId);

}
