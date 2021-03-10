package com.bachelors.nss.rest.models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class UserRequest {

    private final String name;
    private final Set<String> searchTerms;
    private final Set<String> excludedTerms;
    private final Set<String> sources;//names
    private final LocalDateTime from;

}


