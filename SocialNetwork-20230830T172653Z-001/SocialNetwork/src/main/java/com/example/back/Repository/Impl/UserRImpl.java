package com.example.back.Repository.Impl;

import com.example.back.Model.Gender;
import com.example.back.Model.UserM;
import com.example.back.Repository.UserR;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class UserRImpl implements UserR {
    private final DataSource dataSource;

    @Override
    public UserM getUserById(Long id) {
        String sql = "SELECT * FROM account WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    UserM user = new UserM();
                    user.setId_user(resultSet.getLong("id"));
                    user.setBirthday(resultSet.getDate("birthday"));
                    user.setGender(resultSet.getString("gender") != null ? Gender.valueOf(resultSet.getString("gender")) : null);
                    user.setPassword(resultSet.getString("password"));
                    user.setUsername(resultSet.getString("username"));
                    user.setEmail(resultSet.getString("email"));

                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<UserM> filterUserByUsername(String keyword) {
        List<UserM> users = new ArrayList<>();
        String sql = "SELECT * FROM account WHERE username LIKE ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + keyword + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    UserM user = new UserM();
                    user.setId_user(resultSet.getLong("id"));
                    user.setBirthday(resultSet.getDate("birthday"));
                    user.setGender(resultSet.getString("gender") != null ? Gender.valueOf(resultSet.getString("gender")) : null);
                    user.setPassword(resultSet.getString("password"));
                    user.setUsername(resultSet.getString("username"));
                    user.setEmail(resultSet.getString("email"));

                    users.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public List<UserM> getAllUsers() {
        List<UserM> users = new ArrayList<>();
        String sql = "SELECT * FROM account";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    UserM user = new UserM();
                    user.setId_user(resultSet.getLong("id"));
                    user.setBirthday(resultSet.getDate("birthday"));
                    user.setGender(resultSet.getString("gender") != null ? Gender.valueOf(resultSet.getString("gender")) : null);
                    user.setPassword(resultSet.getString("password"));
                    user.setUsername(resultSet.getString("username"));
                    user.setEmail(resultSet.getString("email"));

                    users.add(user);
                }
            }
         catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public Boolean updateExistingUserById(UserM user,Long id) {
        String sql = "UPDATE account SET username = ?, birthday = ? , gender = ? , password = ?, email = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setDate(2,user.getBirthday());
            preparedStatement.setString(3 ,String.valueOf(user.getGender()));
            preparedStatement.setString(4,user.getPassword());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setLong(6, id);

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public UserM signUp(UserM user) {
        String sql = "INSERT INTO account(username,email,password,confirm_password) values (? , ? , ? , ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getConfirmPassword());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setId_user(generatedKeys.getLong(1));
                        return user;
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return null;
    }

    @Override
    public UserM logIn(String username,String password) {
        String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    UserM user = new UserM();
                    user.setId_user(resultSet.getLong("id"));
                    user.setBirthday(resultSet.getDate("birthday"));
                    user.setGender(resultSet.getString("gender") != null ? Gender.valueOf(resultSet.getString("gender")) : null);
                    user.setPassword(resultSet.getString("password"));
                    user.setUsername(resultSet.getString("username"));
                    user.setEmail(resultSet.getString("email"));

                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
