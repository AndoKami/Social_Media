package com.example.back.Repository.Impl;

import com.example.back.Model.PostM;
import com.example.back.Repository.PostR;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class PostRImpl implements PostR {

    private DataSource dataSource;

    @Override
    public PostM createPost(PostM post) {
        String sql = "INSERT INTO post(description,author_id,date_time) values (? , ? , ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, post.getDescription());
            preparedStatement.setLong(2, post.getAuthorId());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        post.setIdPost(generatedKeys.getLong(1));
                        return post;
                    }
                }
            }
        }
            catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PostM getPostById(Long authorId) {
        String sql = "SELECT * FROM post WHERE author_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, authorId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    PostM post = new PostM();
                    post.setIdPost(resultSet.getLong("id_post"));
                    post.setDescription(resultSet.getString("description"));
                    post.setAuthorId(resultSet.getLong("author_id"));
                    post.setDateTime(String.valueOf(resultSet.getTimestamp("date_time")));

                    return post;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<PostM> getAllPosts() {
        List<PostM> posts = new ArrayList<>();
        String sql = "SELECT * FROM post";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                PostM post = new PostM();
                post.setIdPost(resultSet.getLong("id_post"));
                post.setDescription(resultSet.getString("description"));
                post.setAuthorId(resultSet.getLong("author_id"));
                post.setDateTime(String.valueOf(resultSet.getTimestamp("date_time")));

                posts.add(post);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public Boolean updatePost(String description,Long id) {
        String sql = "UPDATE post SET description = ? WHERE id_post = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1,description);
            preparedStatement.setLong(2, id);

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean deletePost(Long postId) {
        String sql = "DELETE FROM post WHERE id_post = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, postId);

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            return false;
        }
    }
}
