package com.example.back.Model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Table(name = "comment")
public class CommentM {
    @Id
    private Long id;

    private Long AuthorId;

    private String content;

    private Long PostId;

    private Timestamp datetime;
}
