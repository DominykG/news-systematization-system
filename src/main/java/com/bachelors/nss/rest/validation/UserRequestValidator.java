package com.bachelors.nss.rest.validation;

import com.bachelors.nss.db.models.Source;
import com.bachelors.nss.db.repositories.SourceRepository;
import com.bachelors.nss.errors.ValidationError;
import com.bachelors.nss.rest.models.UserRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Log4j2
@Component
public final class UserRequestValidator {

    public static final String NAME_FIELD = "name";
    public static final String SEARCH_TERMS_FIELD = "searchTerms";
    public static final String EXCLUDED_TERMS_FIELD = "excludedTerms";
    public static final String SOURCES_FIELD = "sources";
    public static final String FROM_FIELD = "from";

    private static final Set<ValidationError> errors = new HashSet<>();

    public static Set<ValidationError> validateUserRequest(UserRequest request, SourceRepository sourceRepository) {
        errors.clear();

        validateName(request);
        validateSearchTerms(request);
        validateExcludedTerms(request);
        validateSources(request, sourceRepository);
        validateFrom(request);

        return errors;
    }

    private static void validateName(UserRequest request) {
        try {
            if (request.getName().length() < 3 || request.getName().length() > 20) {
                errors.add(ValidationError.builder()
                        .fieldName(NAME_FIELD)
                        .errorMessage("Name must be more than 3 and less than 20 characters long.")
                        .build());
            }
        } catch (NullPointerException e) {
            errors.add(ValidationError.builder()
                    .fieldName(NAME_FIELD)
                    .errorMessage("Field must be present.")
                    .build());
        }
    }

    private static void validateSearchTerms(UserRequest request) {
        try {
            if (request.getSearchTerms().size() < 1) {
                errors.add(ValidationError.builder()
                        .fieldName(SEARCH_TERMS_FIELD)
                        .errorMessage("At least one search term must be present.")
                        .build());
            }
            request.getSearchTerms().stream().forEach((searchTerm) -> {
                if (searchTerm.length() > 100)
                    errors.add(ValidationError.builder()
                            .fieldName(SEARCH_TERMS_FIELD)
                            .errorMessage(String.format("'%s'. Is longer than 100 characters.", searchTerm))
                            .build());
            });
        } catch (NullPointerException e) {
            errors.add(ValidationError.builder()
                    .fieldName(SEARCH_TERMS_FIELD)
                    .errorMessage("Field must be present.")
                    .build());
        }
    }

    private static void validateExcludedTerms(UserRequest request) {
        if (request.getExcludedTerms() != null) {
            request.getExcludedTerms().stream().forEach((excludedTerm) -> {
                if (excludedTerm.length() > 100)
                    errors.add(ValidationError.builder()
                            .fieldName(EXCLUDED_TERMS_FIELD)
                            .errorMessage(String.format("'%s'. Is longer than 100 characters.", excludedTerm))
                            .build());
            });
        }
    }

    private static void validateSources(UserRequest request, SourceRepository sourceRepository) {
        if (request.getSources() != null) {
            if (request.getSources().size() > 20) {
                errors.add(ValidationError.builder()
                        .fieldName(SOURCES_FIELD)
                        .errorMessage("20 or less sources allowed.")
                        .build());
            }
            for (String source : request.getSources()) {
                Source s = sourceRepository.findByName(source);
                if (s == null) {
                    errors.add(ValidationError.builder()
                            .fieldName(SOURCES_FIELD)
                            .errorMessage(String.format("Source with name provided is invalid. Name: '%s'.", source))
                            .build());
                }
            }
        }
    }

    private static void validateFrom(UserRequest request) {
        if (request.getFrom() != null && request.getFrom().isAfter(LocalDateTime.now())) {
            errors.add(ValidationError.builder()
                    .fieldName(FROM_FIELD)
                    .errorMessage("Date is later than today.")
                    .build());
        }
    }

}
