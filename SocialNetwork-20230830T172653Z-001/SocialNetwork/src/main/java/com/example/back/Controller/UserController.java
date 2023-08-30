package com.example.back.Controller;

import com.example.back.Model.UserM;
import com.example.back.Service.UserS;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {
    private UserS service;

    @GetMapping("/users")
    public List<UserM> getAllUsers(){
        return service.getAllUsers();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserM> getUserById(@PathVariable Long id){
        UserM user = service.getUserById(id);
        if(user != null){
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/users/filter")
    public ResponseEntity<List<UserM>> getUserByUsername(@RequestParam String keyword){
        List<UserM> findingUser = service.findByUsername(keyword);
        if( keyword != null){
            return ResponseEntity.ok(findingUser);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/login")
    public ResponseEntity<UserM> login(@RequestBody UserM user) {
        UserM loggedInUser = service.logIn(user.getUsername(),user.getPassword());
        if (loggedInUser != null) {
            return ResponseEntity.ok(loggedInUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<UserM> signup(@RequestBody UserM user) {
        UserM createdUser = service.createUser(user);
        if (!user.getPassword().equals(user.getConfirmPassword())) {
             ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password not match");
        }
        if (createdUser != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/user/edit/{id}")
    public Boolean updateUserById(@PathVariable Long id,
                                  @RequestBody UserM user){
        return service.updateUser(user, id);
    }
}
