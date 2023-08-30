package com.example.back.Repository.Impl;

import com.example.back.Model.CommentM;
import com.example.back.Repository.CommentsR;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Repository
public class CommentsRImpl implements CommentsR {
    private DataSource dataSource;

    @Override
    public List<CommentM> getListCommentByPostId(Long id) {
        List<CommentM> comments = new ArrayList<>();
        String sql = "SELECT * FROM comments WHERE id_post = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    CommentM comment = new CommentM();
                    comment.setId(resultSet.getLong("id"));
                    comment.setAuthorId(resultSet.getLong("id_author"));
                    comment.setPostId(resultSet.getLong("id_post"));
                    comment.setContent(resultSet.getString("content"));
                    comment.setDatetime(resultSet.getTimestamp("datetime"));
                    comments.add(comment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }

    @Override
    public CommentM createComment(CommentM comment) {
        String sql = "INSERT INTO comments (id_author, id_post, content , datetime) VALUES (?, ?, ? , ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, comment.getAuthorId());
            preparedStatement.setLong(2, comment.getPostId());
            preparedStatement.setString(3, comment.getContent());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        comment.setId(generatedKeys.getLong(1));
                        return comment;
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
    public CommentM getCommentById(Long id) {
        String sql = "SELECT * FROM comments WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    CommentM comment = new CommentM();
                    comment.setId(resultSet.getLong("id"));
                    comment.setAuthorId(resultSet.getLong("id_author"));
                    comment.setPostId(resultSet.getLong("id_post"));
                    comment.setContent(resultSet.getString("content"));
                    comment.setDatetime(resultSet.getTimestamp("datetime"));

                    return comment;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteCommentById(Long id) {
        String sql = "DELETE FROM comments WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean updateCommentById(CommentM content , Long postId, Long id ) {
        String sql = "UPDATE comments SET content = ? WHERE id_post = ? AND id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, content.getContent());
            preparedStatement.setLong(2,postId);
            preparedStatement.setLong(3, id);

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
