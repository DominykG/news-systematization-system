package com.bachelors.nss.kafka.consumers;

import com.bachelors.nss.database.models.NewsTopic;
import com.bachelors.nss.database.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NewsAsProtoKafkaConsumer {

    @Autowired
    private MessageRepository messageRepository;

    @KafkaListener(topics = "${spring.kafka.consumer.topic}")
    public void listen(String message) {
        messageRepository.save(NewsTopic.builder().body(message).build());
        System.out.println("I got this: " + message);
    }
}
