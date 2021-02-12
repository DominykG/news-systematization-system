package com.bachelors.nss.rest.controllers;

import com.bachelors.nss.database.models.NewsArticle;
import com.bachelors.nss.database.repositories.ArticleRepository;
import com.bachelors.nss.kafka.producers.ProtoMessageKafkaProducer;
import com.bachelors.nss.protobuf.NewsArticleProto.Article;
import com.google.protobuf.Timestamp;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    @GetMapping("/getAll")
    private List<NewsArticle> getEmployees() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsArticle> read(@PathVariable("id") Long id) {
        NewsArticle foundArticle = repository.getOne(id);

        return ResponseEntity.ok(foundArticle);
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

    @PostMapping("/create")
    private ResponseEntity<NewsArticle> create(@RequestBody NewsArticle newsArticle) {
        NewsArticle createdArticle = repository.save(newsArticle);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdArticle.getId())
                .toUri();

        return ResponseEntity.created(uri)
                .body(createdArticle);
    }

    @PostMapping("/createMany")
    private List<NewsArticle> createMany(@RequestBody NewsArticle[] newsArticles) {
        List<NewsArticle> createdArticles = List.of(newsArticles);
        repository.saveAll(createdArticles);

        return createdArticles;
    }

}
