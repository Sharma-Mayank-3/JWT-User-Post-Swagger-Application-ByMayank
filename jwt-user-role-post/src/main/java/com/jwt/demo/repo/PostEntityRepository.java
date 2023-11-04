package com.jwt.demo.repo;

import com.jwt.demo.entity.PostEntity;
import com.jwt.demo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostEntityRepository extends JpaRepository<PostEntity, Integer> {

    List<PostEntity> findByUserEntity(UserEntity userEntity);

}
