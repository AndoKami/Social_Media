CREATE DATABASE social_media;

\c social_media;

CREATE TABLE account (
                         id  SERIAL PRIMARY KEY,
                         username  VARCHAR(200) NOT NULL,
                         birthday    DATE,
                         gender      VARCHAR(1) CHECK (gender IN('M','F')),
                         email       VARCHAR(200) NOT NULL UNIQUE,
                         password    VARCHAR(200) NOT NULL,
                         confirm_password VARCHAR(200)
);

CREATE TABLE post (
                      id_post SERIAL PRIMARY KEY,
                      description text,
                      date_time TIMESTAMP,
                      author_id  INT REFERENCES account(id) ON DELETE CASCADE
);

CREATE TABLE comments (
                         id  SERIAL PRIMARY KEY,
                         id_author  int NOT NULL REFERENCES account(id),
                         id_post  int NOT NULL REFERENCES post(id_post),
                         content  VARCHAR NOT NULL,
                         datetime TIMESTAMP NOT NULL
);