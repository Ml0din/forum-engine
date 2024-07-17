package com.mladin.forum.repository;

import com.mladin.forum.entity.ForumGroupEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ForumGroupRepository extends JpaRepository<ForumGroupEntity, String> {
    Optional<ForumGroupEntity> findById(String id);
    Optional<ForumGroupEntity> findByName(String name);

    List<ForumGroupEntity> findAll();

    boolean existsByName(String name);

    @Transactional
    @Query(value = "update `groups` SET authorities = :authorities WHERE name = :name", nativeQuery = true)
    @Modifying
    void setAuthoritiesByName(@Param("name") String name, @Param("authorities") byte[] authorities);
}
