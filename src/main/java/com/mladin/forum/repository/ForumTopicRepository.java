package com.mladin.forum.repository;

import com.mladin.forum.entity.ForumTopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ForumTopicRepository extends JpaRepository<ForumTopicEntity, String> {
    List<ForumTopicEntity> findAllBySubcategory(String subcategory);

    boolean existsById(String id);
}
