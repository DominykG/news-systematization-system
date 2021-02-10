package com.bachelors.nss.kafka.consumers;

import com.bachelors.nss.protobuf.NewsArticle.Article;
import com.google.protobuf.InvalidProtocolBufferException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NewsAsProtoKafkaConsumer {

    @KafkaListener(topics = "${spring.kafka.consumer.topic}")
    public void listen(byte[] article) throws InvalidProtocolBufferException {
        System.out.println("I got this: " + Article.parseFrom(article));
    }
}
