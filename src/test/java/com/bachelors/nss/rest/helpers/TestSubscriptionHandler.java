package com.bachelors.nss.rest.helpers;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.parallel.Execution;

import static com.bachelors.nss.rest.helpers.SubscriptionHandler.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestSubscriptionHandler {

    @Test
    @Order(1)
    void testGenerateKafkaTopicScript() {
        generateKafkaTopicScript("test1");
        //check if file exists
    }

    @Test
    @Order(2)
    void testExecuteKafkaTopicScript() {
        executeKafkaTopicScript("test1.bat");
        //check if topic exists
    }

    @Test
    @Order(3)
    void testDeleteKafkaTopicScript() {
        deleteKafkaTopicScript("test1.bat");
        //check if file no longer exists
    }
}