package com.bachelors.nss.kafka.producers;

import com.bachelors.nss.protobuf.NewsArticleProto.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProtoMessageKafkaProducer {

    @Autowired
    private KafkaTemplate<String, byte[]> protoMessageKafkaTemplate;

    @Value("${spring.kafka.producer.topic}")
    private String kafkaTopic;

    //Call to send a particular Article proto
    public void send(Article article) {
        protoMessageKafkaTemplate.send(kafkaTopic, article.toByteArray());
    }

}
