package com.example.back.Service;

import com.example.back.Model.PostM;
import com.example.back.Repository.Impl.PostRImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PostS {

    private PostRImpl repository;

    public List<PostM> getAllPosts() {
        return repository.getAllPosts();
    }

    public PostM getPostById(Long id) {
        return repository.getPostById(id);
    }

    public PostM createPost(PostM post) {
        return repository.createPost(post);
    }

    public Boolean updatePost(String description ,Long postId) {
        return repository.updatePost(description,postId);
    }

    public Boolean deletePost(Long id) {
        return repository.deletePost(id);
    }
}
