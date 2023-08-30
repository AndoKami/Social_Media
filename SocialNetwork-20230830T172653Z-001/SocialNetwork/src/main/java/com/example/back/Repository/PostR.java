package com.example.back.Repository;

import com.example.back.Model.PostM;

import java.util.List;

public interface PostR {
    PostM createPost(PostM post);

    PostM getPostById(Long postId);

    List<PostM> getAllPosts();

    Boolean updatePost(String description , Long id);

    Boolean deletePost(Long postId);
}