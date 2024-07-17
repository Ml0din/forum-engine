package com.mladin.forum.repository;

import com.mladin.forum.entity.ForumCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ForumCategoryRepository extends JpaRepository<ForumCategoryEntity, String> {
    List<ForumCategoryEntity> findAll();
}
