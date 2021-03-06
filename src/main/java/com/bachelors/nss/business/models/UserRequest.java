package com.bachelors.nss.business.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public class UserRequest {

    private final String name;
    private final Set<String> searchTerms;
    private final Set<String> excludedTerms;
    private final Set<String> sources;
    private final Set<String> rssFeeds;

}


