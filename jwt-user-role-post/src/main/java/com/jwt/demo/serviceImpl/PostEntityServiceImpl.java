package com.jwt.demo.serviceImpl;

import com.jwt.demo.dto.PostEntityDto;
import com.jwt.demo.entity.PostEntity;
import com.jwt.demo.entity.UserEntity;
import com.jwt.demo.exception.ResourceNotFoundException;
import com.jwt.demo.repo.PostEntityRepository;
import com.jwt.demo.repo.UserEntityRepository;
import com.jwt.demo.service.PostEntityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostEntityServiceImpl implements PostEntityService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PostEntityRepository postEntityRepository;

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Override
    public PostEntityDto createPost(PostEntityDto postEntityDto, int userId) {
        PostEntity map = this.modelMapper.map(postEntityDto, PostEntity.class);
        UserEntity userEntity = this.userEntityRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "userId", userId));
        map.setUserEntity(userEntity);
        PostEntity save = this.postEntityRepository.save(map);
        return this.modelMapper.map(save, PostEntityDto.class);
    }

    @Override
    public PostEntityDto getPostById(int postId) {
        PostEntity postEntity = this.postEntityRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "postId", postId));
        return this.modelMapper.map(postEntity, PostEntityDto.class);
    }

    @Override
    public List<PostEntityDto> getAllPost() {
        List<PostEntity> all = this.postEntityRepository.findAll();
        return all.stream().map(post-> this.modelMapper.map(post, PostEntityDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<PostEntityDto> getPostByUserId(int userId) {
        UserEntity userEntity = this.userEntityRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "userId", userId));
        List<PostEntity> byUserEntity = this.postEntityRepository.findByUserEntity(userEntity);
        return byUserEntity.stream().map(post-> this.modelMapper.map(post, PostEntityDto.class)).collect(Collectors.toList());
    }
}
