package com.example.back.Service;

import com.example.back.Model.CommentM;
import com.example.back.Repository.Impl.CommentsRImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentsS {
    private CommentsRImpl repository;

    public List<CommentM> getCommentsByPostId(Long id){
        return repository.getListCommentByPostId(id);
    }

    public CommentM addComment(CommentM comment){
        return repository.createComment(comment);
    }

    public CommentM getCommentById(Long id){
        return repository.getCommentById(id);
    }

    public boolean updateCommentById(CommentM content,Long id,Long postId){
        return repository.updateCommentById(content,id,postId);
    }

    public boolean deleteCommentById(Long id){
        return repository.deleteCommentById(id);
    }
}
