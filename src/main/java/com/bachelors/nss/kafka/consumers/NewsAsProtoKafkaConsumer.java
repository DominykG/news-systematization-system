package com.bachelors.nss.kafka.consumers;

import com.bachelors.nss.database.repositories.ArticleRepository;
import com.bachelors.nss.protobuf.NewsArticleProto.Article;
import com.google.protobuf.InvalidProtocolBufferException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.bachelors.nss.database.helpers.ProtoToObjectConverter.parseProtoToObject;

@Service
public class NewsAsProtoKafkaConsumer {

    @Autowired
    ArticleRepository repository;

    @KafkaListener(topics = "${spring.kafka.consumer.topic}")
    public void listen(byte[] article) throws InvalidProtocolBufferException {

        Article protoArticle = Article.parseFrom(article);
        System.out.println("I got this: " + protoArticle);

        repository.save(parseProtoToObject(protoArticle));
    }
}
