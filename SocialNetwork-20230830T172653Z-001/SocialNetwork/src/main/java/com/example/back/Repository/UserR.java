package com.example.back.Repository;

import com.example.back.Model.UserM;

import java.util.List;

public interface UserR {
    UserM getUserById(Long id);

    List<UserM> filterUserByUsername(String keyword);

    List<UserM> getAllUsers();

    Boolean updateExistingUserById(UserM user,Long id);

    UserM signUp(UserM user);

    UserM logIn(String username, String password);
}
