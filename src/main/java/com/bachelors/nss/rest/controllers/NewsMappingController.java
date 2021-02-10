package com.bachelors.nss.rest.controllers;

import com.bachelors.nss.protobuf.NewsArticle.Article;

import com.bachelors.nss.database.models.NewsArticle;
import com.bachelors.nss.database.repositories.ArticleRepository;
import com.bachelors.nss.kafka.producers.ProtoMessageKafkaProducer;
import com.google.protobuf.Timestamp;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/nss/v1")
public final class NewsMappingController {

    @Autowired
    ArticleRepository repository;

    @Autowired
    private ProtoMessageKafkaProducer kafkaProducer;

    @GetMapping("/health")
    private void getHealth() {}

    @GetMapping("/getArticles/all")
    private List<NewsArticle> getEmployees() {
        return repository.findAll();
    }

    @PostMapping("/sendDefaultArticle")
    private void sendKafkaMessage() {
        kafkaProducer.send(Article.newBuilder()
                .setSourceName("Source")
                .setAuthor("Author")
                .setTitle("Title")
                .setDescription("Description")
                .setUrl("Url")
                .setImageUrl("ImageUrl")
                .setDatePublish(Timestamp.newBuilder().setSeconds(1612915200L).build())
                .setContent("Content")
                .build());
    }

}
