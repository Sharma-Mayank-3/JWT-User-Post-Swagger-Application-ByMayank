package com.jwt.demo.serviceImpl;

import com.jwt.demo.dto.UserEntityDto;
import com.jwt.demo.entity.RoleEntity;
import com.jwt.demo.entity.UserEntity;
import com.jwt.demo.exception.ResourceNotFoundException;
import com.jwt.demo.repo.RoleRepo;
import com.jwt.demo.repo.UserEntityRepository;
import com.jwt.demo.service.UserEntityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserEntityServiceImpl implements UserEntityService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo repo;

    @Override
    public UserEntityDto createUser(UserEntityDto userEntityDto, int roleId) {
        UserEntity map = this.modelMapper.map(userEntityDto, UserEntity.class);
        String encode = this.passwordEncoder.encode(map.getPassword());
        map.setUserPassword(encode);

        UserEntity save = this.userEntityRepository.save(map);

        RoleEntity roleEntity = this.repo.findById(roleId).get();
        List<UserEntity> userEntities = roleEntity.getUserEntities();
        userEntities.add(save);
        roleEntity.setUserEntities(userEntities);
        this.repo.save(roleEntity);


        return this.modelMapper.map(save, UserEntityDto.class);
    }

    @Override
    public UserEntityDto getUserById(int userId) {
        UserEntity userEntity = this.userEntityRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        return this.modelMapper.map(userEntity, UserEntityDto.class);
    }

    @Override
    public List<UserEntityDto> getAllUsers() {
        List<UserEntity> all = this.userEntityRepository.findAll();
        return all.stream().map(user-> this.modelMapper.map(user, UserEntityDto.class)).collect(Collectors.toList());
    }
}
