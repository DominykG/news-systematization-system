package com.bachelors.nss.business.models;

import com.bachelors.nss.database.models.Source;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class UserResponse {

    public final String generalInformation = "News will be published to the Kafka topic every day" +
            " at 18:00 UTC and will be saved to be consumed for 14 calendar days, older news will be deleted";

    private final String name;
    private final String kafkaTopicName;
    private final String searchQuery;
    private final Set<Source> sources;

}
