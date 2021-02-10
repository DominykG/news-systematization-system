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

    @Column(name = "SOURCE_NAME", nullable = false)
    private String sourceName;

    @Column(name = "AUTHOR", nullable = false)
    private String author;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "URL", nullable = false)
    private String url;

    @Column(name = "IMAGE_URL", nullable = false)
    private String imageUrl;

    @Column(name = "DATE_PUBLISH", nullable = false)
    private LocalDateTime datePublish;

    @Column(name = "CONTENT", nullable = false)
    private String content;
}
