package com.bachelors.nss.business.validation;

import com.bachelors.nss.business.errors.ValidationError;
import com.bachelors.nss.business.models.UserRequest;
import com.bachelors.nss.database.models.RssFeed;
import com.bachelors.nss.database.models.Source;
import com.bachelors.nss.database.repositories.RssFeedRepository;
import com.bachelors.nss.database.repositories.SourceRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public final class UserRequestValidator {

    private static final String NAME_FIELD = "name";
    private static final String SEARCH_TERMS_FIELD = "searchTerms";
    private static final String EXCLUDED_TERMS_FIELD = "excludedTerms";
    private static final String SOURCES_FIELD = "sources";
    private static final String RSS_FEED_FIELD = "rssFeeds";

    private static final Set<ValidationError> errors = new HashSet<>();

    public static Set<ValidationError> validateUserRequest(UserRequest request,
                                                           SourceRepository sourceRepository,
                                                           RssFeedRepository rssFeedRepository) {
        errors.clear();

        validateName(request);
        validateSearchTerms(request);
        validateExcludedTerms(request);
        validateSources(request, sourceRepository);
        validateRssFeeds(request, rssFeedRepository);

        return errors;
    }

    private static void validateName(UserRequest request) {
        try {
            if (request.getName().length() < 3 || request.getName().length() > 20) {
                errors.add(ValidationError.of(  NAME_FIELD,
                        "Name must be more than 3 and less than 20 characters long."));
            }
        } catch (NullPointerException e) {
            errors.add(ValidationError.of(NAME_FIELD, "Field must be present."));
        }
    }

    private static void validateSearchTerms(UserRequest request) {
        try {
            if (request.getSearchTerms().size() < 1) {
                errors.add(ValidationError.of(SEARCH_TERMS_FIELD,
                        "At least one search term must be present."));
            }
            request.getSearchTerms().stream().forEach((searchTerm) -> {
                if (searchTerm.length() > 30)
                    errors.add(ValidationError.of(SEARCH_TERMS_FIELD,
                            String.format("'%s'. Is longer than 100 characters.", searchTerm)));
            });
        } catch (NullPointerException e) {
            errors.add(ValidationError.of(SEARCH_TERMS_FIELD, "Field must be present."));
        }
    }

    private static void validateExcludedTerms(UserRequest request) {
        if (request.getExcludedTerms() != null) {
            request.getExcludedTerms().stream().forEach((excludedTerm) -> {
                if (excludedTerm.length() > 30)
                    errors.add(ValidationError.of(EXCLUDED_TERMS_FIELD,
                            String.format("'%s'. Is longer than 100 characters.", excludedTerm)));
            });
        }
    }

    private static void validateSources(UserRequest request, SourceRepository sourceRepository) {
        if (request.getSources() != null) {
            if (request.getSources().size() > 20) {
                errors.add(ValidationError.of(SOURCES_FIELD, "20 or less sources allowed."));
            }
            for (String source : request.getSources()) {
                Source s = sourceRepository.findByName(source);
                if (s == null) {
                    errors.add(ValidationError.of(SOURCES_FIELD,
                            String.format("Source with name provided is invalid. Name: '%s'.", source)));
                }
            }
        }
    }

    private static void validateRssFeeds(UserRequest request, RssFeedRepository rssFeedRepository) {
        if (request.getRssFeeds() != null) {
            for (String rssFeed : request.getRssFeeds()) {
                Optional<RssFeed> rss = rssFeedRepository.findById(rssFeed);
                if (rss.isEmpty()) {
                    errors.add(ValidationError.of(RSS_FEED_FIELD,
                            String.format("RSS Feed with name provided is invalid. Name: '%s'.", rssFeed)));
                }
            }
        }
    }

}