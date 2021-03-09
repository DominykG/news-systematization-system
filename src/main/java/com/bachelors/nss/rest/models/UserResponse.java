package com.bachelors.nss.rest.models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class UserResponse {

    public static final String GENERAL_INFORMATION = "News will be published to the Kafka topic every day" +
            " at 18:00 UTC and will be saved to be consumed for 14 calendar days, older news will be deleted";

    private final String kafkaTopicName;
    private final String searchQuery;
    private final List<String> sources;

}
