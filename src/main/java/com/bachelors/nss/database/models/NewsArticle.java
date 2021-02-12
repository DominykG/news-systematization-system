package com.bachelors.nss.database.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "NEWS_ARTICLE")
public class NewsArticle implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false)
    private Long id;

    @Column(name = "SOURCE", nullable = false)
    private String source;

    @Column(name = "AUTHOR", nullable = false)
    private String author;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "DESCRIPTION", nullable = false, length = 2048)
    private String description;

    @Column(name = "URL", nullable = false, length = 1024)
    private String url;

    @Column(name = "URL_TO_IMAGE", nullable = false, length = 1024)
    private String urlToImage;

    @Column(name = "PUBLISHED_AT", nullable = false)
    private LocalDateTime publishedAt;

    @Column(name = "CONTENT", nullable = false, length = 2048)
    private String content;
}
