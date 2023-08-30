package com.example.back.Controller;

import com.example.back.Model.PostM;
import com.example.back.Service.PostS;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class Postcontroller {

    private PostS postService;

    @PostMapping("/create")
    public ResponseEntity<PostM> createPost(@RequestBody PostM post) {
        PostM posts = postService.createPost(post);
        if(posts != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(posts);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/")
    public ResponseEntity<List<PostM>> getAllPosts() {
        List<PostM> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostM> getPostByAuthorId(@PathVariable Long id) {
        PostM post = postService.getPostById(id);
        if (post != null) {
            return ResponseEntity.ok(post);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public Boolean updatePost(@PathVariable Long id,
                              @RequestBody PostM post) {
        return postService.updatePost(post.getDescription() , id);
    }

    @DeleteMapping("/delete/{id}")
    public Boolean deletePost(@PathVariable Long id) {
        return postService.deletePost(id);
    }
}
