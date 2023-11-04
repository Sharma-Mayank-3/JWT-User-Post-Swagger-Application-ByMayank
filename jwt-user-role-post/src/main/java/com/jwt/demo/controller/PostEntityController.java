package com.jwt.demo.controller;

import com.jwt.demo.dto.PostEntityDto;
import com.jwt.demo.payload.ApiResponse;
import com.jwt.demo.service.PostEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostEntityController {

    @Autowired
    private PostEntityService postEntityService;

    @PostMapping("/user/{userId}/post/")
    public ResponseEntity<ApiResponse> createPost(@RequestBody PostEntityDto postEntityDto,
                                                  @PathVariable("userId") int userId
                                                  ){
        PostEntityDto post = this.postEntityService.createPost(postEntityDto, userId);
        ApiResponse build = ApiResponse.builder().controllerName("PostEntityController").status(true).data(post).message("Post-Created").build();
        return new ResponseEntity<>(build, HttpStatus.CREATED);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<ApiResponse> getPostById(@PathVariable("postId") int postId){
        PostEntityDto postById = this.postEntityService.getPostById(postId);
        ApiResponse build = ApiResponse.builder().controllerName("PostEntityController").status(true).data(postById).message("Post ById").build();
        return new ResponseEntity<>(build, HttpStatus.OK);
    }

    @GetMapping("/post/")
    public ResponseEntity<ApiResponse> getAlPosts(){
        List<PostEntityDto> allPost = this.postEntityService.getAllPost();
        ApiResponse build = ApiResponse.builder().controllerName("PostEntityController").status(true).data(allPost).message("All Post").build();
        return new ResponseEntity<>(build, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/post")
    public ResponseEntity<ApiResponse> getPostByUserId(@PathVariable("userId") int userId){
        List<PostEntityDto> allPost = this.postEntityService.getPostByUserId(userId);
        ApiResponse build = ApiResponse.builder().controllerName("PostEntityController").status(true).data(allPost).message("All Post byUser").build();
        return new ResponseEntity<>(build, HttpStatus.OK);
    }

}
