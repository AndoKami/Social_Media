package com.example.back.Repository;

import com.example.back.Model.CommentM;

import java.util.List;

public interface CommentsR {
    List<CommentM> getListCommentByPostId(Long id);
    CommentM createComment(CommentM comment);
    CommentM getCommentById(Long id);
    boolean deleteCommentById(Long id);
    boolean updateCommentById(CommentM Content, Long postId , Long id);
}
