package com.bachelors.nss.rest.models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class UserRequest {

    private final String name;
    //String = term/phrase; Boolean = exact match
    private final List<String> searchTerms;
    private final List<String> excludedTerms;
    private final List<String> sources;
    private final LocalDateTime from;

}


