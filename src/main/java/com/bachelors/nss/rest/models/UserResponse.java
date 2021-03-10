package com.bachelors.nss.rest.models;

import com.bachelors.nss.db.models.Source;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Set;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class UserResponse {

    public final String generalInformation = "News will be published to the Kafka topic every day" +
            " at 18:00 UTC and will be saved to be consumed for 14 calendar days, older news will be deleted";

    private final String name;
    private final String kafkaTopicName;
    private final String searchQuery;
    private final Set<Source> sources;

}
