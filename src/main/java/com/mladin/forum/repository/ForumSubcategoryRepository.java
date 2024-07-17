package com.mladin.forum.repository;

import com.mladin.forum.entity.ForumSubcategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ForumSubcategoryRepository extends JpaRepository<ForumSubcategoryEntity, String> {
    List<ForumSubcategoryEntity> findByCategory(String category);

    boolean existsById(String id);
}
