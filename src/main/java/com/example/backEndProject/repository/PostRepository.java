package com.example.backEndProject.repository;

import com.example.backEndProject.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "SELECT * FROM posts WHERE id = ?",nativeQuery = true)
    Post findPostByID(Long id);

    @Query(value = "UPDATE posts SET number_of_likes = newLikeCount",nativeQuery = true)
    Post addLikeToPost(Long id);
}
