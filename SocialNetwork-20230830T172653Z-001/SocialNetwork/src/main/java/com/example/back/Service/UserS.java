package com.example.back.Service;

import com.example.back.Model.UserM;
import com.example.back.Repository.Impl.UserRImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class UserS {
    private UserRImpl repository;

    public List<UserM> getAllUsers(){
        return repository.getAllUsers();
    }

    public UserM getUserById(Long id){
        return repository.getUserById(id);
    }

    public List<UserM> findByUsername(String keyword){
        return repository.filterUserByUsername(keyword);
    }

    public Boolean updateUser(UserM user,Long id){
        return repository.updateExistingUserById(user,id);
    }

    public UserM createUser(UserM user){
        return repository.signUp(user);
    }

    public UserM logIn(String username,String password){
        UserM user =  repository.logIn(username,password);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        } else {
            return null;
        }
    }
}
