package com.example.back.Controller;

import com.example.back.Model.CommentM;
import com.example.back.Service.CommentsS;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/comments")
public class CommentsController {
    private CommentsS service;

    @PostMapping("/create")
    public ResponseEntity<CommentM> addNewComment(@RequestBody CommentM comment){
        CommentM toCreate = service.addComment(comment);
        if(toCreate != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(toCreate);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<List<CommentM>> getAllCommentsByPostId(@PathVariable Long id){
        List<CommentM> comments = service.getCommentsByPostId(id);
        if(comments != null){
           return ResponseEntity.status(HttpStatus.OK).body(comments);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentM> getCommentById(@PathVariable Long id){
        CommentM comment = service.getCommentById(id);
        if(comment != null) {
            return ResponseEntity.status(HttpStatus.OK).body(comment);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/update/{id}/{postId}")
    public boolean updateCommentById(@PathVariable Long id,
                                     @PathVariable Long postId,
                                     @RequestBody CommentM comment){
        return service.updateCommentById(comment ,postId , id);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteCommentById(@PathVariable Long id){
        return service.deleteCommentById(id);
    }
}
