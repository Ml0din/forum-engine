package com.mladin.forum.repository;

import com.mladin.forum.entity.ForumUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ForumUserRepository extends JpaRepository<ForumUserEntity, String> {
    Optional<ForumUserEntity> findByID(String ID);
    Optional<ForumUserEntity> findByUsername(String username);
    Optional<ForumUserEntity> findByEmail(String email);

    boolean existsByID(String ID);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    @Query(value = "SELECT password FROM `users` WHERE email = :email", nativeQuery = true)
    String findPasswordByEmail(@Param("email") String email);
}
