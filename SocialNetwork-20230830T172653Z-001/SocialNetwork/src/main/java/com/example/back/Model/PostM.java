package com.example.back.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


import java.sql.Timestamp;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "post")
public class PostM {
    @Id
    private Long idPost;

    private String description;

    private String dateTime;

    private Long authorId;



}
