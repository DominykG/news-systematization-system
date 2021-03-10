package com.bachelors.nss.rest.validation;

import com.bachelors.nss.db.repositories.SourceRepository;
import com.bachelors.nss.errors.ValidationError;
import com.bachelors.nss.rest.models.UserRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
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

    private static SourceRepository repository;

    @Autowired
    public UserRequestValidator(SourceRepository repository) {
        UserRequestValidator.repository = repository;
    }

    public static Set<ValidationError> validateUserRequest(UserRequest request) {
        errors.clear();

        validateName(request);
        validateSearchTerms(request);
        validateExcludedTerms(request);
        validateSources(request);
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
            request.getSearchTerms().forEach((searchTerm) -> {
                if (searchTerm.length() > 100)
                    errors.add(ValidationError.builder()
                            .fieldName(SEARCH_TERMS_FIELD)
                            .errorMessage(String.format("'%s'. Is longer than 100 characters.", searchTerm))
                            .build());
            });
        } catch (HttpMessageNotReadableException e){
            errors.add(ValidationError.builder()
                    .fieldName(SEARCH_TERMS_FIELD)
                    .errorMessage("Field must be a List.")
                    .build());
        } catch (NullPointerException e) {
            errors.add(ValidationError.builder()
                    .fieldName(SEARCH_TERMS_FIELD)
                    .errorMessage("Field must be present.")
                    .build());
        }
    }

    private static void validateExcludedTerms(UserRequest request) {
        if (request.getExcludedTerms() != null) {
            request.getExcludedTerms().forEach((excludedTerm) -> {
                if (excludedTerm.length() > 100)
                    errors.add(ValidationError.builder()
                            .fieldName(EXCLUDED_TERMS_FIELD)
                            .errorMessage(String.format("'%s'. Is longer than 100 characters.", excludedTerm))
                            .build());
            });
        }
    }

    private static void validateSources(UserRequest request) {
        if (request.getSources() != null) {
            if (request.getSources().size() > 20) {
                errors.add(ValidationError.builder()
                        .fieldName(SOURCES_FIELD)
                        .errorMessage("20 or less sources allowed")
                        .build());
            }
            for (String source : request.getSources()) {
                if (!repository.existsById(source)) {
                    errors.add(ValidationError.builder()
                            .fieldName(SOURCES_FIELD)
                            .errorMessage(String.format("'%s' is not a valid source", source))
                            .build());
                }
            }
        }
    }

    private static void validateFrom(UserRequest request) {
        if (request.getFrom().isAfter(LocalDateTime.now())) {
            errors.add(ValidationError.builder()
                    .fieldName(FROM_FIELD)
                    .errorMessage("Date is later than today.")
                    .build());
        }
    }

}
